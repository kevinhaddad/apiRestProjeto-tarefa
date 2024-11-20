package com.example.projeto_tarefa.service;

import com.example.projeto_tarefa.repositories.TarefaRepository;
import com.example.projeto_tarefa.model.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    // CRUD Tarefa
    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> getTarefaById(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa createTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deleteTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}
