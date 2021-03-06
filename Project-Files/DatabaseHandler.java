package regalator3000;

import java.sql.DriverManager;
import java.sql.SQLTimeoutException;

import java.sql.Connection;

/*Clase que guarda la conexion con la base de datos para que las demas clases puedan acceder a ella,
 * Hay que instanciarla y guarda en actuaUserID el id en la base de datos del usuario logeado para
 * que las demas clases puedan cambiar sus datos. 
 * 
 * Para logear/deslogear a un usuario usar los metodos de UserControl, no 
 * hacerlo a lo bruto con setUserID...
 */
public class DatabaseHandler {
	
	private Connection actualConnection;
	private int actualUserID = -1;

	public DatabaseHandler(String nombreBdD, String superUser, String pwd) {
		setConnection(nombreBdD,superUser,pwd);
	}
	
	public Connection getConnection(){
		return this.actualConnection;
	}
	
	public void setConnection(String nombreBdD, String superUser, String pwd) {
		this.actualConnection = connectToDb(nombreBdD,superUser,pwd);
		//if (this.actualConnection == null){
			//throw new SQLTimeoutException(); //No puede encontrar BdD, buscar otro tipo de excepciones o crear una?
		//}
	}
	
	public int getUserID(){
		return actualUserID;
	}
	
	public void setUserID(int newID){
		this.actualUserID = newID;
	}
	
	public void closeConnection(){
		this.actualConnection = null;
	}

	/*Crea una conexion a una base de datos, PUEDE DEVOLVER NULL*/
	private static Connection connectToDb(String dbName,String user,String pwd) {
		Connection unaConexion = null;
		try{
			/*Auto reconnect=true, solo se cerrara llamando al metodo close() de la connection, usamos ssl para mas seguridad(a lo mejor
			para usuarios remotos no va bien comprobar; Tambien esta la opcion verifyServerCertificate=true/false sobretodo para modo local*/
			unaConexion  = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName+"?autoReconnect=true&useSSL=true",user, pwd);
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		return unaConexion;
	}
	

	public static void main(String[] args) throws SQLTimeoutException{ 

	}

}
