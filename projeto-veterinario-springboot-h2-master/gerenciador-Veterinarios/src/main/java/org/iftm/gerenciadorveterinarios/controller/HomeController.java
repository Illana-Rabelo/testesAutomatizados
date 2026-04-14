package org.iftm.gerenciadorveterinarios.controller;

import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private VeterinarioService servico;

    @GetMapping("/home")
    public String home(@RequestParam(value = "nome", required = false) Optional<String> nome, Model model) {
        List<Veterinario> veterinarios;
        
        if (nome.isPresent() && !nome.get().isEmpty()) {
            veterinarios = servico.buscaVeterinariosComParteNome(nome.get());
        } else {
            veterinarios = servico.buscaTodosVeterinarios();
        }
        
        model.addAttribute("veterinarios", veterinarios);
        return "home";
    }
}
