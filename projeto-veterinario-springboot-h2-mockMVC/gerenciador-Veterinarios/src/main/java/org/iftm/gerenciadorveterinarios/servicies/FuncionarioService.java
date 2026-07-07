/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.iftm.gerenciadorveterinarios.servicies;

import java.util.List;
import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> ListarTodos(){
        return repository.findAll();
    }

  public Funcionario salvar(Funcionario funcionarioEntrada) {

    if (funcionarioEntrada.getSalario() < 0) {
        throw new IllegalArgumentException("Salário inválido");
    }

    funcionarioEntrada.setAtivo(true);

    return repository.save(funcionarioEntrada);
}
  
public void concederFerias(Integer id) {

    Funcionario funcionario = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));

    funcionario.setEmFerias(true);

    repository.save(funcionario);
}

public Funcionario buscarPorId(Integer id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
}

public List<Funcionario> buscarPorNome(String nome) {
    return repository.findByNomeContainingIgnoreCase(nome);
}
}
