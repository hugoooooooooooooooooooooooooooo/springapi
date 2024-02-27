package com.javaspring.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "institutos")
public class Instituto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="instituto", nullable = false, length = 50)
    private String instituto;

    @OneToMany(mappedBy = "instituto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Instituto> institutos;

    public Instituto() {  }
    public Instituto(Long id, String instituto) {
        this.id = id;
        this.instituto = instituto;
    }
}

