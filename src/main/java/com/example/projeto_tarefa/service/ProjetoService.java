package com.example.projeto_tarefa.service;

import com.example.projeto_tarefa.repositories.ProjetoRepository;
import com.example.projeto_tarefa.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    // CRUD Projeto
    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> getProjetoById(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto createProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public void deleteProjeto(Long id) {
        projetoRepository.deleteById(id);
    }
}
