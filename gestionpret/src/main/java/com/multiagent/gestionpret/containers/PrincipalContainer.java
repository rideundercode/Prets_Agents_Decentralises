package com.multiagent.gestionpret.containers;

import org.springframework.boot.SpringApplication;

import com.multiagent.gestionpret.GestionpretApplication;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class PrincipalContainer {

    public static void main(String[] args) {
        SpringApplication.run(GestionpretApplication.class, args);
        demarrerAgents();
    }
        
    public static void demarrerAgents() {
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = rt.createMainContainer(profile);
        
        try {
            container.start();
            System.out.println("Conteneur d'agents démarré sur " + profile.getParameter(Profile.MAIN_HOST, null));

            // Créer l'agent de demande de prêt
            AgentController demandeAgent = container.createNewAgent("DemandeAgent", "com.multiagent.gestionpret.containers.DemandeAgent", null);
            demandeAgent.start(); // Démarrer l'agent de demande

            // Créer l'agent de décision
            AgentController decisionAgent = container.createNewAgent("DecisionAgent", "com.multiagent.gestionpret.containers.DecisionAgent", null);
            decisionAgent.start(); // Démarrer l'agent de décision

        } catch (ControllerException ex) {
            ex.printStackTrace();
        }
    }
}
