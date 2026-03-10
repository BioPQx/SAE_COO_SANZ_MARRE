package view;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import repository.StockRepository;
import repository.ReservationRepository;
import model.Stock;
import model.Reservation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatistiquesView {

    public static BorderPane create() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f3f4f6;");

        StockRepository stockRepo = new StockRepository();
        ReservationRepository resRepo = new ReservationRepository();
        List<Stock> stocks = stockRepo.findAll();
        List<Reservation> reservations = resRepo.findAll();


        int totalStocks       = stocks.size();
        int totalReservations = reservations.size();
        long alertStocks      = stocks.stream().filter(s -> s.getQuantiteDisponible() <= s.getSeuilAlerte()).count();
        long activeStocks     = stocks.stream().filter(Stock::isActif).count();
        int totalDispo        = stocks.stream().mapToInt(Stock::getQuantiteDisponible).sum();
        int totalQty          = stocks.stream().mapToInt(Stock::getNombre).sum();
        double tauxDispo      = totalQty == 0 ? 0 : (double) totalDispo / totalQty * 100;

        Label titre = new Label("Tableau de bord");
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titre.setTextFill(Color.web("#1a1a2e"));

        Label sousTitre = new Label("Vue d'ensemble des stocks et réservations");
        sousTitre.setFont(Font.font("Segoe UI", 13));
        sousTitre.setTextFill(Color.web("#6b7280"));

        VBox titleBlock = new VBox(4, titre, sousTitre);

        HBox kpiRow = new HBox(16);
        kpiRow.getChildren().addAll(
            createKpi("📦  Stocks",             String.valueOf(totalStocks),           "#4f46e5", "#eef2ff"),
            createKpi("📋  Réservations",       String.valueOf(totalReservations),     "#0891b2", "#e0f2fe"),
            createKpi("⚠️  Alertes",             String.valueOf(alertStocks),           "#d97706", "#fef3c7"),
            createKpi("✅  Ressources actives",  String.valueOf(activeStocks),          "#16a34a", "#dcfce7"),
            createKpi("📊  Disponibilité",       String.format("%.1f %%", tauxDispo),  "#7c3aed", "#f3e8ff")
        );

        VBox topBlock = new VBox(16, titleBlock, kpiRow);
        topBlock.setPadding(new Insets(28, 32, 16, 32));
        root.setTop(topBlock);


        PieChart pieStock = new PieChart();
        pieStock.setLegendVisible(true);
        pieStock.setLabelsVisible(true);
        pieStock.setAnimated(true);
        pieStock.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12px;");

        Map<String, Long> stockStatut = stocks.stream()
                .collect(Collectors.groupingBy(Stock::getStatut, Collectors.counting()));
        stockStatut.forEach((statut, count) ->
                pieStock.getData().add(new PieChart.Data(statut + " (" + count + ")", count)));

        VBox pieCard = chartCard("Stocks par statut", pieStock);


        CategoryAxis x1 = new CategoryAxis();
        NumberAxis y1 = new NumberAxis();
        x1.setLabel("Type");
        y1.setLabel("Nombre");
        x1.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12px;");
        y1.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12px;");

        BarChart<String, Number> resChart = new BarChart<>(x1, y1);
        resChart.setLegendVisible(false);
        resChart.setBarGap(6);
        resChart.setCategoryGap(20);
        resChart.setAnimated(true);
        resChart.setStyle("-fx-font-family: 'Segoe UI';");

        Map<String, Long> resTypes = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getType, Collectors.counting()));

        XYChart.Series<String, Number> serieRes = new XYChart.Series<>();
        resTypes.forEach((type, count) ->
                serieRes.getData().add(new XYChart.Data<>(type, count)));
        resChart.getData().add(serieRes);

        VBox resCard = chartCard("Réservations par type", resChart);


        CategoryAxis x2 = new CategoryAxis();
        NumberAxis y2 = new NumberAxis();
        x2.setLabel("Ressource");
        y2.setLabel("Nb réservations");
        x2.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12px;");
        y2.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12px;");

        BarChart<String, Number> topChart = new BarChart<>(x2, y2);
        topChart.setLegendVisible(false);
        topChart.setBarGap(8);
        topChart.setCategoryGap(24);
        topChart.setAnimated(true);
        topChart.setStyle("-fx-font-family: 'Segoe UI';");

        Map<String, Long> topRessources = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRessource, Collectors.counting()));

        XYChart.Series<String, Number> serieTop = new XYChart.Series<>();
        topRessources.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(5)
                .forEach(e -> serieTop.getData().add(new XYChart.Data<>(e.getKey(), e.getValue())));
        topChart.getData().add(serieTop);

        VBox topCard = chartCard("Top 5 ressources réservées", topChart);


        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(8, 32, 28, 32));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        grid.getRowConstraints().addAll(row1, row2);

        grid.add(pieCard, 0, 0);
        grid.add(resCard, 1, 0);
        grid.add(topCard, 0, 1, 2, 1);

        GridPane.setVgrow(pieCard, Priority.ALWAYS);
        GridPane.setVgrow(resCard, Priority.ALWAYS);
        GridPane.setVgrow(topCard, Priority.ALWAYS);
        GridPane.setHgrow(pieCard, Priority.ALWAYS);
        GridPane.setHgrow(resCard, Priority.ALWAYS);
        GridPane.setHgrow(topCard, Priority.ALWAYS);

        root.setCenter(grid);
        animate(root);
        return root;
    }


    private static VBox createKpi(String title, String value, String textColor, String bgColor) {
        Label t = new Label(title);
        t.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        t.setTextFill(Color.web("#6b7280"));

        Label v = new Label(value);
        v.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        v.setTextFill(Color.web(textColor));

        Region accent = new Region();
        accent.setPrefHeight(3);
        accent.setStyle("-fx-background-color: " + textColor + "; -fx-background-radius: 2;");

        VBox card = new VBox(8, t, v, accent);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: transparent;"
        );
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private static VBox chartCard(String title, javafx.scene.Node chart) {
        Label lbl = new Label(title);
        lbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web("#374151"));

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #e5e7eb;");
        VBox.setMargin(sep, new Insets(6, 0, 8, 0));

        VBox.setVgrow(chart, Priority.ALWAYS);

        VBox card = new VBox(0, lbl, sep, chart);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #e5e7eb;" +
            "-fx-border-radius: 10;"
        );
        VBox.setVgrow(card, Priority.ALWAYS);
        return card;
    }


    private static void animate(Pane node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.6), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}