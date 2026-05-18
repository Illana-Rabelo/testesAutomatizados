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
    
        animalInput = new Animal(null, "Rex", "Cachorro", 3, false);
        animalSalvo = new Animal(1L, "Rex", "Cachorro", 3, true); 
    }


    @Test
    void cadastrarDeveriaForcarStatusInternadoComoTrueAoSalvar() {
        when(repository.save(any(Animal.class))).thenReturn(animalSalvo);

        Animal resultado = service.cadastrar(animalInput);

        assertNotNull(resultado);
        assertTrue(resultado.getInternado(), "Todo animal cadastrado deve entrar como internado = true");
        verify(repository, times(1)).save(animalInput);
    }


    @Test
    void cadastrarDeveriaLancarIllegalArgumentExceptionQuandoIdadeForNegativa() {
    
        Animal animalInvalido = new Animal(null, "Frajola", "Gato", -2, false);

       
        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(animalInvalido);
        });


        verify(repository, never()).save(any(Animal.class));
    }


    @Test
    void darAltaDeveriaMudarStatusParaFalseEPreencherId() {
        Long idExistente = 1L;
        when(repository.findById(idExistente)).thenReturn(Optional.of(animalSalvo));
        when(repository.save(any(Animal.class))).thenReturn(animalSalvo);

        Animal resultado = service.darAlta(idExistente);

        assertNotNull(resultado);
        assertFalse(resultado.getInternado(), "O status do animal deveria mudar para false ao receber alta");
        verify(repository).save(animalSalvo);
    }
}