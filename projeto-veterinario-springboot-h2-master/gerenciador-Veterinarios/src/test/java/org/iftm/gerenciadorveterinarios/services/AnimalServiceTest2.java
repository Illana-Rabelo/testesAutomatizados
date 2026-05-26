package org.iftm.gerenciadorveterinarios.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.iftm.gerenciadorveterinarios.servicies.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @InjectMocks
    private AnimalService service;

    @Mock
    private AnimalRepository repository;

    private Animal animalInput;
    private Animal animalSalvo;

    @BeforeEach
    void setUp() {
        // Para o Ciclo 1: Instanciamos com o status invertido (internado = false)
        animalInput = new Animal(null, "Hedwig", "Coruja", 4, false);
        animalSalvo = new Animal(1L, "Hedwig", "Coruja", 4, true); // O esperado final é true
    }

    // --- CICLO 1: Regra de Negócio no Cadastro (Status Padrão) ---
    @Test
    void cadastrarDeveriaForcarStatusInternadoComoTrueAoSalvar() {
        when(repository.save(any(Animal.class))).thenReturn(animalSalvo);

        Animal resultado = service.cadastrar(animalInput);

        assertNotNull(resultado);
        assertTrue(resultado.getInternado(), "O status padrão ao cadastrar deve ser internado = true");
        verify(repository, times(1)).save(animalInput);
    }

    // --- CICLO 2: Proteção de Domínio (Validação) ---
    @Test
    void cadastrarDeveriaLancarIllegalArgumentExceptionQuandoIdadeForNegativa() {
        Animal animalInvalido = new Animal(null, "Fang", "Cão", -1, false);

        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(animalInvalido);
        });

        // Valida que barrou o salvamento e nunca chamou o repository
        verify(repository, never()).save(any(Animal.class));
    }

    // --- CICLO 3: Regra de Negócio de Atualização (Ação Específica) ---
    @Test
    void darAltaDeveriaMudarStatusParaFalseAoBuscarPorIdValido() {
        Long idExistente = 1L;
        when(repository.findById(idExistente)).thenReturn(Optional.of(animalSalvo));
        when(repository.save(any(Animal.class))).thenReturn(animalSalvo);

        Animal resultado = service.darAlta(idExistente);

        assertNotNull(resultado);
        assertFalse(resultado.getInternado(), "O status deve mudar para false após a alta");
        verify(repository).save(animalSalvo);
    }
}