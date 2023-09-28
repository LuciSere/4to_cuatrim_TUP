package UTN.presentacion;

import UTN.conexion.Conexion;
import UTN.datos.EstudianteDAO;
import UTN.dominio.Estudiante;

import java.util.Scanner;

public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);//para leer informacion por consola
        //Se crea una instancia de clase servicio, esto lo hacemos por fuera del ciclo while
        var estudianteDao = new EstudianteDAO();//esta instancia debe hacerse solo una vez
        while(!salir){
            try{
                mostrarMenu();//Mostramos menu
                //El metodo EjecutarOpciones devolvera un booleano
                salir = ejecutarOpciones(consola, estudianteDao);//este arroja una exception
            }catch(Exception e){
                System.out.println("Ocurrio un error al ejecutar la operacion en el menu "+e.getMessage());
            }
        }//fin while
    }//Fin Main
    private static void mostrarMenu(){
        System.out.println("""
                ******* Sistema de Estudiantes *******
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elige una opción:
                """);
    }//fin metodo mostrarMenu()

    //Metodo Ejecutar opciones, retorna un booleano que puede modificar la varible salir,
    // si es true termina el clico while
    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch(opcion){
            case 1 -> { //Listar Estudiantes
                System.out.println("Listado de Estudiantes: ");
                var estudiantes = estudianteDAO.listarEstudiantes();//recibe la lista (no la muestra)
                //vamos a iterar cada objeto de tipo estudiante
                estudiantes.forEach(System.out::println); //para imprimir la lista
            }//Fin case 1
            case 2 -> { //Buscar Estudiante por id
                System.out.println("Introduce el id_estudiante a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());//recibe el id digitado
                var estudiante = new Estudiante((idEstudiante));//crea un estudiante con ese id
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante); //recibe un bool
                if (encontrado)
                    System.out.println("Estudiante encontrado: "+estudiante);
                else
                    System.out.println("Estudiante NO encontrado: "+estudiante);
            }//Fin case 2
            case 3 -> { //Agregar Estudiante
                System.out.println("Agregar Estudiante");
                System.out.println("Nombre: ");
                var nombre = consola.nextLine();
                System.out.println("Apellido: ");
                var apellido = consola.nextLine();
                System.out.println("Telefono: ");
                var telefono = consola.nextLine();
                System.out.println("Email: ");
                var email = consola.nextLine();
                //crear el objeto estudiante (sin id)
                var estudiante = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);
                if (agregado)
                    System.out.println("Estudiante agregado: "+estudiante);
                else
                    System.out.println("Estudiante NO agregado: "+estudiante);
            }//Fin case 3
            case 4 -> { //Modificar Estudiante
                System.out.println("Modificar Estudiante: ");
                //Especificar el id del estudiante a modificar
                System.out.println("id_estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());//recibe el id digitado
                System.out.println("Nombre: ");
                var nombre = consola.nextLine();
                System.out.println("Apellido: ");
                var apellido = consola.nextLine();
                System.out.println("Telefono: ");
                var telefono = consola.nextLine();
                System.out.println("Email: ");
                var email = consola.nextLine();
                //Creo el objeto estudiante a modificar (conn los datos ingresados)
                var estudiante = new Estudiante(idEstudiante, nombre, apellido, telefono, email);
                var modificado = estudianteDAO.modificarEstudiante(estudiante);//devuelve un bool
                if (modificado)
                    System.out.println("Estudiante modificado: "+estudiante);
                else
                    System.out.println("Estudiante NO modificado: "+estudiante);
            }//Fin case 4
            case 5 -> { //Eliminar Estudiante
                System.out.println("Eliminar Estudiante: ");
                System.out.println("id_estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());//recibe el id digitado
                var estudiante = new Estudiante((idEstudiante));//crea un estudiante con ese id
                var eliminado = estudianteDAO.eliminarEstudiante(estudiante); //recibe un bool
                if (eliminado)
                    System.out.println("Estudiante eliminado: "+estudiante);
                else
                    System.out.println("Estudiante NO eliminado: "+estudiante);
            }//Fin case 5
            case 6 -> { //Salir
                System.out.println("Hasta luego!..");
                salir = true;
            }//Fin case 6
            default -> System.out.println("Opcion desconocida, ingrese otra opcion");
        }//Fin switch
        return salir;
    }//Fin metodo ejecutarOpciones()

}//Fin Class SistemaEstudiantesApp