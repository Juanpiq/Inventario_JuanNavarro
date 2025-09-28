package com.MposGlobal.Inventario_JuanNavarro.Servicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Categoria;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios.ProductoInterfazServicio;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.CategoriaRepositorio;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.ProductoRepositorio;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.TagRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoServicio implements ProductoInterfazServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private TagRepositorio tagRepositorio;


    @Override
    public List<ProductoRespDTO> listarProductos() {
        return productoRepositorio.findAll()
                .stream()
                .filter(Producto::getActivo)
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ProductoRespDTO crearProducto(ProductoCrearDTO productoCrearDTO) throws BusinessException {
        Producto prod = this.productoRepositorio.findByNombreIgnoreCase(productoCrearDTO.nombre());
        if(prod == null){
            Categoria existente = this.categoriaRepositorio.findByIdCategoria(productoCrearDTO.categoriaId().intValue());
            if(existente != null){
                if(existente.getActivo()){
                    prod = new Producto();
                    prod.setNombre(productoCrearDTO.nombre());
                    prod.setCategoria(existente);
                    prod.setFechaCreacion(LocalDateTime.now());

                    if (productoCrearDTO.tagsIds() != null && !productoCrearDTO.tagsIds().isEmpty()) {
                        // Obtener todos los tags por sus IDs
                        List<Tag> tagsEncontrados = this.tagRepositorio.findAllById(productoCrearDTO.tagsIds());
                        // Verificar que todos los IDs enviados existan
                        if (tagsEncontrados.size() != productoCrearDTO.tagsIds().size()) {
                            throw new BusinessException("Uno o más tags enviados no existen.", 404);
                        }
                        prod.setTags(new HashSet<>(tagsEncontrados));
                    }
                    return mapToDTO(this.productoRepositorio.save(prod));

                } else throw new BusinessException("La categoría con id" + existente.getIdCategoria() + " esta desactivada, activela antes si quiere asociar un producto", 409);
            } else throw new BusinessException("La categoría con id " + existente.getIdCategoria() + " no existe.", 404);
        } else throw new BusinessException("El producto " + prod.getNombre() + " ya existe", 409);
    }

    @Override
    public ProductoRespDTO buscarProducto(int id) throws BusinessException {
        Producto prod = this.productoRepositorio.findByIdProducto(id);
        if(prod != null){
            if(prod.getActivo())
                return mapToDTO(prod);
            else throw new BusinessException("El producto con id " + prod.getIdProducto() + " esta desacticado, activar primero si desea ver sus datos", 406);
        } else throw new BusinessException("El producto con id " + id + "no existe", 404);
    }

    @Override
    public ProductoRespDTO buscarProducto(String nombre) throws BusinessException {
        Producto prod = this.productoRepositorio.findByNombreIgnoreCase(nombre);
        if(prod != null){
            if(prod.getActivo())
                return mapToDTO(prod);
            else throw new BusinessException("El producto con nombre " + prod.getNombre() + " esta desacticado, activar primero si desea ver sus datos", 406);
        } else throw new BusinessException("El producto con nombre " + nombre + "no existe", 404);
    }

    @Override
    public String desactivarProducto(String nombre) throws BusinessException {
        Producto prod = this.productoRepositorio.findByNombreIgnoreCase(nombre);
        if(prod != null){
            if(prod.getActivo()){
                prod.setActivo(false);
                this.productoRepositorio.save(prod);
                return "Se ha desactivado el producto " + prod.getNombre() + " exitosamente";
            } else throw new BusinessException("El producto " + prod.getNombre() + " ya estaba desactivado", 406);
        } else throw new BusinessException("El producto " + nombre + " no existe", 404);
    }

    @Override
    public String desactivarProducto(int id) throws BusinessException {
        Producto prod = this.productoRepositorio.findByIdProducto(id);
        if(prod != null){
            if(prod.getActivo()){
                prod.setActivo(false);
                this.productoRepositorio.save(prod);
                return "Se ha desactivado el producto " + prod.getNombre() + " exitosamente";
            } else throw new BusinessException("El producto " + prod.getNombre() + " ya estaba desactivado", 406);
        } else throw new BusinessException("El producto con id " + id + " no existe", 404);
    }

    @Override
    public String activarProducto(String nombre) throws BusinessException {
        Producto prod = this.productoRepositorio.findByNombreIgnoreCase(nombre);
        if(prod != null){
            if(prod.getCategoria().getActivo()){
                if(!prod.getActivo()){
                    prod.setActivo(true);
                    this.productoRepositorio.save(prod);
                    return "Se ha activado la el producto " + prod.getNombre() + " exitosamente";
                } else throw new BusinessException("El producto " + prod.getNombre() + " ya estaba desactivado", 406);
            } else throw new BusinessException("El producto " + prod.getNombre() + " no se puede activar ya que su categoria está desactivada", 409);
        } else throw new BusinessException("El producto " + nombre + " no existe", 404);
    }

    @Override
    public String activarProducto(int id) throws BusinessException {
        Producto prod = this.productoRepositorio.findByIdProducto(id);
        if(prod != null){
            if(prod.getCategoria().getActivo()){
                if(!prod.getActivo()){
                    prod.setActivo(true);
                    this.productoRepositorio.save(prod);
                    return "Se ha activado la el producto " + prod.getNombre() + " exitosamente";
                } else throw new BusinessException("El producto " + prod.getNombre() + " ya estaba desactivado", 406);
            } else throw new BusinessException("El producto " + prod.getNombre() + " no se puede activar ya que su categoria está desactivada", 409);
        } else throw new BusinessException("El producto con id " + id + " no existe", 404);
    }

    @Override
    public String modificarProducto(ProductoActDTO productoActDTO) throws BusinessException {
        // Buscar el producto por ID
        Producto producto = this.productoRepositorio.findByIdProducto(productoActDTO.id());
        if (producto == null) {
            throw new BusinessException("El producto con id " + productoActDTO.id() + " no existe", 404);
        }

        // Verificar si el nombre ya existe en otro producto
        Producto existenteNombre = this.productoRepositorio.findByNombreIgnoreCase(productoActDTO.nombre());
        if (existenteNombre != null && !(existenteNombre.getIdProducto() == productoActDTO.id())) {
            throw new BusinessException("Ya existe un producto con nombre " + productoActDTO.nombre(), 409);
        }

        // Verificar categoría
        Categoria categoria = this.categoriaRepositorio.findByIdCategoria(productoActDTO.categoriaId());
        if (categoria == null) {
            throw new BusinessException("La categoría con id " + productoActDTO.categoriaId() + " no existe", 404);
        }
        if (!categoria.getActivo()) {
            throw new BusinessException("La categoría con id " + productoActDTO.categoriaId() + " está desactivada", 409);
        }

        // Actualizar datos
        producto.setNombre(productoActDTO.nombre());
        producto.setCategoria(categoria);
        producto.setCosto(productoActDTO.costo());
        producto.setPrecio(productoActDTO.precio());
        producto.setFechaActualizacion(LocalDateTime.now());

        // Actualizar tags si se proporcionan
        if (productoActDTO.tagsIds() != null && !productoActDTO.tagsIds().isEmpty()) {
            List<Tag> tagsEncontrados = this.tagRepositorio.findAllById(productoActDTO.tagsIds());
            if (tagsEncontrados.size() != productoActDTO.tagsIds().size()) {
                throw new BusinessException("Uno o más tags enviados no existen.", 404);
            }
            producto.setTags(new HashSet<>(tagsEncontrados));
        }

        // Guardar y retornar DTO
        Producto actualizado = this.productoRepositorio.save(producto);
        return "mapToDTO(actualizado)";
    }

    // Mapper para ProductoRespDTO
    private ProductoRespDTO mapToDTO(Producto producto) {
        Set<String> tags = producto.getTags() == null ? Set.of() :
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
                tags
        );
    }
}
