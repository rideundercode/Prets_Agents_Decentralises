package com.multiagent.gestionpret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.multiagent.gestionpret.containers.PrincipalContainer;

@SpringBootApplication
public class GestionpretApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionpretApplication.class, args);
		PrincipalContainer.demarrerAgents();

	}

}
