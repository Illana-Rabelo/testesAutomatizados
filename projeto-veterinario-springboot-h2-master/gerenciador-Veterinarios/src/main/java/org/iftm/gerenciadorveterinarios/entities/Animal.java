package org.iftm.gerenciadorveterinarios.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
    private Integer idade;
    private Boolean internado;

    // Construtores
    public Animal() {}

    public Animal(Long id, String nome, String especie, Integer idade, Boolean internado) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.internado = internado;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
    public Boolean getInternado() { return internado; }
    public void setInternado(Boolean internado) { this.internado = internado; }
}