package org.iftm.gerenciadorveterinarios.servicies;


import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuncionarioServiceITest {
    

    @Autowired
    private FuncionarioService service;

    @Test
    public void testFuncionarioComStatusPadrao(){
        //arrenge
        Funcionario funcionarioEntrada = new Funcionario(1,"Camila Martins","veterinario",5100.00,false,false);
      
        
     

        // act
       Funcionario resultado = service.salvar(funcionarioEntrada);

     // assert
     assertTrue(resultado.isAtivo()); 
     assertEquals("Camila Martins", resultado.getNome());
     assertEquals("veterinario", resultado.getCargo());
     assertEquals(5100.00, resultado.getSalario());

    }
    
    @Test
    public void testarNaoDeveSalvarFuncionarioComSalarioInvalido() {

    // arrange
    Funcionario funcionario = new Funcionario(1, "Camila Martins", "veterinario", -1000.00, false, true);

    // act 
    assertThrows(IllegalArgumentException.class, () -> {
        service.salvar(funcionario);
    });

  }

  @Test
   public void testarConcederFeriasComSucesso() {


   // act
   service.concederFerias(1);

   // assert
   Funcionario atualizado = service.buscarPorId(1);
   assertTrue(atualizado.isEmFerias());

    }

    @Test
public void testarConcederFeriasFuncionarioInexistente() {
    // Tenta buscar um ID que não existe 
    assertThrows(RuntimeException.class, () -> {
        service.concederFerias(999);
    });
}
}

