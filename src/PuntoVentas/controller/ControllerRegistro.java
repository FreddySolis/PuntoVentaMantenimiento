package PuntoVentas.controller;

import PuntoVentas.model.UsersModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
        if(user==null){
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
                                    System.out.println("Creacion exitosa");
                                }else{
                                    System.out.println("Guardar usuario error");
                                }
                            }else{
                                System.out.println("guardar info error");
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
           
       }else{
       
       }

    }

    @FXML
    void regresarLogin(ActionEvent event) {
        UsersModel.find_email(conexion, "asd@hotmail.com");
        UsersModel.find_user(conexion, "rulo");
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
}
