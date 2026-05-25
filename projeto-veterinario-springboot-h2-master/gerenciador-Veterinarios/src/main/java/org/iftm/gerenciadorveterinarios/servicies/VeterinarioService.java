package org.iftm.gerenciadorveterinarios.servicies;

import java.util.List;
import java.util.Optional;
import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioService {
    
    @Autowired
    private VeterinarioRepository repositorio; // Mantido o padrão original com 'o'

    @Transactional(readOnly = true)
    public List<Veterinario> buscaVeterinariosComParteNome(String nome){
        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

  // Adicione ou corrija para este nome (com o "s" no final que a HomeController está pedindo na linha 30)
    @Transactional(readOnly = true)
    public List<Veterinario> buscaTodosVeterinarios(){
        return repositorio.findAll();
    }

    // Adicione ou corrija para este nome (com o "Id" maiúsculo que a VeterinarioController pede nas linhas 46 e 64)
    @Transactional(readOnly = true)
    public Optional<Veterinario> buscaVeterinariosPeloId(Long id){
        return repositorio.findById(id);
    }

    @Transactional
    public Veterinario salvar(Veterinario veterinario){
        if (veterinario.getSalario() != null && veterinario.getSalario() < 1518.00) {
            throw new IllegalArgumentException("O salário não pode ser inferior ao salário mínimo.");
        }
        return repositorio.save(veterinario);
    }

    // Método que o VeterinarioController usa na linha 54
    @Transactional
    public void apagar(Veterinario veterinario){
        repositorio.delete(veterinario);
    }

    // Método com ID exigido pelo Desafio 2 do PDF
    @Transactional
    public void apagar(Long id) {
        Veterinario vet = repositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Veterinário não encontrado para exclusão."));
        repositorio.delete(vet);
    }

    @Transactional
    public Veterinario concederAumento(Long id, Double valorAumento) {
        Veterinario vet = repositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Veterinário não encontrado."));
        
        vet.setSalario(vet.getSalario() + valorAumento);
        return repositorio.save(vet);
    }
}