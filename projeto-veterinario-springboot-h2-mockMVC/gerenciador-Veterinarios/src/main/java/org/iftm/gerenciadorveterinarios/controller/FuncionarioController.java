package org.iftm.gerenciadorveterinarios.controller;


import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.servicies.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<Funcionario>> ListarTodos() {
        return ResponseEntity.ok(service.ListarTodos());
    }
    
     @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }
    @PostMapping
    public ResponseEntity<Funcionario> salvar(@RequestBody Funcionario funcionario) {
        Funcionario novoFuncionario = service.salvar(funcionario);
        return ResponseEntity.ok(novoFuncionario);
    }


    @PutMapping("/{id}/ferias")
    public ResponseEntity<String> concederFerias(@PathVariable Integer id) {
        service.concederFerias(id);
        return ResponseEntity.ok("Férias concedidas com sucesso.");
    }

    
}