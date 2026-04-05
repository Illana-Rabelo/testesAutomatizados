package funcionario.iftm.edu.br.demo.entities;

public class Funcionario {
    protected String nome;
    protected int horasTrabalhadas;
    protected double valorHora;

    // Construtor usando os setters para garantir que ninguém nasça "fora da lei"
    public Funcionario(String nome, int horasTrabalhadas, double valorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
        // Valida tudo de uma vez após os valores estarem atribuídos
        validarLimitesPagamento(horasTrabalhadas, valorHora);
    }

    // Regra de negócio: Horas * Valor [cite: 5]
    public double calcularPagamento() {
        return horasTrabalhadas * valorHora;
    }

    // --- SETTERS COM PROGRAMAÇÃO DEFENSIVA ---

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        // Limites mensais: 20 a 160 [cite: 11]
        if (horasTrabalhadas < 20 || horasTrabalhadas > 160) {
            throw new IllegalArgumentException("O número de horas mensais deve estar entre 20 e 160.");
        }
        validarLimitesPagamento(horasTrabalhadas, this.valorHora);
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public void setValorHora(double valorHora) {
        // Limites: 1% (15.18) a 10% (151.80) do salário mínimo [cite: 12]
        double min = 1518.0 * 0.01;
        double max = 1518.0 * 0.10;

        if (valorHora < min || valorHora > max) {
            throw new IllegalArgumentException("O valor por hora deve ser entre R$ 15.18 e R$ 151.80.");
        }
        validarLimitesPagamento(this.horasTrabalhadas, valorHora);
        this.valorHora = valorHora;
    }

    // Método auxiliar para validar os limites totais (R$ 1518 a R$ 10000) [cite: 7,
    // 8]
    private void validarLimitesPagamento(int horas, double valor) {
        double total = horas * valor;
        if (total < 1518.0) {
            throw new IllegalArgumentException(
                    "O pagamento total não pode ser inferior ao salário mínimo de R$ 1518.00.");
        }
        if (total > 10000.0) {
            throw new IllegalArgumentException("O pagamento total não pode ultrapassar o teto de R$ 10000.00.");
        }
    }

    // --- GETTERS ---

    public String getNome() {
        return nome;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }
}
