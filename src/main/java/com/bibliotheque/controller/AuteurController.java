package com.bibliotheque.controller;

import com.bibliotheque.model.Auteur;
import com.bibliotheque.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auteur")
public class AuteurController {

    @Autowired
    private AuteurRepository auteurRepository;

    /** affiche liste des auteurs */
    @GetMapping
    public String afficherAuteurs(Model model) {
        List<Auteur> auteurs = auteurRepository.findAll();
        model.addAttribute("auteurs", auteurs);
        model.addAttribute("auteur", new Auteur());
        return "auteur";
    }

    /** ajout d'un auteur depuis le formulaire */
    @PostMapping("/ajouter")
    public String ajouterAuteur(@RequestParam("nomPrenom") String nomPrenom) {
        Auteur nouvelAuteur = new Auteur(nomPrenom.trim());
        auteurRepository.save(nouvelAuteur);
        // Pattern POST-Redirect-GET : evite la re-soumission
        return "redirect:/auteur";
    }
}
