package org.iftm.gerenciadorveterinarios.entities;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;

@Entity
@Table(name = "tb_veterinario")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String especialidade;

    private Double salario;

    private Instant dataNascimento;

    public Veterinario() {
    }

    public Veterinario(Integer id, String nome, String email, String especialidade, Double salario,
            Instant dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.especialidade = especialidade;
        this.salario = salario;
        this.dataNascimento = dataNascimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
