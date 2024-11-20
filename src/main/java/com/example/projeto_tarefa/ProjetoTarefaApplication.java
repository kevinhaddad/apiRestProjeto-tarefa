package com.example.projeto_tarefa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication(scanBasePackages = "com.example.projeto_tarefa")
	public class ProjetoTarefaApplication {

		public static void main(String[] args) {
			SpringApplication.run(ProjetoTarefaApplication.class, args);
		}

	}