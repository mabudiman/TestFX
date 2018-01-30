/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testfx;

import batch.scheduller.BatchScheduller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Arief
 */
public class TestFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        int total_data = 100;
        int total_slot = 10;
        int total_event = 10;
        int total_personil = 20;
        int busy = 10;
        int normal = 10;
        int loose = total_personil - busy - normal;
        int time_block = 60;
        
        
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("(busy,normal,loose)");
        //creating the chart
        final LineChart<String,Number> lineChart = 
                new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle("Statistik Penjadwalan");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Total gagal jadwal");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Total slot waktu yang digunakan");
        //populating the series with data
        for (int i = 0; i < 20; i++) {
           BatchScheduller BS = new BatchScheduller(total_event,total_personil,busy,normal,loose,time_block,total_slot,total_data);
           series.getData().add(new XYChart.Data((i+1)+"("+busy+","+normal+","+loose+")", BS.failSchedulles));
           series2.getData().add(new XYChart.Data((i+1)+"("+busy+","+normal+","+loose+")", BS.usedColorRates));
           if(busy>0){
               busy--;
           }else if(normal>0){
               normal--;
           }
           loose = total_personil - busy - normal;
//           System.out.println("===== STATISTIK DATA =====");
//           System.out.println("Total kegagalan : " + BS.failSchedulles + " dari total " + (total_event*total_data) + " event di " + total_data + " batch");
//           System.out.println("Rata-rata kegagalan perbatch : " + ((float)BS.failSchedulleRates/total_data) + " per batch dari " + total_data + " batch");
//           System.out.println("Rata-rata waktu digunakan : " + ((float)BS.usedColorRates/total_data) + " per batch dari " + total_slot + " timeslot");
//           System.out.println("===== STATISTIK DATA =====");
        }
        
//        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series,series2);
       
//        primaryStage.setScene(scene);
//        primaryStage.show();
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
//        root.getChildren().add(lineChart);
        
//        Scene scene = new Scene(root, 300, 250);
        Scene scene  = new Scene(lineChart,1000,600);
        
        primaryStage.setTitle("Batch Schedulling");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
