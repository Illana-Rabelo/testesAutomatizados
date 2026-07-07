package org.iftm.gerenciadorveterinarios.servicies;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.repositories.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {
     @Mock
     private FuncionarioRepository repository;

    @InjectMocks
    private FuncionarioService service;

    @Test
    public void testFuncionarioComStatusPadrao(){
        //arrenge
        Funcionario funcionarioEntrada = new Funcionario(1,"Leonardo Andrade","gerente",5000.00,false,false);
        Funcionario funcionarioEsperado = new Funcionario(2,"Eduardo Andrade","Adiministrador",3000.00,false,true);
        funcionarioEntrada.setAtivo(true);
        
        //mock
      when(repository.save(any(Funcionario.class)))
        .thenReturn(funcionarioEsperado);

        // act
       Funcionario resultado = service.salvar(funcionarioEntrada);

     // assert
         assertEquals(funcionarioEsperado, resultado);
        assertEquals("Eduardo Andrade", resultado.getNome());
        assertEquals(2, resultado.getId());

    }
    
    @Test
    public void testarNaoDeveSalvarFuncionarioComSalarioInvalido() {

    // arrange
    Funcionario funcionario = new Funcionario(1, "Camila Martins", "gerente", -1000.00, false, true);

    // act 
    assertThrows(IllegalArgumentException.class, () -> {
        service.salvar(funcionario);
    });

    
    verify(repository, never()).save(any());
  }

  @Test
   public void testarConcederFeriasComSucesso() {

    Integer id = 1;

    Funcionario funcionario = new Funcionario(1, "Fabiana Ramos", "gerente", 5000.00, false, true);

    // mock busca
    when(repository.findById(id))
        .thenReturn(Optional.of(funcionario));

    // act
    service.concederFerias(id);

    // assert 
    assertTrue(funcionario.isEmFerias());

    verify(repository).save(funcionario);
}
}
