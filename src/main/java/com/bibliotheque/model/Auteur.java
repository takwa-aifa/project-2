package com.bibliotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "auteur")
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_prenom", nullable = false)
    private String nomPrenom;

    @Column(name = "nbre_points")
    private int nbrePoints = 0;

    @ManyToMany(mappedBy = "auteurs", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Livre> livres = new HashSet<>();

    public Auteur() {}

    public Auteur(String nomPrenom) {
        this.nomPrenom = nomPrenom;
        this.nbrePoints = 0;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomPrenom() { return nomPrenom; }
    public void setNomPrenom(String nomPrenom) { this.nomPrenom = nomPrenom; }

    public int getNbrePoints() { return nbrePoints; }
    public void setNbrePoints(int nbrePoints) { this.nbrePoints = nbrePoints; }

    public Set<Livre> getLivres() { return livres; }
    public void setLivres(Set<Livre> livres) { this.livres = livres; }

    public void ajouterPoints(int points) {
        this.nbrePoints += points;
    }
}
