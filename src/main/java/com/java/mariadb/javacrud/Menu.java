package com.java.mariadb.javacrud;

import dao.EmpleadoDao;
import dao.EmpleadoDaoImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import model.Empleado;





/**
 * Clase que representa el menú principal de una aplicación de gestión de empleados. 
 * 
 * Permite al usuario insertar un nuevo empleado, listar todos los empleados y buscar un empleado por ID.
 * Utiliza un objeto de la clase KeyBoardReader para leer la entrada del usuario, una interfaz EmpleadoDao
 * para realizar operaciones en la base de datos y una clase Empleado para representar a los empleados.
 * 
 * @author Juan Eugenio Antón Area
 */
public class Menu {
    private KeyBoardReader reader;
    private EmpleadoDao dao;
    
    public Menu(){
        reader= new KeyBoardReader();
        dao= EmpleadoDaoImpl.getInstance();
    }
    
    public void insert(){
        System.out.println("\nINSERCIÓN DE UN NUEVO EMPLEADO");
        System.out.println("--------------------------------\n");
        
        System.out.print("Introduzca el nombre (sin apellidos) del empleado:");
        String nombre = reader.nextLine();
        
        System.out.print("Introduzca los apellidos del empleado:");
        String apellidos = reader.nextLine();
        
        System.out.print("Introduzca la fecha de nacimiento del empleado(formato dia/mes/año):");
        LocalDate fechaNaciemiento= reader.nextLocalDate();
        
        System.out.print("Introduzca el puesto del empleado:");
        String puesto = reader.nextLine();
        
        System.out.print("Introduzca el email del empleado:");
        String email = reader.nextLine();
        
        try {
            dao.add(new Empleado(nombre, apellidos, fechaNaciemiento, puesto, email));
            System.out.println("Nuevo empleado registrado.");
        } catch (SQLException ex) {
            System.err.println("Error insertando el nuevo registro en la base de datos vuelva a intentarlo de nuevo.");
        }
        System.out.println();
    }
    
    public void listAll(){
        System.out.println("\nLISTADO DE TODOS LOS EMPLEADOS");
        System.out.println("--------------------------------\n");
        
        
        try {
            List<Empleado> result=dao.getAll();
            if(result.isEmpty())
                System.out.println("\nNo hay empleados registrados en la base de datos.");
            else{
                printCabezeraTablaEmpleado();
                result.forEach(this::printEmpleado);
                
            }
        } catch (SQLException ex) {
            System.err.println("Error mostrando registros de la base de datos vuelva a intentarlo de nuevo.");
            ex.printStackTrace();
        }
        System.out.println("\n");
    }
    
    private void printCabezeraTablaEmpleado(){
        System.out.printf("%2s %30s %8s %10s %25s", "ID","NOMBRE","FEC. NAC.","PUESTO","EMAIL");
        System.out.println();
        IntStream.range(1,100).forEach(x -> System.out.print("-"));
        System.out.println("\n");
    }
    
    private void printEmpleado(Empleado emp){
        System.out.printf("%2s %30s %9s %10s %25s\n",
                emp.getId_empleado(),
                emp.getNombre()+" "+emp.getApellidos(),
                emp.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                emp.getPuesto(),
                emp.getEmail());
    }
    
    public void listById(){
        System.out.println("\nBUSQUEDA DE EMPLEADOS POR ID");
        System.out.println("------------------------------\n");
        try {
            System.out.print("Introduzca el ID del empleado a buscar: ");
            int id= reader.nextInt();
            
            Empleado emp= dao.getById(id);
            if(emp==null)
                System.out.println("No hay empleados registrados en la base de datos con ese ID");
            else{
                printCabezeraTablaEmpleado();
                printEmpleado(emp);
                
            }
            System.out.println("\n");
        } catch (SQLException ex) {
            System.err.println("Error mostrando registros de la base de datos vuelva a intentarlo de nuevo.");
            ex.printStackTrace();
        }
    }
    
    public void update(){
        System.out.println("\nACTUALIZACION DEL EMPLEADO");
        System.out.println("----------------------------\n");
        try {
            System.out.print("Introduzca el ID del empleado a buscar: ");
            int id= reader.nextInt();
            
            Empleado emp=dao.getById(id);
            
            if(emp==null)
                System.out.println("No hay empleados registrados en la base de datos con ese ID");
            else{
                printCabezeraTablaEmpleado();
                printEmpleado(emp);
                System.out.println("\n");
                
                System.out.printf("Introduzca el nombre (sin apellidos) del empleado(%s)",emp.getNombre());
                String nombre = reader.nextLine();
                nombre=(nombre.isBlank()) ? emp.getNombre():nombre;
                
                System.out.printf("Introduzca los apellidos del empleado(%s)",emp.getApellidos());
                String apellidos = reader.nextLine();
                apellidos=(apellidos.isBlank()) ? emp.getApellidos():apellidos;
                
                System.out.printf("Introduzca la fecha de nacimiento del empleado(formato dd/MM/aaaa)(%s)",
                        emp.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                String strFechaNacimiento = reader.nextLine();
                LocalDate fechaNacimiento = (strFechaNacimiento.isBlank())?emp.getFechaNacimiento():
                        LocalDate.parse(strFechaNacimiento,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                System.out.printf("Introduzca el puesto del empleado(%s)",emp.getPuesto());
                String puesto = reader.nextLine();
                puesto=(puesto.isBlank()) ? emp.getPuesto():puesto;
                
                System.out.printf("Introduzca el email del empleado(%s)",emp.getEmail());
                String email = reader.nextLine();
                email=(email.isBlank()) ? emp.getEmail():email;
                
                emp.setNombre(nombre);
                emp.setApellidos(apellidos);
                emp.setFechaNacimiento(fechaNacimiento);
                emp.setPuesto(puesto);
                emp.setEmail(email);
                
                dao.update(emp);
                
                System.out.println("");
                System.out.printf("Empleado con ID %s actualizado",emp.getId_empleado());
                System.out.println("");
                
                
                
            }
            
        } catch (SQLException e) {
            System.err.println("Error actualizando registros de la base de datos vuelva a intentarlo de nuevo.");
            e.printStackTrace();
        }
    }
    
    public void delete(){
        System.out.println("\nBORRADO DE UN EMPLEADO");
        System.out.println("------------------------\n");
        try {
            System.out.print("Introduzca el ID del empleado a borrar: ");
            int id=reader.nextInt();
            
            System.out.printf("Está usted seguro de eliminar al empleado con ID=%s?(s/n): ",id);
            String borrar=reader.nextLine();
            
            if(borrar.equalsIgnoreCase("s")){
                dao.delete(id);
                System.out.printf("El empleado con ID %s se ha borrado\n",id);
            }
        } catch (SQLException e) {
            System.err.println("Error consultando registros de la base de datos vuelva a intentarlo de nuevo.");
            e.printStackTrace();
        }
        System.out.println("");
    }
    
    public void init(){
        int opcion;
        do{
            menu();
            opcion=reader.nextInt();
            switch (opcion) {
                case 1: 
                    listAll();
                    break;
                case 2: 
                    listById();
                    break;
                case 3: 
                    insert();
                    break;
                case 4: 
                    update();
                    break;
                case 5: 
                    delete();
                    break;
                case 0:
                    System.out.println("\nSaliendo del programa...\n");
                    break;
                default:
                    System.err.println("\nEl número introducido no se corresponde con una opción válida\n");
                    
            }
        }while(opcion!=0);
        
    }
    
    public void menu(){
        System.out.println("SISTEMA DE GESTION DE EMPLEADOS");
        System.out.println("===============================\n");
        System.out.println("-> Introduzca una opción de entre las siguientes: ");
        System.out.println("0: Salir");
        System.out.println("1: Listar todos los empleados");
        System.out.println("2: Listar un empleado por su ID");
        System.out.println("3: Insertar un nuevo empleado");
        System.out.println("4: Actualizar un empelado");
        System.out.println("5: Eliminar un empleado");
        System.out.print("\nOpción: ");
    }
    
    static class KeyBoardReader {
        BufferedReader br;
        StringTokenizer st; 

        public KeyBoardReader() {
           br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next(){
            while(st==null || !st.hasMoreElements()){
                try{
                    st=new StringTokenizer(br.readLine());
                }catch(IOException ex){
                    System.out.println("Error leyendo del teclado ");
                    ex.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        int nextInt(){
            return Integer.parseInt(next());
        }
        
        double nextDouble(){
            return Double.parseDouble(next());
        }
        LocalDate nextLocalDate(){
            return LocalDate.parse(next(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String nextLine(){
            String str="";
            try {
                if(st.hasMoreElements()){
                    str=st.nextToken("\n");
                }else
                    str = br.readLine();
            } catch (IOException e) {
                    System.out.println("Error leyendo del teclado ");
                    e.printStackTrace();
            } 
            return str;
        }
    }
}
