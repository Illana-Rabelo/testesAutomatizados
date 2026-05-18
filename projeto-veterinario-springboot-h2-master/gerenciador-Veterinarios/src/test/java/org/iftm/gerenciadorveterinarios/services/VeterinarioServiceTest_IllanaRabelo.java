package org.iftm.gerenciadorveterinarios.services; 

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VeterinarioServiceTest_IllanaRabelo { 

    @InjectMocks
    private VeterinarioService service;

    @Mock
    private VeterinarioRepository repository;

    private Veterinario vet1;
    private Veterinario vet2;

    @BeforeEach
    void setUp() {
        vet1 = new Veterinario();
        vet1.setId(1L);
        vet1.setNome("Ana Silva");
        vet1.setEmail("ana@email.com");
        vet1.setEspecialidade("Gatos");
        vet1.setSalario(5500.0);

        vet2 = new Veterinario();
        vet2.setId(2L);
        vet2.setNome("Carlos Silva");
        vet2.setEmail("carlos@email.com");
        vet2.setEspecialidade("Cães");
        vet2.setSalario(6000.0);
    }

    @Test
    void buscaVeterinariosComParteNomeDeveriaRetornarListaComDoisVeterinariosQuandoTermoForSilva() {
        String termoBusca = "Silva";
        List<Veterinario> listaFake = new ArrayList<>();
        listaFake.add(vet1);
        listaFake.add(vet2);
        
        when(repository.findByNomeContainingIgnoreCase(termoBusca)).thenReturn(listaFake);

        List<Veterinario> resultado = service.buscaVeterinariosComParteNome(termoBusca);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findByNomeContainingIgnoreCase(termoBusca);
    }

    @Test
    void deveLancarExcecaoAoApagarQuandoIdNaoExistir() {
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.apagarPeloId(idInexistente);
        });

        verify(repository, never()).delete(any(Veterinario.class));
    }
}
