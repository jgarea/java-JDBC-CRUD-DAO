/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.java.mariadb.javacrud.pool.MyDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Empleado;

/**
 *
 * @author Juan
 */
public class EmpleadoDaoImpl implements EmpleadoDao {

    private static EmpleadoDaoImpl instancia;

    static {
        instancia = new EmpleadoDaoImpl();

    }

    private EmpleadoDaoImpl() {
    }

    public static EmpleadoDaoImpl getInstance() {
        return instancia;
    }

    @Override
    public int add(Empleado emp) throws SQLException {
        String sql = "INSERT INTO empleado (nombre, apellidos, fecha_nacimiento, puesto, email) VALUES (?,?,?,?,?);";
        int result;
        try ( Connection conn = MyDataSource.getConnection();  PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, emp.getNombre());
            pstm.setString(2, emp.getApellidos());
            pstm.setDate(3, Date.valueOf(emp.getFechaNacimiento()));
            pstm.setString(4, emp.getPuesto());
            pstm.setString(5, emp.getEmail());

            result = pstm.executeUpdate();
        }

        return result;
    }

    @Override
    public Empleado getById(int id) throws SQLException {
        Empleado result = null;

        String sql = "SELECT * FROM empleado WHERE id_empleado=?";

        try ( Connection conn = MyDataSource.getConnection();  PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try ( ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    result = crearEmpleado(rs);

                }
            }
        }

        return result;
    }

    @Override
    public List<Empleado> getAll() throws SQLException {
        String sql = "SELECT * FROM empleado";
        List<Empleado> result = new ArrayList<>();

        try ( Connection conn = MyDataSource.getConnection();  PreparedStatement pstm = conn.prepareStatement(sql);  ResultSet rs = pstm.executeQuery()) {

            Empleado emp;
            while (rs.next()) {
                emp = crearEmpleado(rs);

                result.add(emp);
            }

        }

        return result;
    }

    @Override
    public int update(Empleado emp) throws SQLException {
        String sql="""
                    UPDATE empleado SET
                        nombre = ?, apellidos = ?,
                        fecha_nacimiento = ?,
                        puesto = ?, email = ?
                    WHERE id_empleado = ?
                   """;
        int result;
        
        try(Connection conn = MyDataSource.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            
            pstm.setString(1, emp.getNombre());
            pstm.setString(2, emp.getApellidos());
            pstm.setDate(3, Date.valueOf(emp.getFechaNacimiento()));
            pstm.setString(4, emp.getPuesto());
            pstm.setString(5, emp.getEmail());
            pstm.setInt(6, emp.getId_empleado());
            
            result=pstm.executeUpdate();
        }
        
        return result;
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Crea un objeto de tipo Empleado a partir de los datos de un ResultSet de
     * base de datos.
     *
     * @param rs El ResultSet que contiene los datos del empleado.
     * @return Un objeto Empleado con los datos del ResultSet.
     * @throws SQLException Si ocurre algún problema al obtener los datos del
     * ResultSet.
     */
    private Empleado crearEmpleado(ResultSet rs) throws SQLException {
        Empleado emp = new Empleado();
        emp.setId_empleado(rs.getInt("id_empleado"));
        emp.setNombre(rs.getString("nombre"));
        emp.setApellidos(rs.getString("apellidos"));
        emp.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        emp.setPuesto(rs.getString("puesto"));
        emp.setEmail(rs.getString("email"));
        return emp;
    }

}
