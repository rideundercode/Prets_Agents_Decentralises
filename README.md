# Système de Gestion de Prêts avec Agents Décentralisés

## Serveur de Génération de Données de Prêts

### Description
Ce serveur Spring Boot génère des demandes de prêt aléatoires à la volée via une API REST. Il permet de simuler des demandes de prêt, facilitant le développement et les tests d'un système de gestion de prêts.

### Fonctionnalités
- **Génération de Données Aléatoires** : Produire des informations de demande de prêt, incluant :
  - Nom du client
  - Montant du prêt
  - Durée du prêt (en mois)
  - Revenu du client
  - Score de crédit

### API Endpoint
#### Génération de Demandes de Prêt
- **URL** : `/api/loan_requests`
- **Méthode** : `GET`
- **Paramètres** :
  - `count` (optionnel) : Nombre de demandes de prêt à générer (par défaut : 1).

#### Exemple de Requête
```http
GET http://localhost:8080/api/loan_requests?count=5
```

#### Exemple de Requête
```http
[
    {
        "name": "Client 685",
        "term": 34,
        "income": 118054.0,
        "creditScore": 730,
        "amount": 6260.0
    },
    {
        "name": "Client 743",
        "term": 19,
        "income": 62321.0,
        "creditScore": 371,
        "amount": 13394.0
    },
    {
        "name": "Client 677",
        "term": 15,
        "income": 75976.0,
        "creditScore": 574,
        "amount": 16700.0
    },
    {
        "name": "Client 749",
        "term": 13,
        "income": 34300.0,
        "creditScore": 326,
        "amount": 6578.0
    },
    {
        "name": "Client 352",
        "term": 58,
        "income": 34502.0,
        "creditScore": 522,
        "amount": 22895.0
    }
]```

## Agents et Interaction

### Définition des Agents
Le système de gestion de prêts est composé de plusieurs agents, chacun étant un microservice autonome, interagissant via Apache Kafka pour assurer une communication asynchrone et efficace. Les agents définis sont :

1. **Agent de Demande de Prêt**  
   - Gère le processus de soumission des demandes de prêt par les clients.
   - Collecte et valide les informations de la demande.
   - Suit l'état de la demande et informe le client.

2. **Agent d'Évaluation de Crédit**  
   - Évalue la solvabilité du client en fonction des données financières.
   - Accède aux scores de crédit via une API.
   - Fournit une évaluation du risque associée à la demande.

3. **Agent de Décision**  
   - Prend la décision finale d'approuver ou de rejeter la demande de prêt.
   - Reçoit les évaluations des autres agents et applique des critères prédéfinis.
   - Communique la décision aux autres agents.

4. **Agent de Notification**  
   - Informe le client des décisions et mises à jour concernant leur demande de prêt.
   - Envoie des notifications d'approbation ou de rejet et maintient la communication.

5. **Agent de Suivi**  
   - Suit le prêt après son approbation et assure un suivi avec le client.
   - Gère le calendrier des paiements et envoie des rappels.
   - Évalue la satisfaction du client et collecte des retours.

### Interaction via Kafka
L'interaction entre les agents se fait via Apache Kafka, un système de messagerie distribué qui permet de gérer la communication asynchrone. Voici le flux de communication entre les agents :

1. **Soumission d'une Demande de Prêt**  
   - L'agent de demande de prêt publie un message sur le topic `loan_requests`.

2. **Évaluation du Crédit**  
   - L'agent d'évaluation de crédit écoute le topic `loan_requests`, évalue le crédit et publie les résultats sur le topic `credit_evaluations`.

3. **Décision de Prêt**  
   - L'agent de décision écoute `credit_evaluations`, prend une décision et publie celle-ci sur le topic `loan_decisions`.

4. **Notification au Client**  
   - L'agent de notification écoute `loan_decisions` et informe le client du statut de sa demande.

5. **Suivi des Prêts**  
   - L'agent de suivi peut écouter les notifications pour gérer le suivi et envoyer des rappels de paiement.

### Avantages de l'Utilisation de Kafka
- **Scalabilité** : Gère une charge importante de messages.
- **Résilience** : Stocke les messages en cas de défaillance d'un microservice.
- **Découplage** : Permet aux microservices d'évoluer indépendamment.
