# SAE_COO_SANZ_MARRE
Github du projet de Conception Orienté Objet du S2 de BUT SD.

Objectif : 

Réaliser une application graphique Java permettant de : 
 P1 - Gérer les utilisateurs de l'application (créer, supprimer, modifier...)
 P1 - Gérer les réservations de materiel (emprunt, retours, modification ...)
 P1 - Gérer les stocks de matériel (ajouter, supprimer, modifier...)
 P2 - Afficher les statistiques du materiel (TdB dynamique si possible)

Interface graphique : 
  - Panneau gauche avec onglets :
      - Utilisateurs
      - Stocks /ressources
      - Materiel / Reservations
      - Statistiques
      - Parametres?

Utilisateurs :
 (OPTIONNEL : Utilisateurs à autorisations variables : 0 = emprunteur (élève) ; 1 = autor d'accès au menu emprunt et stats et param ; 2 = 1 + Stocks ; 3 : 2 + utilisateurs )
 
  - Afficher un utilisateur :
       - Affichage d'un champ pour rechercher l'utilisateur à Afficher,
       -  Affichage de toutes les infos de l'utilisateur
  - Bouton ajout d'utilisateur
       - Affiche des champs à remplir pour créer l'utilisateur,
       - Vérification que l'utilisateur n'éxiste pas déjà
       - écriture dans la base utilisateur du nouvel utilisateur.
  - Bouton Suppression d'utilisateur
       - Affichage d'un champ pour rechercher l'utilisateur à supprimer,
       - Affichage des infos de l'utilisateur avec 2 boutons : Annuler / Supprimer
       - Si bouton annuler : menu, Si bouton Supprimer : Demande à l'opérateur de remarquer le nom de l'utilisateur en entier pour confirmer la suppression avec de nouveau deux boutons : Annuler / Supprimer
  - Bouton Modification d'utilisateur
       - Affichage d'un champ pour rechercher l'utilisateur à modifier,
       - Affichage de toutes les infos modifiables de l'utilisateur,
       - un bouton "modifier" a côté de chaque champ modifiable qui transforme l'affichage en champ de modif ou qui affiche une pop up de modifiaction du champ avec deux boutons : Annuler / Confirmer

Stocks : 

- Afficher le stock actuel :
       - Affichage du stock actuel à Afficher,
       - Affichage de toutes les infos des objets selectionnés
  - Bouton ajout d'un objet au stock
       - Affiche des champs à remplir pour créer l'utilisateur,
       - Vérification que l'utilisateur n'éxiste pas déjà
       - écriture dans la base utilisateur du nouvel utilisateur.
  - Bouton Suppression d'un objet au stock
       - Affichage d'un champ pour rechercher l'objet à supprimer,
       - Affichage des infos de l'objet avec 2 boutons + un champ pour mettre le nombre à supprimer : Annuler / Supprimer
       - Si bouton annuler : menu, Si bouton Supprimer : supprime la quantité demandée avec confirmation.
  - Bouton Modification d'un objet du stock
       - Affichage d'un champ pour rechercher l'objet à modifier,
       - Affichage de toutes les infos modifiables de l'objet,
       - un bouton "modifier" a côté de chaque champ modifiable qui transforme l'affichage en champ de modif ou qui affiche une pop up de modifiaction du champ avec deux boutons : Annuler / Confirmer
