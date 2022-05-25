package conexionbasededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Metodos {
   //creamos las variables para la conexion
    static Connection cn;
    static Statement s;
    static ResultSet rs;
    DefaultTableModel modelo= new DefaultTableModel();
  //creamos la operacion para mostrar datos en una jtable en el jform
    public DefaultTableModel lista(){
        
    try{
    cn = Metodos.Conectar();
    Statement s= cn.createStatement();
    //consuta a mostrar
    String query = "select * from usuarios";
    rs = s.executeQuery(query);
   ResultSetMetaData rsmd=rs.getMetaData();
   //obtenemos numero de columnas 
   int CanColumns = rsmd.getColumnCount();
    //comprobamos 
   for(int i=1;i<=CanColumns;i++){
       //cargamos columnas en modelo
   modelo.addColumn(rsmd.getColumnLabel(i));
   }
   while (rs.next()){
   //creamos array 
       Object[] fila=new Object[CanColumns];
   //cargamos datos a modelo
   for(int i=0;i<CanColumns;i++){
   fila[i] = rs.getObject(i+1);
   }
   modelo.addRow(fila);
   }
    }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    //retornamos modelo para jtable
    return modelo;
    
    }
    
    
    public static Connection Conectar()throws SQLException{
    //ruta de la base de datos la cual crearemos
        String ruta = "C:\\Users\\david\\OneDrive\\Escritorio\\usuarios.db";
        try{
        //Class.forName("org.sqlite.JDBC");
        cn = DriverManager.getConnection("jdbc:sqlite:"+ruta);        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null, e);
        }
        return cn;
    }
    
    
    public void AgregarConsulta(String nombre,int edad,String correo,String contraseña) {
        String sql = "INSERT INTO usuarios(nombre,edad,correo,contraseña) VALUES(?,?,?,?)";

        try (Connection cn = Metodos.Conectar();
                PreparedStatement pstmt = cn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, edad);
            pstmt.setString(3, correo);
            pstmt.setString(4, contraseña);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
        //CREAMOS METODO PARA ELIMINAR DATOS
        public void EliminarConsulta(String nombre){
       try{
           cn=Metodos.Conectar();
       Statement s=cn.createStatement();
       String query="DELETE FROM usuarios WHERE nombre='"+nombre+"'";
       s.executeUpdate(query);
       s.close();
       cn.close();
       JOptionPane.showMessageDialog(null, "ELIMINADO");
       }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        
        }
        //creamos metodo para modificar datos
        public void ModificarConsulta(String nombre,int edad,String correo,String contraseña){
        try{
            cn=Metodos.Conectar();
        Statement s=cn.createStatement();
        String query="UPDATE usuarios SET edad='"+edad+"',correo='"+correo+"',contraseña='"+contraseña+"' WHERE nombre='"+nombre+"'";
        s.executeUpdate(query);
        s.close();
        cn.close();
        JOptionPane.showMessageDialog(null, "MODIFICADO");
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        
        
        }

      
}