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
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final TarefaService tarefaService;

    @Autowired
    public ProjetoController(final ProjetoService projetoService, final TarefaService tarefaService) {
        this.projetoService = projetoService;
        this.tarefaService = tarefaService;
    }

    @Tag(name = "GET")
    @Operation(summary = "Get all projects", description = "Retrieve all projects")
    @GetMapping(value = "/all", produces = {"application/json", "application/xml"})
    public List<Projeto> getAllProjetos() {
        return projetoService.getAllProjetos();
    }

    @Tag(name = "GET")
    @Operation(summary = "Get project by ID", description = "Retrieve a project by its ID")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoService.getProjetoById(id);
        return projeto.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Tag(name = "POST")
    @Operation(summary = "Create a project", description = "Create a new project")
    @PostMapping(value = "/", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createProjeto(@RequestBody Projeto projeto) {
        try {
            projeto.getTarefas().forEach(tarefa -> tarefa.setProjeto(projeto));
            projetoService.createProjeto(projeto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Projeto criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na criação do projeto: " + e.getMessage());
        }
    }

    @Tag(name = "DELETE")
    @Operation(summary = "Delete a project", description = "Delete a project by its ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProjeto(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoService.getProjetoById(id);
        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        projetoService.deleteProjeto(id);
        return ResponseEntity.status(HttpStatus.OK).body("Projeto deletado com sucesso");
    }

    @Tag(name = "PUT")
    @Operation(summary = "Update a project", description = "Update an existing project")
    @PutMapping(value = "/", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editProjeto(@RequestBody Projeto projeto) {
        try {
            projetoService.createProjeto(projeto);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na atualização do projeto: " + e.getMessage());
        }
    }

    @Tag(name = "POST")
    @Operation(summary = "Add a task to the project", description = "Add a task to the project")
    @PostMapping("/{projetoId}/tarefa")
    public ResponseEntity<String> addTarefaToProjeto(@PathVariable Long projetoId, @RequestBody Tarefa tarefa) {
        Optional<Projeto> projeto = projetoService.getProjetoById(projetoId);
        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        Projeto proj = projeto.get();
        tarefa.setProjeto(proj);  // Associa a tarefa ao projeto
        tarefaService.createTarefa(tarefa);  // Salva a tarefa
        proj.addTarefa(tarefa);  // Adiciona a tarefa ao projeto
        projetoService.createProjeto(proj);  // Salva o projeto
        return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa adicionada ao projeto com sucesso");
    }

    @Tag(name = "PUT")
    @Operation(summary = "Update a task in the project", description = "Update a task in the project")
    @PutMapping("/{projetoId}/tarefa/{tarefaId}")
    public ResponseEntity<String> updateTarefaForProjeto(@PathVariable Long projetoId, @PathVariable Long tarefaId, @RequestBody Tarefa tarefaAtualizada) {
        Optional<Projeto> projeto = projetoService.getProjetoById(projetoId);
        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }

        Optional<Tarefa> tarefa = tarefaService.getTarefaById(tarefaId);
        if (tarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }

        tarefaAtualizada.setId(tarefaId);
        tarefaAtualizada.setProjeto(projeto.get());
        tarefaService.createTarefa(tarefaAtualizada);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa atualizada com sucesso");
    }

    @Tag(name = "DELETE")
    @Operation(summary = "Delete a task from the project", description = "Delete a task from the project")
    @DeleteMapping("/{projetoId}/tarefa/{tarefaId}")
    public ResponseEntity<String> removeTarefaFromProjeto(@PathVariable Long projetoId, @PathVariable Long tarefaId) {
        Optional<Projeto> projeto = projetoService.getProjetoById(projetoId);
        Optional<Tarefa> tarefa = tarefaService.getTarefaById(tarefaId);

        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        if (tarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }

        projeto.get().removeTarefa(tarefa.get());
        projetoService.createProjeto(projeto.get());
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa removida do projeto com sucesso");
    }
}
