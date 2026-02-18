package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est necessaire")
    @Size(min = 2, max = 100, message = "Le nom doit etre entre 2 -- 100 caractères")
    @Column(nullable = false)
    private String nom;

    @NotNull(message = "La capacité est necessaire")
    @Min(value = 1, message = "La capacité min est de un personne")
    @Max(value = 1000, message = "La capacité max est de mille personnes")
    @Column(nullable = false)
    private Integer capacite;

    @Size(max = 500, message = "La description ne peut pas etre < 500")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Le statut est necessaire")
    @Column(nullable = false)
    private Boolean disponible = true;

    @Min(value = 0, message = "L'étage ne peut pas être < 0")
    private Integer etage;

    // Constructeur par défaut requis par JPA
    public Salle() {
    }

    public Salle(String nom, Integer capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getEtage() {
        return etage;
    }

    public void setEtage(Integer etage) {
        this.etage = etage;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", description='" + description + '\'' +
                ", disponible=" + disponible +
                ", etage=" + etage +
                '}';
    }
}
