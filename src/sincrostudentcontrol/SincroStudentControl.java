/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sincrostudentcontrol;

import BackEnd.Conector;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author angel
 */
public class SincroStudentControl {

    
    Conector Local_Select = new Conector("Local");
    Conector Local_Update = new Conector("Local");
    Conector Nube = new Conector("Nube");
    
    public static void main(String[] args) {
        SincroStudentControl Sincro = new SincroStudentControl();
        Sincro.Sincro();
    }
    
    private void Sincro(){
        String Cambio_Local = "SELECT Correlativo, Query FROM bitacora WHERE Estado = false";
        String Cambio_Nube;
        int Id = 0;
        
        try{
            ResultSet rs = Local_Select.getSt().executeQuery(Cambio_Local);
            while(rs.next()){
                Cambio_Nube = rs.getString("Query");
                Cambio_Nube = Cambio_Nube.replace("`", "'");
                Id = rs.getInt("Correlativo");
                int Resultado = Nube.getSt().executeUpdate(Cambio_Nube);
                if(Resultado > 0){
                    Cambio_Local = "UPDATE bitacora SET Estado = TRUE WHERE Correlativo  = " + rs.getInt("Correlativo");
                    Local_Update.getSt().executeUpdate(Cambio_Local);
                }
            }
            Local_Select.Close();
            Nube.Close();
            Local_Update.Close();
        }catch(Exception e){
            try{
                String Query_Error = "INSERT INTO log_errores_replicacion VALUES(CURRENT_DATE, "+Id+", '"+e.getMessage().replace("'", "`")+"')";
                Local_Update.getSt().executeUpdate(Query_Error);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Ocurrio un error en la Sincronizacion de datos a la pagina web\nFavor Comunicarse con Soporte Tecnico", "Student Control", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
