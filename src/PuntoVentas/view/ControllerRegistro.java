package PuntoVentas.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PuntoVentas.model.ConnectorMySQL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerRegistro {
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
	public void regresarLogin() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLOGIN.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasLOGIN");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Cancelar.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private Button Aceptar;
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
