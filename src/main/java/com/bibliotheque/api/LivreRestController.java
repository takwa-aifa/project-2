package com.bibliotheque.api;

import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.LivreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Livres", description = "Gestion des livres")
@RestController
@RequestMapping("/api/livres")
public class LivreRestController {

    @Autowired
    private LivreRepository livreRepository;

    @Operation(summary = "Lister tous les livres")
    @GetMapping
    public List<Livre> getAll() {
        return livreRepository.findAll();
    }

    @Operation(summary = "Ajouter un livre")
    @PostMapping
    public Livre create(@RequestBody Livre livre) {
        return livreRepository.save(livre);
    }

    @Operation(summary = "Supprimer un livre")
    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable String isbn) {
        livreRepository.deleteById(isbn);
    }
}