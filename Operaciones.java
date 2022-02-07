package integridad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Adrián Martín Moro
 */
public class Operaciones {

    private String[] partes = null;//sirve para desglosar el nombre de usuario la contraseña y el tipo de rol de cada usuario
    private static final String ruta = "Usuarios.txt";//ruta donde se van a encontrar los usuarios
    Hash h = new Hash();
    Auditoria au = new Auditoria();
    Usuario u = new Usuario();
    

    public boolean leerUsuarios(String user, String password) {
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
                partes = linea.split(";");
                //if(h.comprobarUsuarios() == true){
                if (partes[0].equals(user) && partes[1].equals(password)){
                    a = true;
                }
                //}

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
        return a;
    }

    public void ComprobarRolMenu(int i) {//en este metodo segun el rol de cada usuario se mostrara un menú u otro
        if (i == 1) {
            System.out.println("1.Agregar amigo");
            System.out.println("2.Ver amigos");
            System.out.println("3.Agregar usuario");
            System.out.println("4.Modificar amigo");
            System.out.println("5.Eliminar amigo");
        } else if (i == 2) {
            System.out.println("1.Agregar amigo");
            System.out.println("2.Ver amigos");
            System.out.println("4.Modificar amigo");

        } else if (i == 3) {
            System.out.println("2.Ver amigos");

        }

    }

    public int ComprobarRol() {//comprueba el rol de cada usuario

        int a = 0;
        if (partes[2].equals("1")) {
            a = 1;//admin
        } else if (partes[2].equals("2")) {
            a = 2;//gestor
        } else if (partes[2].equals("3")) {
            a = 3;//ayudante
        }
        return a;
    }

    public void agregarAmigo(Usuario usuario) throws NoSuchAlgorithmException {
        BufferedWriter bw = null;
        FileWriter fw = null;
        Scanner sn = new Scanner(System.in);

        System.out.println("Dime el nombre de tu amigo: ");
        String nombre = sn.nextLine();

        try {

            File file = new File(partes[0] + ".txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(nombre + "\n");
            h.añadirHashAmigo(partes[0], nombre, 1);
            System.out.println("información agregada!");
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
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("agregarAmigo", "",nombre, usuario.getUsuario());
    }

    public void leerAmigos(Usuario usuario) {//muestra los amigos que tienes añadidos
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        boolean a = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(partes[0] + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);

            }
        } catch (Exception e) {
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
        
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("MostrarAmigos","","", usuario.getUsuario());
    }

    public void agregarUsuario(Usuario u) throws NoSuchAlgorithmException {
        BufferedWriter bw = null;
        FileWriter fw = null;
        Scanner user = new Scanner(System.in);
        Scanner password = new Scanner(System.in);
        Scanner rol = new Scanner(System.in);
        String nombre;
        String contraseña;

        int Rol;
        System.out.println("Dime el nombre del usuario: ");
        nombre = user.nextLine();

        System.out.println("Dime la contraseña del usuario: ");
        contraseña = password.nextLine();
        System.out.println("Dime el rol del usuario: ");
        System.out.println("1.Admin");
        System.out.println("2.Gerente");
        System.out.println("3.Ayudante");
        Rol = rol.nextInt();
        String dato = nombre + contraseña + Rol;
        try {

            File file = new File("Usuarios.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            String usuario = nombre + ";" + contraseña + ";" + Rol;
            bw.write(usuario + "\n");
            h.anadirHashUsuario(dato, 1);
            System.out.println("información agregada!");
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
        
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("AgregarUsuario",dato,"", u.getUsuario());
    }

    public void modificarAmigo(Usuario usuario) throws IOException, NoSuchAlgorithmException {//modifica un amigo de tu agenda

        eliminarAmigo(usuario);
        agregarAmigo(usuario);

    }

    public void eliminarAmigo(Usuario usuario) throws NoSuchAlgorithmException {

        Scanner sn = new Scanner(System.in);

        System.out.println("Dime el nombre de tu amigo: ");
        String nombre = sn.nextLine();
        try {

            File inFile = new File(partes[0] + ".txt");

            if (!inFile.isFile()) {
                System.out.println("no hay file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(partes[0] + ".txt"));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            h.eliminarHash(partes[0], nombre);
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
        
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("eliminarAmigo", nombre,"", usuario.getUsuario());
    }

    public void renombrarusUsuarios(Usuario usuario) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File archivoOriginal = new File("Usuarios.txt");
            File archivoCopia = new File("Usuarios_Perdida_Integridad.txt");
            inputStream = new FileInputStream(archivoOriginal);
            outputStream = new FileOutputStream(archivoCopia);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            System.out.println("Marca de perdida de integridad realizada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("MarcarPerdidaIntegridadUsuarios","","", usuario.getUsuario());
    }
    
        public void renombrarusAmigos(Usuario usuario) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File archivoOriginal = new File(partes[0]+".txt");
            File archivoCopia = new File(partes[0]+"Perdida_Integridad.txt");
            inputStream = new FileInputStream(archivoOriginal);
            outputStream = new FileOutputStream(archivoCopia);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            System.out.println("Marca de perdida de integridad realizada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("MarcarPerdidaIntegridadAmigos","","", usuario.getUsuario());
    }
     
    public boolean Salir(Usuario usuario){
        //String accion, String resultado, String anterior, String usuario, Date fecha
        au.auditar("CierreSesion", "","", usuario.getUsuario());
    return true;
}
        

}
