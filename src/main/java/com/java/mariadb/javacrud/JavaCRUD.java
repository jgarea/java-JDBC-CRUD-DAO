/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.java.mariadb.javacrud;

import com.java.mariadb.javacrud.pool.MyDataSource;
import dao.EmpleadoDao;
import dao.EmpleadoDaoImpl;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import model.Empleado;

/**
 *
 * @author Juan
 */
public class JavaCRUD {

    public static void main(String[] args) {
        testDao();
    }

    public static void testDao() {
        EmpleadoDao dao = EmpleadoDaoImpl.getInstance();
        
        Empleado emp = new Empleado("Jose Luis", "Rodriguez Pereira", LocalDate.of(1990, Month.FEBRUARY, 4),
                "Profesor", "joropere@gmail.com");
        try {
            int n = dao.add(emp);
            System.out.println("El número de registros insertados es "+n);
            
            List<Empleado> empleados = dao.getAll();
            
            if(empleados.isEmpty())
                System.out.println("La lista está vacia");
            else
                empleados.forEach(System.out::println); //expresión lambda 
            
            Empleado emp1=dao.getById(1);
            System.out.println("\n"+ emp1);
            
            emp1.setFechaNacimiento(LocalDate.of(1992, Month.MARCH, 4));
            
            n=dao.update(emp1);
            
            emp1= dao.getById(1);
            
            System.out.println("\nEmpleado modificado\n"+ emp1);
            
            
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void testPool() {
        System.out.println("Hello World!");
        try ( Connection conn = MyDataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
