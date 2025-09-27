package com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private int idTag;

    @Column(name = "nombre", nullable = false, length = 75)
    private String nombre;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();
}
