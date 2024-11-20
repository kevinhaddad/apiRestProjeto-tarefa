package com.example.projeto_tarefa.controller;

import com.example.projeto_tarefa.model.Projeto;
import com.example.projeto_tarefa.model.Tarefa;
import com.example.projeto_tarefa.service.ProjetoService;
import com.example.projeto_tarefa.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;
    private final ProjetoService projetoService;

    @Autowired
    public TarefaController(TarefaService tarefaService, ProjetoService projetoService) {
        this.tarefaService = tarefaService;
        this.projetoService = projetoService;
    }

    @Tag(name = "GET")
    @Operation(summary = "Get all tasks", description = "Retrieve all tasks")
    @GetMapping(value = "/all", produces = {"application/json", "application/xml"})
    public List<Tarefa> getAllTarefas() {
        return tarefaService.getAllTarefas();
    }

    @Tag(name = "GET")
    @Operation(summary = "Get task by ID", description = "Retrieve a task by its ID")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaService.getTarefaById(id);
        return tarefa.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Tag(name = "POST")
    @Operation(summary = "Create a task", description = "Create a new task")
    @PostMapping(value = "/", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createTarefa(@RequestBody Tarefa tarefa) {
        try {
            // Verifica se o projeto associado está salvo
            Projeto projeto = tarefa.getProjeto();
            if (projeto == null || projeto.getId() == null || !projetoService.getProjetoById(projeto.getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Projeto associado não encontrado ou não persistido");
            }

            tarefaService.createTarefa(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na criação da tarefa: " + e.getMessage());
        }
    }

    @Tag(name = "DELETE")
    @Operation(summary = "Delete a task", description = "Delete a task by its ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaService.getTarefaById(id);
        if (tarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        tarefaService.deleteTarefa(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada com sucesso");
    }

    @Tag(name = "PUT")
    @Operation(summary = "Update a task", description = "Update an existing task")
    @PutMapping(value = "/", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editTarefa(@RequestBody Tarefa tarefa) {
        try {
            tarefaService.createTarefa(tarefa);
            return ResponseEntity.status(HttpStatus.OK).body("Tarefa atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na atualização da tarefa: " + e.getMessage());
        }
    }
}
