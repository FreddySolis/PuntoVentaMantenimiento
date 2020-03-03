package PuntoVentas.model;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectorMySQL {
	public static Connection getConnection() {
            Connection conn;
            try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/papeleria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
                    return conn;
            }catch(SQLException e) {
                    return null;
            }
	}
}
