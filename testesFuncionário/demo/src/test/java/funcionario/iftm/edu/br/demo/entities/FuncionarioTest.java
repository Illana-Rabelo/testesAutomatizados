package funcionario.iftm.edu.br.demo.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioTest {

    @Test
    void testarModificarHorasComValoresValidosProduzPagamentoEsperado() {
     
        Funcionario f = new Funcionario("Illana", 100, 20.0); 
        assertEquals(2000.0, f.calcularPagamento(), 0.01); 
    }

    @Test
    void testarModificarValorPorHoraComValoresValidosProduzPagamentoEsperado() {
        Funcionario f = new Funcionario("Dev", 160, 15.18);
        assertEquals(2428.80, f.calcularPagamento(), 0.01); 

    // Horas trabalhadas        

    @Test
    void testarModificarHorasAbaixoLimiteInferiorGeraErro() {
        Funcionario f = new Funcionario("Teste", 40, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setHorasTrabalhadas(19));
        // Mensagem esperada [cite: 11]
        assertEquals("O número de horas mensais deve estar entre 20 e 160.", e.getMessage());
    }

    @Test
    void testarModificarHorasAcimaLimiteSuperiorGeraErro() {
        Funcionario f = new Funcionario("Teste", 40, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setHorasTrabalhadas(161));
        assertEquals("O número de horas mensais deve estar entre 20 e 160.", e.getMessage());
    }


    // Valor por hora 

    @Test
    void testarModificarValorPorHoraAbaixoLimiteInferiorGeraErro() {
        Funcionario f = new Funcionario("Teste", 100, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setValorHora(15.0));
        // Regra: 1% de 1518 é 15.18 [cite: 12]
        assertEquals("O valor por hora deve ser entre R$ 15.18 e R$ 151.80.", e.getMessage());
    }

    @Test
    void testarModificarValorPorHoraAcimaLimiteSuperiorGeraErro() {
        Funcionario f = new Funcionario("Teste", 100, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setValorHora(152.0));
        assertEquals("O valor por hora deve ser entre R$ 15.18 e R$ 151.80.", e.getMessage());
    }

    // Limites de pagamento total

    @Test
    void testarModificarHorasComValoresValidosGeraPagamentoAbaixoMinimoGeraErro() {
        Funcionario f = new Funcionario("Teste", 100, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setHorasTrabalhadas(20));
        // Pagamento mínimo: R$ 1518.00 [cite: 7]
        assertEquals("O pagamento total não pode ser inferior ao salário mínimo de R$ 1518.00.", e.getMessage());
    }

    @Test
    void testarModificarValorPorHoraComValoresValidosGeraPagamentoAcimaLimiteGeraErro() {
        Funcionario f = new Funcionario("Teste", 40, 20.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> f.setValorHora(150.0));
        // Teto: R$ 10000.00 [cite: 8]
        assertEquals("O pagamento total não pode ultrapassar o teto de R$ 10000.00.", e.getMessage());
    }
}
}