package com.bibliotheque.controller;

import com.bibliotheque.model.Auteur;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.AuteurRepository;
import com.bibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    @GetMapping
    public String afficherLivres(Model model) {
        List<Livre> livres = livreRepository.findAll();
        model.addAttribute("livres", livres);
        model.addAttribute("livre", new Livre());
        return "livre";
    }

    @PostMapping("/ajouter")
    public String ajouterLivre(
            @RequestParam("isbn") String isbn,
            @RequestParam("titre") String titre,
            @RequestParam("anneeParution") int anneeParution) {

        if (!livreRepository.existsById(isbn.trim())) {
            Livre nouveauLivre = new Livre(isbn.trim(), titre.trim(), anneeParution);
            livreRepository.save(nouveauLivre);
        }
        return "redirect:/livre";
    }

    @GetMapping("/affecterAuteur")
    public String afficherFormulaireAffectation(Model model) {
        model.addAttribute("livres", livreRepository.findAll());
        model.addAttribute("auteurs", auteurRepository.findAll());
        return "affectation";
    }

    @PostMapping("/affecterAuteur")
    public String affecterAuteur(
            @RequestParam("livreIsbn") String livreIsbn,
            @RequestParam("auteurId") Long auteurId) {

        Optional<Livre> livreOpt = livreRepository.findByIsbnWithAuteurs(livreIsbn);
        Optional<Auteur> auteurOpt = auteurRepository.findById(auteurId);

        if (livreOpt.isPresent() && auteurOpt.isPresent()) {
            Livre livre = livreOpt.get();
            Auteur auteur = auteurOpt.get();

            boolean dejaAssocie = livre.getAuteurs().stream()
                    .anyMatch(a -> a.getId().equals(auteurId));

            if (!dejaAssocie) {
                livre.ajouterAuteur(auteur);
                auteur.ajouterPoints(1);
                livreRepository.save(livre);
                auteurRepository.save(auteur);
            }
        }
        return "redirect:/livre/affecterAuteur";
    }
}
