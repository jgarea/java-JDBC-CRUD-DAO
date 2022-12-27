/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Empleado;

/**
 *
 * @author Juan
 */
public interface EmpleadoDao {
    int add(Empleado emp) throws SQLException;
    
    Empleado getById(int id)throws SQLException;
    
    List<Empleado> getAll() throws SQLException;
    
    int update(Empleado emp) throws SQLException;
    
    void delete(int id) throws SQLException;
    
}
