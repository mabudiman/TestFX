/*
 * Copyright (C) 2018 Arief
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package testfx;

import batch.scheduller.BatchScheduller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author Arief
 */
public class TestFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // data event
        int total_slot = 10;
        int total_event = 10;
        int time_block = 60;
        // data personil
        int total_personil = 20;
        int busy = 20;
        int normal = 0;
        int loose = total_personil - busy - normal;
        // data test
        int total_data = 10;
        
        Label text1 = new Label("Jumlah Sidang");
        TextField textField1 = new TextField();
        textField1.setText(Integer.toString(total_event));
        Label text2 = new Label("Jumlah Slot Waktu");
        TextField textField2 = new TextField();
        textField2.setText(Integer.toString(total_slot));
        Label text3 = new Label("Jumlah Waktu / Slot");
        TextField textField3 = new TextField();
        textField3.setText(Integer.toString(time_block));
        
        Label text4 = new Label("Jumlah Dosen");
        TextField textField4 = new TextField();
        textField4.setText(Integer.toString(total_personil));
        Label text5 = new Label("Sibuk");
        TextField textField5 = new TextField();
        textField5.setText(Integer.toString(busy));
        Label text6 = new Label("Normal");
        TextField textField6 = new TextField();
        textField6.setText(Integer.toString(normal));
        Label text7 = new Label("Lenggang");
        TextField textField7 = new TextField();
        textField7.setText(Integer.toString(loose));
        
        Label text8 = new Label("Jumlah Diulang");
        TextField textField8 = new TextField();
        textField8.setText(Integer.toString(total_data));
        
        GridPane GP = new GridPane();
        //Setting size for the pane  
        GP.setMinSize(400, 200); 
        //Setting the padding  
        GP.setPadding(new Insets(10, 0, 10, 10)); 
        //Setting the vertical and horizontal gaps between the columns 
        GP.setVgap(5); 
        GP.setHgap(20);
        GP.setStyle("-fx-border-color: black");
        //Setting the Grid alignment 
        GP.setAlignment(Pos.TOP_LEFT);
        GP.add(text1, 0, 0);
        GP.add(text2, 0, 1);
        GP.add(text3, 0, 2);
        GP.add(text4, 0, 3);
        GP.add(text5, 0, 4);
        GP.add(text6, 0, 5);
        GP.add(text7, 0, 6);
        GP.add(text8, 0, 7);
        GP.add(textField1, 1, 0);
        GP.add(textField2, 1, 1);
        GP.add(textField3, 1, 2);
        GP.add(textField4, 1, 3);
        GP.add(textField5, 1, 4);
        GP.add(textField6, 1, 5);
        GP.add(textField7, 1, 6);
        GP.add(textField8, 1, 7);
        
        // ====================== Line Chart ==========================
        // defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("(busy,normal,loose)");
        yAxis.setLabel("Persentase kegagalan per batch");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(5);
        //creating the chart
        final LineChart<String,Number> lineChart = 
                new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle("Statistik Penjadwalan");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Data");
        //populating the series with data
        for (int i = 0; i < 30; i++) {
           BatchScheduller BS = new BatchScheduller(total_event,total_personil,busy,normal,loose,time_block,total_slot,total_data);
           series.getData().add(new XYChart.Data((i+1)+"("+busy+","+normal+","+loose+")",((float)BS.failSchedulles/total_data)/total_event*100));
           if(busy>0){
               busy--;
               normal++;
           }else if(normal>0){
               normal--;
           }
           loose = total_personil - busy - normal;
        }
        
        lineChart.getData().addAll(series);
        lineChart.setStyle("-fx-border-color: black");
        lineChart.setMinSize(700, 400);
        // ====================== Line Chart ==========================
        
        
        Button btn = new Button();
        btn.setText("Eksekusi");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                // data event
                int total_slot = Integer.parseInt(textField2.getText());
                int total_event = Integer.parseInt(textField1.getText());
                int time_block = Integer.parseInt(textField3.getText());
                // data personil
                int total_personil = Integer.parseInt(textField4.getText());
                int busy = Integer.parseInt(textField5.getText());
                int normal = Integer.parseInt(textField6.getText());
                int loose = Integer.parseInt(textField7.getText());
                // data test
                int total_data = Integer.parseInt(textField8.getText());
                
                series.getData().clear();
                for (int i = 0; i < 30; i++) {
                    BatchScheduller BS = new BatchScheduller(total_event,total_personil,busy,normal,loose,time_block,total_slot,total_data);
                    series.getData().add(new XYChart.Data((i+1)+"("+busy+","+normal+","+loose+")",((float)BS.failSchedulles/total_data)/total_event*100));
                    if(busy>0){
                        busy--;
                        normal++;
                    }else if(normal>0){
                        normal--;
                    }
                    loose = total_personil - busy - normal;
                }
            }
        });
        GP.add(btn, 0, 8);
        
        HBox hb = new HBox();
        hb.setSpacing(0); 
        hb.setMargin(GP, new Insets(20, 20, 20, 20)); 
        hb.setMargin(lineChart, new Insets(20, 20, 20, 20)); 
        //retrieving the observable list of the HBox 
        ObservableList list = hb.getChildren(); 
        //Adding all the nodes to the observable list (HBox) 
        list.addAll(GP, lineChart);   
        
        Scene scene  = new Scene(hb,1200,600);
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
