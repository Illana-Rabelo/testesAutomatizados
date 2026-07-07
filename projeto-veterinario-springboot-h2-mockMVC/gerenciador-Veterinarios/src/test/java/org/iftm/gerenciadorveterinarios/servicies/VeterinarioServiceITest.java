package org.iftm.gerenciadorveterinarios.servicies;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VeterinarioServiceITest {
    

    

    @Autowired
    private VeterinarioService service;


     @Test
     public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioCorreto(){
         //arrange
         Integer idExistente = 2;
         String nomeEsperado = "Erica Queiroz Pinto";
         Veterinario veterinarioEsperado = new Veterinario(2,nomeEsperado, "","",BigDecimal.valueOf(0));

        //act
         Optional <Veterinario> vet = service.buscaVeterinariosPeloId(idExistente);
         Veterinario vetRetornado = vet.get();
         //assert

         assertTrue(vet.isPresent());
         assertEquals(nomeEsperado, vetRetornado.getNome());

     }
 
     @Test
     public void testarbuscaVeterinariosComParteNome(){
      //arrange
      String nomeExistente =  "Erica Queiroz Pinto";
      String nomeExistente2 =  "Bruno Queiroz Carvalho";
      String parteNome = "Queiroz";

      Veterinario veterinarioEsperado = new Veterinario(2,nomeExistente, "","",BigDecimal.valueOf(0));
      Veterinario veterinarioEsperado2 = new Veterinario(3,nomeExistente2, "","",BigDecimal.valueOf(0));

      List<Veterinario> veterinarios = new ArrayList<>();
      veterinarios.add(veterinarioEsperado);
      veterinarios.add(veterinarioEsperado2);

      
      //act
      List<Veterinario> vet = service.buscaVeterinariosComParteNome(parteNome);
      
      //assert
      assertEquals(2,vet.size());
      assertEquals(nomeExistente, vet.get(0).getNome());
      assertEquals(nomeExistente2, vet.get(1).getNome());

     }

     @Test
     public void testarDeveLancarExcecaoAoApagarQuandoIdNaoExistir(){
       //arrange
        Integer idInexistente = 999;
       
        //assert
        assertThrows(RuntimeException.class, ()->{
           service.deletar(idInexistente);
        });

   
     }


    @Test
public void testarApagarRealmenteApagaRegistro() {
    Integer id = 2;
    service.apagar(service.buscaVeterinariosPeloId(id).get());

    // Verifica se a busca retorna vazio
    assertTrue(service.buscaVeterinariosPeloId(id).isEmpty());
}
}
