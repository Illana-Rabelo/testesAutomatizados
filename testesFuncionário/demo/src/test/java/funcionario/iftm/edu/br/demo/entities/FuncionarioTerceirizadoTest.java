package funcionario.iftm.edu.br.demo.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioTerceirizadoTest {

    // --- TESTES DE SUCESSO (Caminho Feliz) ---

    @Test
    void testarModificarDespesaGeraPagamentoValido() {
        // Cenário: 100h * R$ 20.00 = 2000.00
        // Despesa: 500.00 -> Bônus (110%): 550.00
        // Total esperado: 2550.00 (Dentro do limite de 10000) [cite: 5, 8]
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Illana Terc", 100, 20.0, 100.0);
        
        ft.setDespesasAdicionais(500.0);
        
        assertEquals(2550.0, ft.calcularPagamento(), 0.01);
    }

    // --- TESTES DE VALIDAÇÃO: DESPESA ADICIONAL (Máx R$ 1000.00) ---

    @Test
    void testarModificarDespesaAcimaDoLimiteGeraErro() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Dev", 100, 20.0, 100.0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> ft.setDespesasAdicionais(1001.0));
        // Limite de despesa: R$ 1000.00 [cite: 13]
        assertEquals("As despesas adicionais não podem ultrapassar R$ 1000.00.", e.getMessage());
    }

    // --- TESTES DE VALIDAÇÃO: LIMITE DE SALÁRIO COM DESPESA ---

    @Test
    void testarModificarDespesageraPagamentoacimaLimiteSalarioGerandoErro() {
        // Cenário: Funcionario já ganha R$ 9500.00 [cite: 8]
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Expert", 100, 95.0, 100.0);
        
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            // Adicionar 500 de despesa geraria +550 de bônus = 10050.00 (Estoura o teto de 10000) [cite: 5, 8]
            ft.setDespesasAdicionais(500.0);
        });
        
        // Mantendo o padrão de mensagem que usamos na classe pai [cite: 8]
        assertEquals("O pagamento total com bônus não pode ultrapassar o teto de R$ 10000.00.", e.getMessage());
    }

    // --- TESTE DE HERANÇA (Garantindo que as regras do pai ainda valem) ---

    @Test
    void testarModificarHorasInvalidasNoTerceirizadoGeraErro() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Estagiário Terc", 40, 20.0, 50.0);
        
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            ft.setHorasTrabalhadas(10); // Regra herdada: mínimo 20h mensais [cite: 11]
        });
        
        assertEquals("O número de horas mensais deve estar entre 20 e 160.", e.getMessage());
    }
}

