package com.multiagent.gestionpret.containers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

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
                        
                        // Évaluer la demande de prêt et afficher la décision
                        boolean decision = peutAccorderPret(request);
                        if (decision) {
                            System.out.println("Demande de prêt approuvée pour : " + request.getName());
                        } else {
                            System.out.println("Rejeté : Score de crédit trop bas ou taux d'endettement trop élevé.");
                        }
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
}
