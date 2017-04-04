/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.VCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class FCliente {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalRegistros;

    /*MOSTRAR*/
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;

        String[] titulos = {"ID", "Nombre", "APaterno", "AMaterno", "Doc", "Número Documento", "Dirección", "Teléfono", "Email", "Código"};

        String[] registro = new String[10];

        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT p.idpersona, p.nombre, p.apaterno, p.amaterno, p.tipo_documento, p.num_documento, "
                + "p.direccion, p.telefono, p.email, c.codigo_cliente FROM persona p INNER JOIN cliente c "
                + "ON p.idpersona=c.idpersona WHERE num_documento like '%"
                + buscar + "%' ORDER BY idpersona DESC";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apaterno");
                registro[3] = rs.getString("amaterno");
                registro[4] = rs.getString("tipo_documento");
                registro[5] = rs.getString("num_documento");
                registro[6] = rs.getString("direccion");
                registro[7] = rs.getString("telefono");
                registro[8] = rs.getString("email");
                registro[9] = rs.getString("codigo_cliente");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registro);
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    /*INSERTAR*/
    public boolean insertar(VCliente dts) {

        sSQL = "INSERT INTO persona (nombre, apaterno, amaterno, tipo_documento, num_documento, direccion, telefono, email)"
                + "values(?,?,?,?,?,?,?,?)";
        sSQL2 = "INSERT INTO cliente (idpersona, codigo_cliente)"
                + "values((SELECT idpersona FROM persona ORDER BY idpersona DESC LIMIT 1), ?)";
        try {

            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApaterno());
            pst.setString(3, dts.getAmaterno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());

            pst2.setString(1, dts.getCodigo_cliente());

            int n = pst.executeUpdate();

            if (n != 0){
                int n2 = pst2.executeUpdate();
                
                if (n2 != 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
            

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }

    /*EDITAR*/
    public boolean editar(VCliente dts) {

        sSQL = "UPDATE persona SET nombre=?, apaterno=?, amaterno=?, tipo_documento=?, num_documento=?, "
                + " direccion=?, telefono=?, email=? WHERE idpersona=?";
        
        sSQL2 = "UPDATE cliente SET codigo_cliente=?"
                + " WHERE idpersona=?";

        try {

            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApaterno());
            pst.setString(3, dts.getAmaterno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getIdpersona());

            pst2.setString(1, dts.getCodigo_cliente());
            pst2.setInt(2, dts.getIdpersona());

            int n = pst.executeUpdate();

            if (n != 0){
                int n2 = pst2.executeUpdate();
                
                if (n2 != 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
            

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }

    /*ELIMINAR*/
    public boolean eliminar(VCliente dts) {

        sSQL = "DELETE FROM cliente WHERE idpersona=?";
        sSQL2 = "DELETE FROM persona WHERE idpersona=?";

        try {

            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setInt(1, dts.getIdpersona());
            

            pst2.setInt(1, dts.getIdpersona());

            int n = pst.executeUpdate();

            if (n != 0){
                int n2 = pst2.executeUpdate();
                
                if (n2 != 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
            

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }

}
