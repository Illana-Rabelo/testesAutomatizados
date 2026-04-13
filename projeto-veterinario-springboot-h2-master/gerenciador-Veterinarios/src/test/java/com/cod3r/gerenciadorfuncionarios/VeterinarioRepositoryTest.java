package com.cod3r.gerenciadorfuncionarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;

public class VeterinarioRepositoryTest {
    
    //  Fase Red - Teste 1: Busca Exata 
@Test
void findByNomeDeveriaNaoEncontrarQuandoCaseForDiferente() {
    // Arrange
    String nomeBusca = "conceição evaristo"; 
    
    // Act
    Optional<Veterinario> result = repository.findByNome(nomeBusca);
    
    // Assert
    assertTrue(result.isEmpty()); // Espera-se vazio porque o case não bate
}

// Teste 4: Case Insensitive 
@Test
void findByNomeIgnoreCaseDeveriaRetornarVeterinarioQuandoExistir() {
    // Arrange
    String nomeBusca = "ERICA QUEIROZ PINTO"; 
    
    // Act
    Optional<Veterinario> result = repository.findByNomeIgnoreCase(nomeBusca);
    
    // Assert
    assertTrue(result.isPresent());
    assertEquals("Erica Queiroz Pinto", result.get().getNome());
}
}
