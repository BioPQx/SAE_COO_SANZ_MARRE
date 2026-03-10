package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AccueilView {

    public static StackPane create() {

        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: #f3f4f6;");

        VBox card = new VBox();
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );
        card.setPadding(new Insets(40, 48, 40, 48));
        card.setAlignment(Pos.TOP_LEFT);
        card.maxWidthProperty().bind(pane.widthProperty().multiply(0.85));
        card.setMaxHeight(Region.USE_PREF_SIZE);

        Label titre = new Label("Bienvenue dans l'application");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titre.setTextFill(Color.web("#1a1a2e"));

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(16, 0, 20, 0));

        Label sousTitre = new Label("Introduction");
        sousTitre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        sousTitre.setTextFill(Color.web("#4f46e5"));

        Region espaceLabel = new Region();
        espaceLabel.setPrefHeight(10);

        Label texte = new Label(
            "Le projet présenté dans ce rapport concerne la conception d'une application de gestion de réservations de matériel pour un service universitaire de prêt de ressources informatiques et audiovisuelles. Ce service souhaite disposer d'un outil permettant de gérer les utilisateurs, les ressources disponibles et les réservations associées, tout en s'appuyant sur un historique fourni au format CSV. Les ressources concernées peuvent être variées, comme des PC, des tablettes, des caméscopes ou des enregistreurs numériques.\n\n" +
            "L'objectif du projet est de développer une application Java avec interface graphique capable de charger les données depuis un fichier CSV, de gérer les différentes entités du système et de produire des statistiques d'usage. L'application doit ainsi permettre de créer, modifier, afficher et supprimer des utilisateurs, des ressources et des réservations, mais aussi de proposer des indicateurs utiles pour mieux comprendre l'utilisation du matériel.\n\n" +
            "Pour répondre à ce besoin, une démarche de conception orientée objet a été adoptée. Cette approche nécessite une phase de modélisation en amont afin d'identifier clairement les éléments du système, leurs propriétés et leurs interactions. Dans ce cadre, l'utilisation d'un diagramme de classes UML permet de représenter la structure du système de façon claire et organisée avant l'implémentation. Ce diagramme met en évidence les classes principales, leurs attributs, leurs méthodes ainsi que les relations qui les relient.\n\n" +
            "Ce rapport présente ainsi le travail de conception réalisé autour de ce projet. Il vise à expliquer le contexte, les objectifs attendus, ainsi que les choix de modélisation effectués pour construire une application cohérente et adaptée aux besoins exprimés."
        );
        texte.setFont(Font.font("Segoe UI", 14));
        texte.setTextFill(Color.web("#374151"));
        texte.setWrapText(true);
        texte.setLineSpacing(3);
        texte.maxWidthProperty().bind(card.widthProperty().subtract(96));

        card.getChildren().addAll(titre, sep, sousTitre, espaceLabel, texte);

        StackPane.setMargin(card, new Insets(36));
        pane.getChildren().add(card);

        return pane;
    }
}