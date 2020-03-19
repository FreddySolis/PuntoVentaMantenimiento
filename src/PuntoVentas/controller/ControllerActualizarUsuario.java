/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuntoVentas.controller;

import static PuntoVentas.controller.ControllerUsuarios.controllerMenu;
import PuntoVentas.model.UsersModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author ayax9
 */
public class ControllerActualizarUsuario implements Initializable{
    private ConnectorMySQL conexion;
    public static UsersModel user;
    
    @FXML
    private TextField CAJAnombre;

    @FXML
    private TextField CAJAcorreo;

    @FXML
    private TextField CAJAapellidoP;

    @FXML
    private TextField CAJAtelefono;

    @FXML
    private Button Cancelar;

    @FXML
    private Button Aceptar;

    @FXML
    private TextField CAJAcontra;

    @FXML
    private TextField CAJAcontra2;

    @FXML
    private RadioButton rbPermiso;

    @FXML
    private TextField CAJAusuario;

    @FXML
    private Label lbError;

    @FXML
    void actualizarUsuario(ActionEvent event) {
        
        if(CAJAnombre.getText().equals("") || CAJAapellidoP.getText().equals("") ||
               CAJAcorreo.getText().equals("") || CAJAcontra.getText().equals("")||
               CAJAcontra2.getText().equals("") || CAJAtelefono.getText().equals("") ||
               CAJAusuario.getText().equals("")){

            lbError.setText("Campos Requeridos Vacios");
            lbError.setVisible(true);

        }else{                

            System.out.println("eox0");
            if(UsersModel.find_email(conexion, CAJAcorreo.getText()) == null || CAJAcorreo.getText().equals(UsersModel.find_email(conexion, CAJAcorreo.getText()).get_correo())){
                System.out.println("eo");
                if(UsersModel.find_user(conexion, CAJAusuario.getText()) == null || UsersModel.find_user(conexion, CAJAusuario.getText()).get_usuario().equals(CAJAusuario.getText())){
                    System.out.println("eox2");
                    if(CAJAcontra.getText().equals(CAJAcontra2.getText())){

                        UsersModel new_user = new UsersModel();
                        new_user.set_id_info(user.get_id_info());
                        new_user.set_id_user(user.get_id_user());
                        new_user.set_nombre(CAJAnombre.getText());
                        new_user.set_apellido(CAJAapellidoP.getText());
                        new_user.set_correo(CAJAcorreo.getText());
                        new_user.set_numero(CAJAtelefono.getText());

                        new_user.set_usuario(CAJAusuario.getText());
                        new_user.set_password(CAJAcontra.getText());
                        if(rbPermiso.isSelected()){
                            new_user.set_admin(1);
                        }else{
                            new_user.set_admin(0);
                        }
                        
                        ////
                        if(new_user.actualizar_usuario(conexion)!=0){

                            if(new_user.actualizar_informacion_usuario(conexion) != 0){
                                lbError.setVisible(false);
                                limpiar();
                                System.out.println("Actualizacion exitosa");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Actualizacion");
                                alert.setHeaderText("Actualizacion Exitosa");  

                                alert.showAndWait();
                                createPage("FXMLPuntoVentasUSUARIOS");
                            }else{
                                System.out.println("Conexion Actualizacion de Informacion fallida");
                            }
                        }else{
                            System.out.println("Conexion Actualizacion de usuario fallida");
                    }      

                    }else{
                        lbError.setText("Contraseñas Erroneas");
                        lbError.setVisible(true);
                    }
                }else{
                    lbError.setText("Ya existe es Usuario");
                    lbError.setVisible(true);
                    System.out.println("Usuario existente");
                }

            }else{
                lbError.setText("Ya existe este correo");
                lbError.setVisible(true);
            }




        }

    }

    @FXML
    void regresar(ActionEvent event) {
        limpiar();
        createPage("FXMLPuntoVentasUSUARIOS");
    }
    
    private AnchorPane home;
    public void createPage(String inter) {
        try {
            home = FXMLLoader.load(getClass().getResource("../view/" + inter + ".fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setNode(Node node) {
        controllerMenu.apContenedor.getChildren().clear();
        controllerMenu.apContenedor.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }
    
    public void limpiar(){
        CAJAnombre.setText("");
        CAJAapellidoP.setText("");
        CAJAcorreo.setText("");
        CAJAcontra.setText("");
        CAJAcontra2.setText("");
        CAJAtelefono.setText("");
        rbPermiso.setSelected(false);
        CAJAusuario.setText("");
    }
    
    public void cargarCampos(){
        CAJAnombre.setText(user.get_nombre());
        CAJAapellidoP.setText(user.get_apellido());
        CAJAcorreo.setText(user.get_correo());
        CAJAcontra.setText(user.get_password());
        CAJAcontra2.setText(user.get_password());
        CAJAtelefono.setText(user.get_numero());
        if(user.get_admin()==1){
            rbPermiso.setSelected(true);
        }else{
            rbPermiso.setSelected(false);
        }
        
        CAJAusuario.setText(user.get_usuario());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCampos();
        System.out.println(user.get_id_info());
        System.out.println(user.get_id_user());
    }
    
}
