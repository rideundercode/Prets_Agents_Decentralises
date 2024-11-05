package com.multiagent.gestionpret.containers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DemandeAgent extends Agent {
    
    @Override
    protected void setup() {
        System.out.println("Agent de Demande de Prêt démarré : " + getLocalName());

        final RestTemplate restTemplate = new RestTemplate();

        // Comportement cyclique pour gérer les demandes de prêt
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                System.out.println("Traitement de la demande de prêt...");
                // Effectuer une requête GET pour générer des demandes de prêt
                ResponseEntity<LoanRequest[]> response = restTemplate.getForEntity("http://localhost:8081/api/loan_requests?count=5", LoanRequest[].class);
                LoanRequest[] requests = response.getBody();
                
                if (requests != null && requests.length > 0) {
                    for (LoanRequest request : requests) {
                        System.out.println("Demande de prêt générée : " + request);
                        
                        // Évaluer la demande de prêt et envoyer la décision
                        boolean decision = peutAccorderPret(request);
                        envoyerDecision(request.getName(), decision);
                    }
                } else {
                    System.out.println("Aucune demande de prêt générée.");
                }
            
                System.out.println("fin de la requete.");
                
                // Pause pour simuler le temps de traitement
                block(10000); // Pause de 10 secondes
            }
        });
    }

    private boolean peutAccorderPret(LoanRequest request) {
        double tauxEndettement = calculerTauxEndettement(request.getIncome(), request.getAmount(), request.getTerm());
        int scoreCredit = request.getCreditScore();

        // Logique d'évaluation
        if (scoreCredit < 600) {
            return false; // Rejeté pour score de crédit trop bas
        }

        if (tauxEndettement > 0.40) {
            return false; // Rejeté pour taux d'endettement trop élevé
        }

        return true; // Approuvé
    }

    private double calculerTauxEndettement(double income, double amount, int term) {
        double mensualite = amount / term; // Simplification : remboursements mensuels égaux
        return mensualite / income;
    }

    private void envoyerDecision(String clientName, boolean decision) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("DecisionAgent", AID.ISLOCALNAME)); // Assurez-vous que l'Agent de Décision est enregistré
        message.setContent(decision ? "APPROUVÉ pour " + clientName : "REJETÉ pour " + clientName);
        message.setConversationId("loan-decision");

        send(message); // Envoyer le message
        System.out.println("Décision envoyée pour " + clientName + ": " + (decision ? "APPROUVÉ" : "REJETÉ"));
    }
}
