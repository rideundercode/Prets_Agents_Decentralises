package com.multiagent.gestionpret.containers;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DecisionAgent extends Agent {
    
    @Override
    protected void setup() {
        System.out.println("Agent de Décision démarré : " + getLocalName());

        // Comportement cyclique pour écouter les décisions
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage message = receive(); // Recevoir un message
                if (message != null) {
                    traiterMessage(message);
                } else {
                    block(); // Bloquer si aucun message n'est reçu
                }
            }
        });
    }

    private void traiterMessage(ACLMessage message) {
        String contenu = message.getContent();
        String clientName = contenu.split(" ")[2]; // Récupérer le nom du client à partir du contenu

        if (contenu.startsWith("APPROUVÉ")) {
            System.out.println("Demande de prêt pour " + clientName + " : APPROUVÉE");
        } else if (contenu.startsWith("REJETÉ")) {
            System.out.println("Demande de prêt pour " + clientName + " : REJETÉE");
        }
    }
}
