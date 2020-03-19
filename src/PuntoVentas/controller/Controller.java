package PuntoVentas.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PuntoVentas.Main;
import PuntoVentas.model.UsersModel;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {

    private ConnectorMySQL conexion;
    @FXML
    private Label lbError;
    @FXML
    AnchorPane anchorOpcion;
    @FXML
    private Button entrar;
    @FXML
    private Button Guardar;
    @FXML
    private TextField CAJAusuario;
    @FXML
    private PasswordField CAJAcontrasenia;
    
    public static UsersModel user;

    @FXML
    public void cargarListado(ActionEvent event) {
        String[] admin = null;
        String password = null;
        UsersModel usuario = null;
        String txtusuario = CAJAusuario.getText();
        String txtpassword = CAJAcontrasenia.getText();

        if (txtusuario.equals("") || txtpassword.equals("")) {
            lbError.setText("Campos requeridos vacios");
            lbError.setVisible(true);

        } else {
            password = UsersModel.get_password(conexion, txtusuario);
            if(password != null){
                if(password.equals(txtpassword)){
                    usuario = UsersModel.find_user(conexion, txtusuario);  
                    user = usuario;
                    if(usuario.get_isdelete() == 0){
                        
                        if(usuario.get_admin() == 1){
                           PuntoVentas.Main.isAdmin = true;
                           ControllerUsuarios.usuarioActivo = usuario;
                           mostra_Menu("FXMLMenuAdmin.fxml");
                        } else {
                            PuntoVentas.Main.isAdmin = false;
                            mostra_Menu("FXMLMenu.fxml");
                        }
                    }else{
                        lbError.setText("Usuario Eliminado");
                        lbError.setVisible(true);
                    }
                   

                } else {
                    lbError.setText("Contraseña incorrecta");
                    lbError.setVisible(true);
                }

                CAJAusuario.clear();
                CAJAcontrasenia.clear();

            } else {
                lbError.setText("No existe ususario");
                lbError.setVisible(true);
            }

        }
    }
    
    @FXML
    private void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void mostra_Menu(String ruta) {
        try {

            AnchorPane root2 = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/" + ruta));
            Scene scene = new Scene(root2);
            Stage primaryLayout = new Stage();
            primaryLayout.setScene(scene);
            primaryLayout.setTitle("FXMLMenu");
            primaryLayout.initStyle(StageStyle.TRANSPARENT);
            primaryLayout.show();
            Stage nuevaEscena = (Stage) this.entrar.getScene().getWindow();
            nuevaEscena.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
