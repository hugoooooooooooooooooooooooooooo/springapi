package com.javaspring.api.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "profesores")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name="edad", nullable = false)
    private Integer edad;

    @Column(name="imagen", nullable = false,length = 100)
    private String imagen;

    @Lob
    @Basic
    @Column(name = "foto",columnDefinition = "longblob",nullable = true)
    private  byte[] foto;

    @ManyToOne
    @JoinColumn(name = "instituto_id")
    @JsonBackReference
    private Instituto instituto;

    @Column(name="created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    public Profesor() {
    }

    public Profesor(String nombre, Integer edad, Instituto instituto) {
        this.nombre = nombre;
        this.edad = edad;
        this.instituto = instituto;
    }
    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                '}';
    }

}

