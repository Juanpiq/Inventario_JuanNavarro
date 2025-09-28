package com.MposGlobal.Inventario_JuanNavarro.Servicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Categoria;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios.CategoriaInterfazServicio;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriaServicio implements CategoriaInterfazServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<CategoriaRespDTO> listarCategorias() {
        return categoriaRepositorio.findAll().stream()
                .filter(Categoria::getActivo) // solo categorías activas
                .map(this::mapToResponseDTO)
                .toList();
    }


    @Override
    public CategoriaRespDTO crearCategoria(CategoriaCrearDTO catdto) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByNombreIgnoreCase(catdto.nombre());
        if(existente == null){
            Categoria categoria = new Categoria();
            categoria.setNombre(catdto.nombre());
            Categoria guardada = this.categoriaRepositorio.save(categoria);
            return mapToResponseDTO(guardada);
        } else throw new BusinessException("La categoria " + existente.getNombre() + " ya existe", 406);


    }

    @Override
    public CategoriaRespDTO buscarCategoria(int id) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByIdCategoria(id);
        if(existente != null){
            if(!existente.getActivo())
                throw new BusinessException("La categoria con id " + id + " esta desactivada, activar primero si desea ver sus datos", 406);
            else return mapToResponseDTO(existente);
        }else throw new BusinessException("La categoría con el id " + id + " no existe", 404);
    }

    @Override
    public CategoriaRespDTO buscarCategoria(String nombre) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByNombreIgnoreCase(nombre);
        if(existente != null){
            if(!existente.getActivo())
                throw new BusinessException("La categoria con nombre " + nombre + " esta desactivada, activar primero si desea ver sus datos", 406);
            return mapToResponseDTO(existente);
        }else throw new BusinessException("La categoría con el nombre " + nombre + " no existe", 404);
    }

    @Override
    public List<ProductoRespDTO> listarProductosCategoria(int id) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByIdCategoria(id);
        if(existente != null){
            if(existente.getActivo()){
                List<Producto> productosActivos = existente.getProductos().stream()
                        .filter(Producto::getActivo)
                        .toList();

                return productosActivos.stream()
                        .map(this::mapProductoToDTO)
                        .toList();
            } else throw new BusinessException("La categoría con el id " + id + " está desactivada, activar primero si desea ver sus datos", 406);

        } else throw new BusinessException("No existe categoría con el id " + id, 404);
    }

    @Override
    public String desactivarCategoria(int id) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByIdCategoria(id);
        if(existente != null){
            if(existente.getActivo()) {
                existente.setActivo(false);
                existente.setFechaActualizacion(LocalDateTime.now());

                if(existente.getProductos() != null){
                    for(Producto producto : existente.getProductos()){
                        if(producto.getActivo()){
                            producto.setActivo(false);
                            producto.setFechaActualizacion(LocalDateTime.now());
                        }
                    }
                }

                this.categoriaRepositorio.save(existente);
                return "Se ha desactivado la categoría " + existente.getNombre() + " exitosamente";
            } else throw new BusinessException("La categoría " + existente.getNombre() + " ya estaba desactivada", 406);
        }else throw new BusinessException("La categoría con el id " + id + " no existe", 404);
    }

    @Override
    public String desactivarCategoria(String nombre) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByNombreIgnoreCase(nombre);
        if(existente != null){
            if(existente.getActivo()) {
                existente.setActivo(false);
                existente.setFechaActualizacion(LocalDateTime.now());

                if(existente.getProductos() != null){
                    for(Producto producto : existente.getProductos()){
                        if(producto.getActivo()){
                            producto.setActivo(false);
                            producto.setFechaActualizacion(LocalDateTime.now());
                        }
                    }
                }

                this.categoriaRepositorio.save(existente);
                return "Se ha desactivado la categoría " + existente.getNombre() + " exitosamente";
            } else throw new BusinessException("La categoría " + existente.getNombre() + " ya estaba desactivada", 406);
        }else throw new BusinessException("La categoría con el nombre " + nombre + " no existe", 404);
    }

    @Override
    public String activarCategoria(int id) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByIdCategoria(id);
        if(existente != null){
            if(!existente.getActivo()) {
                existente.setActivo(true);
                existente.setFechaActualizacion(LocalDateTime.now());

                this.categoriaRepositorio.save(existente);
                return "Se ha activado la categoría " + existente.getNombre() + " exitosamente";
            } else throw new BusinessException("La categoría " + existente.getNombre() + " ya estaba activada", 406);
        }else throw new BusinessException("La categoría con el id " + id + " no existe", 404);
    }

    @Override
    public String activarCategoria(String nombre) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByNombreIgnoreCase(nombre);
        if(existente != null){
            if(!existente.getActivo()) {
                existente.setActivo(true);
                existente.setFechaActualizacion(LocalDateTime.now());

                this.categoriaRepositorio.save(existente);
                return "Se ha activado la categoría " + existente.getNombre() + " exitosamente";
            } else throw new BusinessException("La categoría " + existente.getNombre() + " ya estaba activada", 406);
        }else throw new BusinessException("La categoría con el nombre " + nombre + " no existe", 404);
    }

    @Override
    public String modificarCategoria(CategoriaActDTO categoria) throws BusinessException {
        Categoria existente = this.categoriaRepositorio.findByIdCategoria(categoria.id());

        if(existente != null){
            Categoria nombreExistente = this.categoriaRepositorio.findByNombreIgnoreCase(categoria.nombre());
            if(existente.getIdCategoria() != nombreExistente.getIdCategoria())
                throw new BusinessException("El producto " + nombreExistente.getNombre() + " ya existe", 409);
            existente.setNombre(categoria.nombre());
            existente.setFechaActualizacion(LocalDateTime.now());
            if(categoria.activo() != existente.getActivo()){
                existente.setActivo(!existente.getActivo());
                if(!existente.getActivo()){
                    String desactivar = desactivarCategoria(existente.getIdCategoria());
                } else activarCategoria(existente.getIdCategoria());
            }

            this.categoriaRepositorio.save(existente);
            return "Se ha modificado la categoria con exito";
        } else throw new BusinessException("No existe categoría con id " + categoria.id(), 404);
    }

    // Mapper de CategoriaRespDTO
    private CategoriaRespDTO mapToResponseDTO(Categoria categoria) {
        return new CategoriaRespDTO(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getActivo(),
                categoria.getFechaCreacion(),
                categoria.getFechaActualizacion(),
                categoria.getProductos() == null ? List.of() :
                        categoria.getProductos().stream()
                                .filter(Producto::getActivo)  // solo productos activos
                                .map(Producto::getNombre)
                                .toList()
        );
    }

    // Mapper de ProductoRespDTO
    private ProductoRespDTO mapProductoToDTO(Producto producto) {
        Set<String> tagNames = producto.getTags() == null ? Set.of() :
                producto.getTags().stream()
                        .map(Tag::getNombre)
                        .collect(Collectors.toSet());

        return new ProductoRespDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getCategoria().getNombre(),
                producto.getCosto(),
                producto.getPrecio(),
                producto.getActivo(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion(),
                tagNames
        );
    }

}
