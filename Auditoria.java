package integridad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author rmadr
 */
public class Auditoria {
    
    DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    public void auditar(String accion, String resultado, String anterior, String usuario){
        
        BufferedWriter bw = null;
        FileWriter fw = null;
        LocalDateTime aux = LocalDateTime.now();
        String formatoFecha = fecha.format(LocalDateTime.now());
        String dato = (accion + ";Antiguo:" + resultado + ";Nuevo:" +anterior + ";user:" + usuario+ ";" + formatoFecha); 
        try {

            File file = new File("Auditoria.txt");

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(dato + "\n");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
}
