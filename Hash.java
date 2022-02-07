package integridad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rmadr
 */
public class Hash {

    ArrayList<String> hashAntiguoUsuarios = new ArrayList<String>();
    ArrayList<String> hashNuevoUsuarios = new ArrayList<String>();
    ArrayList<String> hashAntiguoAmigos = new ArrayList<String>();
    ArrayList<String> hashNuevoAmigos = new ArrayList<String>();
    private static final String ruta = "Usuarios.txt";//ruta donde se van a encontrar los usuarios

    public String crearHash(String usuario) throws NoSuchAlgorithmException {
        //Crear hash

        MessageDigest md5 = MessageDigest.getInstance("SHA-384");
        md5.update(usuario.getBytes());
        byte resultado[] = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultado.length; i++) {
            sb.append(String.format("%02x", resultado[i]));
        }
        return sb.toString();
    }

    public void anadirHashUsuario(String dato, int opcion) throws NoSuchAlgorithmException {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            File file = new File("UsuariosHash.txt");

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            String datoHash = crearHash(dato);
            if (opcion == 1) {
                bw.write(datoHash + "\n");
                System.out.println("información agregada!");
            } else {
                hashNuevoUsuarios.add(datoHash);
            }

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

    public void leerUsuarios() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(ruta);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null && a != true) {

                anadirHashUsuario(linea, 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void añadirHashAmigo(String nombreUsuario, String dato, int opcion) throws NoSuchAlgorithmException {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {

            File file = new File(nombreUsuario + "Hash.txt");

            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            String datoHash = crearHash(dato);
            if (opcion == 1) {
                bw.write(datoHash + "\n");
                System.out.println("información agregada!");
            } else {
                hashNuevoAmigos.add(datoHash);
            }
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

    public void leerAmigos(String user) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(user + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null && a != true) {

                añadirHashAmigo(user, linea, 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void eliminarHash(String usuario, String dato) throws NoSuchAlgorithmException {
        try {

            File inFile = new File(usuario + "Hash.txt");

            if (!inFile.isFile()) {
                System.out.println("no hay file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(usuario + "Hash.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            String nombre = crearHash(dato);
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(nombre)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void leerHashAntiguoUsuarios() {//muestra los amigos que tienes añadidos
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            archivo = new File("UsuariosHash.txt");

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                hashAntiguoUsuarios.add(linea);

            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado Hash para este usuario en el fichero");
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void leerHashNuevoUsuarios() {//muestra los amigos que tienes añadidos
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            archivo = new File("UsuariosHash.txt");

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                hashAntiguoUsuarios.add(linea);

            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado ficheros Hash para este usuario");
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void crearHashAntiguoAmigos(String nombre) {//muestra los amigos que tienes añadidos
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            archivo = new File(nombre + "Hash.txt");

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                hashAntiguoAmigos.add(linea);

            }
        } catch (Exception e) {
            System.out.println("No se ha encontrado ficheros Hash para este usuario");
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean comprobarHashUsuarios() {
        leerUsuarios();
        leerHashAntiguoUsuarios();
        boolean u = false;

        for (String antiguo : hashAntiguoUsuarios) {
            for (String nuevo : hashNuevoUsuarios) {
                if (nuevo.equals(antiguo)) {
                    u = true;
                } else {
                    return u = false;
                }
            }
        }
        return u;
    }

    public int comprobarHashAmigos() {
        leerUsuarios();
        leerHashAntiguoUsuarios();
        int u = 0;
        if (hashAntiguoAmigos.size() != 0) {
            for (String antiguo : hashAntiguoAmigos) {
                for (String nuevo : hashNuevoAmigos) {
                    if (nuevo.equals(antiguo)) {
                        u = 1;//el hash es correcto
                    } else {
                        System.out.println("Hay un problema de integridad con el dato: " + nuevo);
                        u = 0;//hash no es correcto
                    }
                }
            }

        } else {
            System.out.println("NO tienes amigos");
            return u = 2;//no tiene amigos
        }

        return u;
    }
}
