package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {

    @GetMapping
    public String afficherMenu(Model model) {
        model.addAttribute("nom", "Mme ghada");
        model.addAttribute("annee", "JavaEE 2025");
        return "menu";
    }
}
