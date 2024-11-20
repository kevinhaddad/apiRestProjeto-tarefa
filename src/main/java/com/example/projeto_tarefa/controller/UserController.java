//package com.example.projeto_tarefa.controller;
//
//import com.example.projeto_tarefa.model.Projeto;
//import com.example.projeto_tarefa.model.Tarefa;
//import com.example.projeto_tarefa.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // Métodos CRUD para Projeto
//    @GetMapping(value = "/projetos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public List<Projeto> getAllProjetos() {
//        return userService.getAllProjetos();
//    }
//
//    @GetMapping(value = "/projetos/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public Projeto getProjetoById(@PathVariable Long id) {
//        return userService.getProjetoById(id).orElse(null);
//    }
//
//    @PostMapping(value = "/projetos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public Projeto createProjeto(@RequestBody Projeto projeto) {
//        return userService.createProjeto(projeto);
//    }
//
//    @DeleteMapping(value = "/projetos/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public void deleteProjeto(@PathVariable Long id) {
//        userService.deleteProjeto(id);
//    }
//
//    // Métodos CRUD para Tarefa
//    @GetMapping(value = "/tarefas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public List<Tarefa> getAllTarefas() {
//        return userService.getAllTarefas();
//    }
//
//    @GetMapping(value = "/tarefas/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public Tarefa getTarefaById(@PathVariable Long id) {
//        return userService.getTarefaById(id).orElse(null);
//    }
//
//    @PostMapping(value = "/tarefas", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public Tarefa createTarefa(@RequestBody Tarefa tarefa) {
//        return userService.createTarefa(tarefa);
//    }
//
//    @DeleteMapping(value = "/tarefas/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public void deleteTarefa(@PathVariable Long id) {
//        userService.deleteTarefa(id);
//    }
//}
