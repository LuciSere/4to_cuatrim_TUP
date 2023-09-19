package UTN.datos;

import UTN.dominio.Estudiante;

import static UTN.conexion.Conexion.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    //Método Listar
    public List<Estudiante> listarEstudiantes(){ //se van a importar Estudiante y List
        List<Estudiante> estudiantes = new ArrayList<>();
        //Creamos algunos objetos necesarios para comunicarnos con la base de datos
        PreparedStatement ps; //Envia la sentencia a la base de datos
        ResultSet rs; //Obtenemos el resultado de la base de datos
        Connection con = getConnection();
        //Creamos un objeto de tipo conexion
        String sql = "SELECT * FROM estudiantes2023 ORDER BY idestudiantes2023";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){ //vamos a capturar los datos de cada columna
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("idestudiantes2023"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                //Agregarlo a la lista
                estudiantes.add(estudiante);
            }//fin while
        }catch(Exception e){
            System.out.println("Ocurrio un error al seleccionar datos"+e.getMessage());
        }//fin catch
        finally{
            try{
                con.close(); //cerramos la conexion
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }//fin catch
        }//Fin finally
        return estudiantes;
    }//Fin método listarEstudiantes()

    //Metodo por id -> Find by id
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        String sql = "SELECT * FROM estudiantes2023 WHERE idestudiantes2023=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if(rs.next()){ // los datos de cada columna
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;//se encontro un registro
            }//fin if
        }catch(Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: "+e.getMessage());
        }//fin catch
        finally{
            try{
                con.close(); //cerramos la conexion
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }//fin catch
        }//Fin finally
        return false;
    }//fin metodo buscar por id

    //Metodo agregar un nuevo estudiante
    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "INSERT INTO estudiantes2023 (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?))";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;//se encontro un registro
        }catch(Exception e){
            System.out.println("Ocurrio un error al agregar estudiante: "+e.getMessage());
        }//fin catch
        finally{
            try{
                con.close(); //cerramos la conexion
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }//fin catch
        }//Fin finally
        return false;
    }//Fin Metodo agregarEstudiante()

    public static void main(String[] args) {//main para ejcucion de pruebas
        //Listar Estudiantes
        var estudianteDao= new EstudianteDAO();
        System.out.println("Listado de estudiantes: ");
        List<Estudiante> estudiantes =estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);//funcion lambda para imprimir

        //Buscar por id
        var estudiante1 = new Estudiante(1);
        System.out.println("Estudiantes antes de la busqueda: "+estudiante1);
        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        if (encontrado)
            System.out.println("Estudiante encontrado: "+estudiante1);
        else
            System.out.println("No se encontro el estudiante: "+estudiante1.getIdEstudiante());

    }//fin main para la ejecucion de pruebas

}//Fin clase EstudianteDAO
