package org.iftm.gerenciadorveterinarios.repositories;

import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    
    // Para o Teste 1 
    Optional<Veterinario> findByNome(String nome);
    
    // Para o Teste 4 
    Optional<Veterinario> findByNomeIgnoreCase(String nome);
}
