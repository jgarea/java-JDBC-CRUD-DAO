/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.java.mariadb.javacrud.pool.MyDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import model.Empleado;

/**
 *
 * @author Juan
 */
public class EmpleadoDaoImpl implements EmpleadoDao {
    
    private static EmpleadoDaoImpl instancia;
    
    static{
        instancia=new EmpleadoDaoImpl();
        
    }
    
    private EmpleadoDaoImpl(){}
    
    public static EmpleadoDaoImpl getInstance(){
        return instancia;
    }
    
    
    @Override
    public int add(Empleado emp) throws SQLException {
        String sql="INSERT INTO empleado (nombre, apellidos, fecha_nacimiento, puesto, email) VALUES (?,?,?,?,?);";
        int result;
        try(Connection conn=MyDataSource.getConnection();
            PreparedStatement pstm =conn.prepareStatement(sql)) {
            
            pstm.setString(1, emp.getNombre());
            pstm.setString(2, emp.getApellidos());
            pstm.setDate(3, Date.valueOf(emp.getFechaNacimiento()));
            pstm.setString(4, emp.getPuesto());
            pstm.setString(5, emp.getEmail());
            
                
            result=pstm.executeUpdate();
        } 
        
        return result;
    }

    @Override
    public Empleado getById(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Empleado> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(Empleado emp) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
