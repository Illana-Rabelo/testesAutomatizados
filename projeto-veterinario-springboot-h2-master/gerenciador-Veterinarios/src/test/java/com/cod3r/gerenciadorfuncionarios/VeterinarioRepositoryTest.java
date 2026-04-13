package com.cod3r.gerenciadorfuncionarios;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository repository;

    // Ciclo 1 - Teste 1: Busca Exata
    @Test
    void findByNomeDeveriaRetornarVazioQuandoCaseNaoBater() {
        // Arrange
        String nomeBusca = "pedro";

        // Act
        Optional<Veterinario> result = repository.findByNome(nomeBusca);

        // Assert
        assertTrue(result.isEmpty());
    }

    // Teste 4: Case Insensitive
    @Test
    void findByNomeIgnoreCaseDeveriaRetornarVeterinarioQuandoExistir() {
        // Act
        Optional<Veterinario> result = repository.findByNomeIgnoreCase("pedro");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("PEDRO", result.get().getNome());
    }

    // Ciclo 2 - Teste 2: Busca por Trecho (Contains)
    @Test
    void findByNomeContainingIgnoreCaseDeveriaRetornarListaQuandoTrechoExistir() {
        String trecho = "ei";

        // Act
        List<Veterinario> result = repository.findByNomeContainingIgnoreCase(trecho);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByNomeContainingIgnoreCaseDeveriaRetornarTodosQuandoStringVazia() {
        // Act
        List<Veterinario> result = repository.findByNomeContainingIgnoreCase("");

        // Assert
        assertEquals(2, result.size());
    }

    // Ciclo 3 - Teste 3: Busca por Salário
    // Cenário A: Salário superior a um valor
    @Test
    void findBySalarioGreaterThanDeveriaRetornarVeterinariosComSalarioMaior() {
        // Act
        List<Veterinario> result = repository.findBySalarioGreaterThan(5000.0);

        // Assert
        assertEquals(1, result.size());  // apenas o Pedro
    }

    // Cenário B: Salário inferior a um valor
    @Test
    void findBySalarioLessThanDeveriaRetornarVeterinariosComSalarioMenor() {
        // Act
        List<Veterinario> result = repository.findBySalarioLessThan(4000.0);

        // Assert
        assertEquals(1, result.size()); // apenas a Conceição 
    }

    // Cenário C: Faixa salarial (Mínimo e Máximo)
    @Test
    void findBySalarioBetweenDeveriaRetornarVeterinariosNaFaixa() {
        // Act
        List<Veterinario> result = repository.findBySalarioBetween(3000.0, 7000.0);

        // Assert
        assertEquals(2, result.size()); // Ambos estão nesta faixa
    }
}
