package org.iftm.gerenciadorveterinarios.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository repository;

    @Test
    public void testeBuscarVeterinarioComIdExistenteCorretamente() {

        int idExistente = 1;
        String nomeEsperado = "Conceição Evaristo";
        String emailEsperado = "conceicao@gmail.com";

        Veterinario veterinarioEncontrado = repository.getById(idExistente);

        assertNotNull(veterinarioEncontrado);
        assertEquals(nomeEsperado, veterinarioEncontrado.getNome());
        assertEquals(emailEsperado, veterinarioEncontrado.getEmail());

    }

    @Test
    public void testeBuscaDeVeterinarioExistentePeloNomeMinusculoSemCaseSensentiveDeveFalhar() {

        String nomeEsperado = "marcos oliveira";

        int quantidadeEsperada = 0;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

        assertNotNull(veterinarioExistentes);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeBuscaDeVeterinarioExistentePeloNomeMaiusculoSemCaseSensentiveDeveFalhar() {

        String nomeEsperado = "MARCOS OLIVEIRA";
        int quantidadeEsperada = 0;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

        assertNotNull(veterinarioExistentes);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeBuscaDeVeterinarioNãoExistenteComCaseSensitiveDeveFalhar() {

        String nomeEsperado = "giovanny morais";
        int quantidadeEsperada = 0;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

        assertNotNull(veterinarioExistentes);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeDeBuscaDeVeterinarioExistenteComCaseSensitive() {
        String nomeEsperado = "amanDa RIbeiro";
        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistentes = repository.findByNomeIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

        assertNotNull(veterinarioExistentes);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeDeBuscaDeNomePelaSilabaTesteDeveFalhar() {
        String nomeExistente = "co";

        String nomeEsperado = "Conceição Evaristo";
        String nomeEsperado2 = "Marcos Oliveira";

        List<Veterinario> veterinarioExistentes = repository.findByNomeContainsIgnoreCase(nomeExistente);

        assertNotNull(veterinarioExistentes);

        assertThrows(AssertionError.class, () -> assertEquals(nomeEsperado2, veterinarioExistentes));
        assertThrows(AssertionError.class, () -> assertEquals(nomeEsperado, veterinarioExistentes));
    }

    @Test
    public void testeDeBuscaDoVeterinarioBrunoPelaSilaba() {
        String nomeEsperado = "Bruno";
        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistente = repository.findByNomeContainsIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistente.size();

        assertNotNull(veterinarioExistente);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeDeBuscaDeParametroComStringVazia() {
        String nomeEsperado = "";

        int quantidadeEsperada = 12;

        List<Veterinario> veterinarioExistente = repository.findByNomeContainsIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistente.size();

        assertNotNull(veterinarioExistente);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeDeBuscaDeSalarioSuperiorAoValorInformado() {

        Double SalarioEsperado = 3200.0;
        int quantidadeEsperada = 11;

        List<Veterinario> veterinarioExistente = repository
                .findBySalarioGreaterThan(BigDecimal.valueOf(SalarioEsperado));
        int quantidadeExistente = veterinarioExistente.size();

        assertNotNull(veterinarioExistente);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeDeBuscaDeSalarioInferiorAoValorInformado() {

        BigDecimal salarioReferencia = BigDecimal.valueOf(3000.0);
        int quantidadeEsperada = 0;

        List<Veterinario> veterinariosEncontrados = repository.findBySalarioLessThan(salarioReferencia);

        // Assert
        assertNotNull(veterinariosEncontrados);
        assertEquals(quantidadeEsperada, veterinariosEncontrados.size());
    }

    @Test
    public void testeDeBuscaSalarioEntreOsValorInformado() {

        Double SalarioEsperado = 3000.0;
        Double SalarioEsperado2 = 5000.0;

        int quantidadeEsperada = 9;

        List<Veterinario> veterinarioExistente = repository.findBySalarioBetween(BigDecimal.valueOf(SalarioEsperado),
                BigDecimal.valueOf(SalarioEsperado2));
        int quantidadeExistente = veterinarioExistente.size();

        assertNotNull(veterinarioExistente);
        assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeValidaUpdate() {
        Veterinario v = repository.findById(1).orElseThrow();

        v.setNome("Leonardo");
        repository.save(v);

        Veterinario vAtualizado = repository.findById(1).get();

        assertEquals("Leonardo", vAtualizado.getNome());
    }

}
