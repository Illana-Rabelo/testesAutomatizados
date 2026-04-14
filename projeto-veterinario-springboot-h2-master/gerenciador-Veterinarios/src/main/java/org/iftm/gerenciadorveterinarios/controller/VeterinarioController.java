package org.iftm.gerenciadorveterinarios.controller;

import java.util.Optional;
import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VeterinarioController {
    
    @Autowired
    private VeterinarioService servico;

    @GetMapping("/find")
    public String veterinariosFind(Model model) {
        return "findVeterinarioForm";   
    }
    
    @GetMapping("/form")
    public String veterinariosForm(Veterinario veterinario) {       
        return "addVeterinarioForm";
    }

    @PostMapping("/add")
    public String novo(Veterinario veterinario) {
        servico.salvar(veterinario);
        return "redirect:/home";
    }

    @GetMapping("form/{id}")
    public String updateForm(Model model, @PathVariable Long id) {
        Optional<Veterinario> veterinario = servico.buscaVeterinariosPeloId(id);
        if (veterinario.isPresent()) {
            model.addAttribute("veterinario", veterinario.get());
            return "atualizaVeterinarioForm";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("update/{id}")
    public String alterarProduto(Veterinario veterinario, @PathVariable Long id) { 
        servico.salvar(veterinario);
        return "redirect:/home";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) { 
        Optional<Veterinario> veterinario = servico.buscaVeterinariosPeloId(id);
        if (veterinario.isPresent()) {
            servico.apagar(veterinario.get());
        }        
        return "redirect:/home";
    }
}
