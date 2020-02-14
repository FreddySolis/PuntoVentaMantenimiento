package PuntoVentas.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PuntoVentas.Main;
import PuntoVentas.model.ConnectorMySQL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
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
	@FXML
	
	public void guardar() throws SQLException{
		Connection cn = ConnectorMySQL.getConnection();
        try {
        	System.out.println("HOLI cargar inicio secion");
            String sSQLL = "INSERT INTO login (NombreUsuario,Contrasenia) VALUES(?,?)";
            PreparedStatement stt = cn.prepareStatement(sSQLL);
            stt.setString(1,(CAJAusuario.getText()));
            stt.setString(2,CAJAcontrasenia.getText());
            
            stt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        CAJAusuario.clear();
        CAJAcontrasenia.clear();
	}
	
	public void cargarListado()   {
		try {
			
			
			
	        
			
			
			
			
			
			
			////////////////////////////////////////////////////////////////////

			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLISTADO.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasLISTADO");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.entrar.getScene().getWindow();
			nuevaEscena.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private Button Registrarse;
	
	@FXML
	public void cargarRegistro() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasREGISTRO.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasREGISTRO");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Registrarse.getScene().getWindow();
			nuevaEscena.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@FXML
	private Button Regresar;
	@FXML
	public void cargarOPCIONBASEDATOS() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasOPCIONBasedatos.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasOPCIONBasedatos");
			primaryLayout.show();
			Stage nuevaEscena =(Stage) this.Regresar.getScene().getWindow();
			nuevaEscena.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	/*@FXML
	public void regresarLogin() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasLOGIN.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasLOGIN");
			primaryLayout.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cargarInventario() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasPRODUCTOS.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasPRODUCTOS");
			primaryLayout.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cargarUsuarios() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasUSUARIOS.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasUSUARIOS");
			primaryLayout.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cargarProveedores() {
		try {
			AnchorPane root2 = (AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPuntoVentasPROVEEDORES.fxml"));
			Scene scene = new Scene (root2);
			Stage primaryLayout = new Stage();
			primaryLayout.setScene(scene);
			primaryLayout.setTitle("FXMLPuntoVentasPROVEEDORES");
			primaryLayout.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
