package com.example.projeto_tarefa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tarefa> tarefas = new ArrayList<>();

    public Projeto() {}

    public Projeto(Long id) {
        this.id = id;
    }

    public Projeto(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void addTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        tarefa.setProjeto(this);
    }

    public void removeTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
        tarefa.setProjeto(null);
    }

    @PrePersist
    @PreUpdate
    private void ensureTarefasHaveProjeto() {
        for (Tarefa tarefa : tarefas) {
            tarefa.setProjeto(this);
        }
    }
}
