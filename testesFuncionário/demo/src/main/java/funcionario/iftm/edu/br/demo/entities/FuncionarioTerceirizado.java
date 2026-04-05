package funcionario.iftm.edu.br.demo.entities;

public class FuncionarioTerceirizado extends Funcionario {
    private double despesasAdicionais; 

    public FuncionarioTerceirizado(String nome, int horasTrabalhadas, double valorHora, double despesasAdicionais) {
        super(nome, horasTrabalhadas, valorHora);
        this.despesasAdicionais = despesasAdicionais;
    }

    @Override
    public double calcularPagamento() { 
        // Pagamento base + 110% da despesa adicional [cite: 5]
        return super.calcularPagamento() + (despesasAdicionais * 1.1);
    }

    public void setDespesasAdicionais(double despesasAdicionais) { 
        if (despesasAdicionais > 1000.0) { 
            throw new IllegalArgumentException("As despesas adicionais não podem ultrapassar R$ 1000.00.");
        }
        
        // Validação do teto de 10k considerando o bônus novo
        double totalComBonus = super.calcularPagamento() + (despesasAdicionais * 1.1);
        if (totalComBonus > 10000.0) { 
            throw new IllegalArgumentException("O pagamento total com bônus não pode ultrapassar o teto de R$ 10000.00.");
        }
        
        this.despesasAdicionais = despesasAdicionais;
    }
}
