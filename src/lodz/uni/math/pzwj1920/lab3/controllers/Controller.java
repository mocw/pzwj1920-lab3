package lodz.uni.math.pzwj1920.lab3.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas screenCanv;
    @FXML
    private RadioButton rbRedRect = new RadioButton();
    @FXML
    private RadioButton rbBlueRect  = new RadioButton();
    @FXML
    private RadioButton rbGreebRect  = new RadioButton();
    @FXML
    private Button btnInput;
    ObservableList<Integer> options = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
    @FXML
    private ComboBox<Integer> CbXValue;
    @FXML
    private ComboBox<Integer> cbYValue;
    private final ToggleGroup rbRectGroup = new ToggleGroup();
    private GraphicsContext graphicsContext;
    private List<Point> lista = new ArrayList<Point>();

    public RadioButton getRbRedRect() {
        return rbRedRect;
    }

    public RadioButton getRbBlueRect() {
        return rbBlueRect;
    }

    public RadioButton getRbGreebRect() {
        return rbGreebRect;
    }

    public Button getBtnInput() {
        return btnInput;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CbXValue.setItems(options);
        cbYValue.setItems(options);
        rbBlueRect.setToggleGroup(rbRectGroup);
        rbGreebRect.setToggleGroup(rbRectGroup);
        rbRedRect.setToggleGroup(rbRectGroup);
        rbRedRect.setSelected(true);
        graphicsContext = screenCanv.getGraphicsContext2D();

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0,screenCanv.getWidth(),screenCanv.getHeight());
    }

    public void wstaw(ActionEvent actionEvent) {
        try{
            int x = CbXValue.getValue();
            int y = cbYValue.getValue();
            getColor();
            outer:
            for(int i=0,i2=1;i<screenCanv.getWidth();i+=40,i2++)
                for(int j=0,j2=1;j<screenCanv.getHeight();j+=40,j2++){
                    if(i2 == x && j2 == y){
                        if(checkCollision(i,j)) break outer;
                        graphicsContext.fillRect(i,j, Utilities.RECT_WIDTH,Utilities.RECT_HEIGHT);
                        lista.add(new Point(i,j));
                    }
                }
        }
        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wstaw dane!", ButtonType.OK);
            alert.showAndWait();
//            if (alert.getResult() == ButtonType.OK) {
//                // RETURN
//            }
        }
    }

    private void getColor(){
        if(rbBlueRect.isSelected()){
            graphicsContext.setFill(Color.BLUE);
        }
        else if(rbRedRect.isSelected()){
            graphicsContext.setFill(Color.RED);
        }
        else if(rbGreebRect.isSelected()){
            graphicsContext.setFill(Color.GREEN);
        }
    }

    private boolean checkCollision(int i, int j){
        for (Point point : lista) {
            if (point.getX() == i && point.getY() == j) return true;
        }
        return false;
    }

    public void wstawNaKlikniecie(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        getColor();
        outer:
        for(int i=0;i<screenCanv.getWidth();i+=40)
            for(int j=0;j<screenCanv.getHeight();j+=40){
                if(x >= i && x < i+40 && y >= j && y < j+40){
                    if(checkCollision(i,j)) break outer;
                    graphicsContext.fillRect(i,j, Utilities.RECT_WIDTH,Utilities.RECT_HEIGHT);
                    lista.add(new Point(i,j));
                }
            }
    }
}
