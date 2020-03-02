/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author JulioCaballero
 */
public class FXMLMenuController implements Initializable {
    
    @FXML
    private AnchorPane apContenedor;
    
    private  AnchorPane home;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void mostrarCrearProyecto(MouseEvent event) {
    }

   @FXML
    private void min(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        //Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //para maximizar
        //stage.setFullScreenExitHint(" ");
        //stage.setFullScreen(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    private void mostrarSolicitudes(MouseEvent event) {
    }

    @FXML
    private void inventarios(MouseEvent event) {
         try {
            createPage("FXMLProdutos");

        } catch (Exception e) {
            e.printStackTrace();
		}
    }

    @FXML
    private void ventas(MouseEvent event) {
        try {
            createPage("FXMLVentas");

        } catch (Exception e) {
            e.printStackTrace();
		}
    }

    @FXML
    private void proveedores(MouseEvent event) {
        try {
            createPage("FXMLProveedores");

        } catch (Exception e) {
            e.printStackTrace();
		}
    }

    @FXML
    private void reportes(MouseEvent event) {
    }

    @FXML
    private void logout(MouseEvent event) {
        try {
                AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLLogin2"));
                Scene scene = new Scene(root2);
                Stage primaryLayout = new Stage();
                primaryLayout.setScene(scene);
                primaryLayout.setTitle("FXMLLogin");
                primaryLayout.show();
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
     public void createPage(String inter){
        try {
            home = FXMLLoader.load(getClass().getResource("../view/"+ inter +".fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private  void setNode(Node node){
        apContenedor.getChildren().clear();
        apContenedor.getChildren().add((Node)node);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
    }
    
}
