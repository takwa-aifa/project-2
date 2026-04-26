package com.bibliotheque.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "annee_parution")
    private int anneeParution;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "livre_auteur",
        joinColumns = @JoinColumn(name = "livre_isbn"),
        inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    @JsonIgnore
    private Set<Auteur> auteurs = new HashSet<>();

    public Livre() {}

    public Livre(String isbn, String titre, int anneeParution) {
        this.isbn = isbn;
        this.titre = titre;
        this.anneeParution = anneeParution;
    }

    // getters /setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getAnneeParution() { return anneeParution; }
    public void setAnneeParution(int anneeParution) { this.anneeParution = anneeParution; }

    public Set<Auteur> getAuteurs() { return auteurs; }
    public void setAuteurs(Set<Auteur> auteurs) { this.auteurs = auteurs; }

    /** relation bidirectionnelle */
    public void ajouterAuteur(Auteur auteur) {
        this.auteurs.add(auteur);
        auteur.getLivres().add(this);
    }
}
