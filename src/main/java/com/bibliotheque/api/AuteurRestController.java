package com.bibliotheque.api;

import com.bibliotheque.model.Auteur;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.AuteurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.bibliotheque.repository.LivreRepository;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Auteurs", description = "Gestion des auteurs")
@RestController
@RequestMapping("/api/auteurs")
public class AuteurRestController {

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private LivreRepository livreRepository;
    @Operation(summary = "Lister tous les auteurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des auteurs récupérée avec succès")
    })
    @GetMapping
    public List<Auteur> getAll() {
        return auteurRepository.findAll();
    }

    @Operation(summary = "Ajouter un auteur")
    @PostMapping
    public Auteur create(@RequestBody Auteur auteur) {
        return auteurRepository.save(auteur);
    }

    @Operation(summary = "Supprimer un auteur")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Auteur> auteurOpt = auteurRepository.findById(id);

        if (auteurOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Auteur non trouvé avec l'id : " + id);
        }
        Auteur auteur = auteurOpt.get();

        for (Livre livre : auteur.getLivres()) {
            livre.getAuteurs().remove(auteur);
            livreRepository.save(livre);
        }
        auteur.getLivres().clear();
        auteurRepository.save(auteur);
        auteurRepository.deleteById(id);
        return ResponseEntity.ok("Auteur supprimé avec succès");

    }
}