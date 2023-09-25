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
        String sql = "INSERT INTO estudiantes2023 (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";
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

    //Modificar Estudiante
    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConnection();
        String sql = "UPDATE estudiantes2023 SET nombre=?, apellido=?, telefono=?, email=? WHERE idestudiantes2023=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5,estudiante.getIdEstudiante());
            ps.execute();
            return true;//se encontro un registro
        }catch(Exception e){
            System.out.println("Ocurrio un error al Modificar estudiante: "+e.getMessage());
        }//fin catch
        finally{
            try{
                con.close(); //cerramos la conexion
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }//fin catch
        }//Fin finally
        return false;
    }//Fin método modificarEstudiante()

    public static void main(String[] args) {//main para ejcucion de pruebas
        var estudianteDao= new EstudianteDAO();
        //Modificar Estudiante
        var estudianteModificado = new Estudiante(1,"Juan Carlos", "Juarez", "5544663321", "juan@mail.com");
        var modificado = estudianteDao.modificarEstudiante(estudianteModificado);
        if (modificado)
            System.out.println("Estudiante modificado: "+estudianteModificado);
        else
            System.out.println("No se ha modificado el estudiante: "+estudianteModificado);

        //Listar Estudiantes
        System.out.println("Listado de estudiantes: ");
        List<Estudiante> estudiantes =estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);//funcion lambda para imprimir
/*
        //Agregar estudiante
        var nuevoEstudiante = new Estudiante("Carlos", "Lara", "5495544223", "carlosL@mail.com");
        var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
        if (agregado)
            System.out.println("Estudiante agregado: "+nuevoEstudiante);
        else
            System.out.println("No se ha agregado el estudiante: "+nuevoEstudiante);

        //Buscar por id
        var estudiante1 = new Estudiante(1);
        System.out.println("Estudiantes antes de la busqueda: "+estudiante1);
        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        if (encontrado)
            System.out.println("Estudiante encontrado: "+estudiante1);
        else
            System.out.println("No se encontro el estudiante: "+estudiante1.getIdEstudiante());
*/

    }//fin main para la ejecucion de pruebas

}//Fin clase EstudianteDAO
