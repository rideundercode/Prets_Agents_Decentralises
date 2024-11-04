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
    /* 
    public static void main(String[] args) throws Exception {
            
        // Créer une instance de Runtime
        Runtime runtime = Runtime.instance();

        // Créer un profil pour le conteneur principal
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost"); // Écoute sur localhost
        profile.setParameter(Profile.MAIN_PORT, "1099"); // Port par défaut


        // Créer le conteneur d'agents
        AgentContainer container = runtime.createAgentContainer(profile);
        
        // Démarrer le conteneur
        container.start(); // Cela démarre le conteneur, mais aucun agent n'est encore créé
        System.out.println("Conteneur d'agents démarré sur " + profile.getParameter(Profile.MAIN_HOST, "null"));
    }
    */

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
            System.out.println("Conteneur d'agents démarré sur " + profile.getParameter(Profile.MAIN_HOST,null));

            // Créer l'agent de demande de prêt
            AgentController demandeAgent = container.createNewAgent("DemandeAgent", "com.multiagent.gestionpret.containers.DemandeAgent", null);
            demandeAgent.start(); // Démarrer l'agent
        } 
        
        catch (ControllerException ex) {
            ex.printStackTrace();
        }
    }

}

