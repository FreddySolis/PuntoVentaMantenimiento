package PuntoVentas.controller;

import static PuntoVentas.controller.ControllerUsuarios.controllerMenu;
import PuntoVentas.model.UsersModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerRegistro {
    private ConnectorMySQL conexion;
    private UsersModel user;
    @FXML
    private Button Cancelar;
    @FXML
    private TextField CAJAnombre;
    @FXML
    private TextField CAJAapellidoP;
    @FXML
    private TextField CAJAcorreo;
    @FXML
    private TextField CAJAcontra;
    @FXML
    private TextField CAJAcontra2;
    @FXML
    private TextField CAJAtelefono;
    @FXML
    private Button Aceptar;
    @FXML
    private RadioButton rbPermiso;
    @FXML
    private TextField CAJAusuario;
    @FXML
    private Label lbError;

    @FXML
    void crearUsuario(ActionEvent event) {
        
        if(CAJAnombre.getText().equals("") || CAJAapellidoP.getText().equals("") ||
               CAJAcorreo.getText().equals("") || CAJAcontra.getText().equals("")||
               CAJAcontra2.getText().equals("") || CAJAtelefono.getText().equals("") ||
               CAJAusuario.getText().equals("")){

            lbError.setText("Campos Requeridos Vacios");
            lbError.setVisible(true);

        }else{                

            System.out.println("eox0");
            if(UsersModel.find_email(conexion, CAJAcorreo.getText()) == null){
                System.out.println("eo");
                if(UsersModel.find_user(conexion, CAJAusuario.getText()) == null){
                    System.out.println("eox2");
                    if(CAJAcontra.getText().equals(CAJAcontra2.getText())){

                        UsersModel new_user = new UsersModel();
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
                        if(new_user.guardar_usuario(conexion)!=0){

                            if(new_user.guardar_informacion_usuario(conexion) != 0){
                                lbError.setVisible(false);
                                limpiar();
                                System.out.println("Creacion exitosa");
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Registro");
                                alert.setHeaderText("Creacion Exitosa");  

                                alert.showAndWait();
                            }else{
                                System.out.println("Conexion guardado de Informacion fallida");
                            }
                        }else{
                            System.out.println("Conexion guardado de usuario fallida");
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
    void regresarLogin(ActionEvent event) {
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
    


    public void agregar()throws SQLException{
        Connection cn = ConnectorMySQL.getConnection();
        try {
            System.out.println("HOLI registro usuario");

            String sSQLL = "INSERT INTO listausuarios (apellidoPaterno,nombre,Correo,contrasena,contrasena2) VALUES(?,?,?,?,?)";
            PreparedStatement stt = cn.prepareStatement(sSQLL);
            stt.setString(1,(CAJAnombre.getText()));
            stt.setString(2,(CAJAapellidoP.getText()));
            stt.setString(3,(CAJAcorreo.getText()));
            stt.setString(4,(CAJAcontra.getText()));
            stt.setString(5,(CAJAcontra2.getText()));
            stt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        CAJAnombre.clear();
        CAJAapellidoP.clear();
        CAJAcorreo.clear();
        CAJAcontra.clear();
        CAJAcontra2.clear();
    }

    @FXML
    public void cargarListado() throws SQLException {
            Connection cn = ConnectorMySQL.getConnection();
            try {
                    System.out.println("HOLI registro usuario22222");
                    agregar();

//	        try {
//	        	System.out.println("HOLI registro usuario");
//
//	            String sSQLL = "INSERT INTO listausuarios (apellidoPaterno,nombre,Correo,contrasena,contrasena2) VALUES(?,?,?,?,?)";
//	            PreparedStatement stt = cn.prepareStatement(sSQLL);
//	            stt.setString(1,(CAJAnombre.getText()));
//	            stt.setString(2,(CAJAapellidoP.getText()));
//	            stt.setString(3,(CAJAcorreo.getText()));
//	            stt.setString(4,(CAJAcontra.getText()));
//	            stt.setString(5,(CAJAcontra2.getText()));
//	            stt.execute();
//	        }catch (SQLException e){
//	            e.printStackTrace();
//	        }
//	        CAJAnombre.clear();
//	        CAJAapellidoP.clear();
//	        CAJAcorreo.clear();
//	        CAJAcontra.clear();
//	        CAJAcontra2.clear();


                    ////////////////////////
                    AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLISTADO.fxml"));
                    Scene scene = new Scene (root2);
                    Stage primaryLayout = new Stage();
                    primaryLayout.setScene(scene);
                    primaryLayout.setTitle("FXMLPuntoVentasLISTADO");
                    primaryLayout.show();
                    Stage nuevaEscena =(Stage) this.Aceptar.getScene().getWindow();
                    nuevaEscena.close();

            } catch (Exception e) {
                    e.printStackTrace();
            }
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
}
