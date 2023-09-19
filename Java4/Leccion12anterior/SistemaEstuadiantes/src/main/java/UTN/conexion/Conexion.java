package UTN.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConnection(){
        Connection conexion = null; //importar java.sql.Connection
        //Variables para conectarnos a la base de datos
        var baseDatos = "estudiantes";
        var url = "jdbc:mysql://localhost:3306/"+baseDatos;
        var usuario = "root";
        var password = "admin";

        //cargamos la clase del driver de mysql en memoria
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password); //importar java.sql.DriverManager
        }catch (ClassNotFoundException | SQLException e){//importar  java.sql.SQLException
            System.out.println("Ocurrio un error en la conexion: "+e.getMessage());
        }//fin catch
        return conexion;
    }//Fin método Connection
}//fin class Conexion
