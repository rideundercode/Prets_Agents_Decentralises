package com.multiagent.gestionpret.containers;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class DemandeAgent extends Agent {
    
    @Override
    protected void setup() {
        System.out.println("Agent de Demande de Prêt démarré : " + getLocalName());

        // Comportement cyclique pour gérer les demandes de prêt
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                // Ici, vous pouvez ajouter la logique pour générer des demandes de prêt fictives
                // ou interagir avec une interface utilisateur
                // Pour le moment, nous allons juste imprimer un message
                
                System.out.println("Traitement de la demande de prêt...");
                
                // Pause pour simuler le temps de traitement
                block(1000); // Pause de 1 seconde
            }
        });
    }
}
