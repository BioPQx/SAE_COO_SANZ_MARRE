package view;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.chart.*;

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
        root.setStyle("-fx-padding:25; -fx-background-color:#f5f6fa;");

        StockRepository stockRepo = new StockRepository();
        ReservationRepository resRepo = new ReservationRepository();

        List<Stock> stocks = stockRepo.findAll();
        List<Reservation> reservations = resRepo.findAll();

        /*
        ============================
        KPI
        ============================
        */

        int totalStocks = stocks.size();
        int totalReservations = reservations.size();

        long alertStocks = stocks.stream()
                .filter(s -> s.getQuantiteDisponible() <= s.getSeuilAlerte())
                .count();

        long activeStocks = stocks.stream()
                .filter(Stock::isActif)
                .count();

        int totalDispo = stocks.stream()
                .mapToInt(Stock::getQuantiteDisponible)
                .sum();

        int totalQty = stocks.stream()
                .mapToInt(Stock::getNombre)
                .sum();

        double tauxDispo = totalQty == 0 ? 0 :
                (double) totalDispo / totalQty * 100;

        HBox kpiRow = new HBox(20);

        kpiRow.getChildren().addAll(

                createKpi("Stocks", String.valueOf(totalStocks), "#3498db"),
                createKpi("Réservations", String.valueOf(totalReservations), "#2ecc71"),
                createKpi("Alertes", String.valueOf(alertStocks), "#e67e22"),
                createKpi("Ressources actives", String.valueOf(activeStocks), "#9b59b6"),
                createKpi("Disponibilité", String.format("%.1f %%", tauxDispo), "#1abc9c")
        );

        root.setTop(kpiRow);

        /*
        ============================
        PIE CHART
        ============================
        */

        PieChart pieStock = new PieChart();
        pieStock.setTitle("Répartition des stocks");

        Map<String, Long> stockStatut = stocks.stream()
                .collect(Collectors.groupingBy(
                        Stock::getStatut,
                        Collectors.counting()
                ));

        stockStatut.forEach((statut, count) ->
                pieStock.getData().add(
                        new PieChart.Data(statut, count)
                )
        );

        /*
        ============================
        BAR CHART TYPE RESERVATION
        ============================
        */

        CategoryAxis x1 = new CategoryAxis();
        NumberAxis y1 = new NumberAxis();

        BarChart<String, Number> resChart =
                new BarChart<>(x1, y1);

        resChart.setTitle("Réservations par type");

        Map<String, Long> resTypes = reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getType,
                        Collectors.counting()
                ));

        XYChart.Series<String, Number> serieRes =
                new XYChart.Series<>();

        resTypes.forEach((type, count) ->
                serieRes.getData().add(
                        new XYChart.Data<>(type, count)
                )
        );

        resChart.getData().add(serieRes);

        /*
        ============================
        BAR CHART TOP RESSOURCES
        ============================
        */

        CategoryAxis x2 = new CategoryAxis();
        NumberAxis y2 = new NumberAxis();

        BarChart<String, Number> topChart =
                new BarChart<>(x2, y2);

        topChart.setTitle("Top ressources réservées");

        Map<String, Long> topRessources = reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getRessource,
                        Collectors.counting()
                ));

        XYChart.Series<String, Number> serieTop =
                new XYChart.Series<>();

        topRessources.entrySet()
                .stream()
                .sorted((a,b)->Long.compare(b.getValue(),a.getValue()))
                .limit(5)
                .forEach(e ->
                        serieTop.getData().add(
                                new XYChart.Data<>(
                                        e.getKey(),
                                        e.getValue()
                                )
                        )
                );

        topChart.getData().add(serieTop);

        /*
        ============================
        LAYOUT GRID
        ============================
        */

        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(25);

        grid.add(pieStock,0,0);
        grid.add(resChart,1,0);
        grid.add(topChart,0,1,2,1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        grid.getColumnConstraints().addAll(col1,col2);

        VBox.setVgrow(grid, Priority.ALWAYS);

        root.setCenter(grid);

        animate(root);

        return root;
    }

    /*
    ============================
    KPI CARD
    ============================
    */

    private static VBox createKpi(String title, String value, String color){

        Label t = new Label(title);
        t.setStyle("-fx-text-fill:#555");

        Label v = new Label(value);
        v.setStyle("-fx-font-size:28px; -fx-font-weight:bold;");

        VBox card = new VBox(5,t,v);

        card.setStyle(
                "-fx-background-color:white;" +
                "-fx-padding:20;" +
                "-fx-background-radius:10;" +
                "-fx-border-radius:10;" +
                "-fx-border-color:"+color+";" +
                "-fx-border-width:3;"
        );

        HBox.setHgrow(card, Priority.ALWAYS);

        return card;
    }

    /*
    ============================
    ANIMATION
    ============================
    */

    private static void animate(Pane node){

        FadeTransition fade = new FadeTransition(
                Duration.seconds(0.8),node);

        fade.setFromValue(0);
        fade.setToValue(1);

        fade.play();
    }
}