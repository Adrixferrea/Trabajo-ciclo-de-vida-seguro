/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integridad;

/**
 *
 * @author rmadr
 */
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Adrián Martín Moro
 */
public class Integridad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        int opcion;
        boolean salir = false;
        Scanner sn = new Scanner(System.in);
        Scanner user = new Scanner(System.in);
        Scanner password = new Scanner(System.in);
        Operaciones op = new Operaciones();
        Hash hash = new Hash();
        Auditoria au = new Auditoria();
        Usuario u = new Usuario();

        System.out.println("Usuario: ");
        String usuario = user.nextLine();
        u.setUsuario(usuario);
        
        System.out.println("Contraseña: ");
        String contraseña = password.nextLine();
        u.setContraseña(contraseña);

        if (hash.comprobarHashUsuarios() != true) {//Compruebo que esta bien los datos de los usuarios
            System.out.println("Hay un problema de integridad con el dato: " + usuario);
            int opcion2;
            boolean salir2 = false;
            Scanner sn2 = new Scanner(System.in);

            while (!salir) {

                System.out.println("Que desea hacer:");
                System.out.println("1.Eliminar el dato");
                System.out.println("2.Modificar el dato");
                System.out.println("3.Marcar registro como perdida de integridad");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        op.eliminarAmigo(u);
                        
                        break;
                    case 2:
                        op.modificarAmigo(u);
                        break;
                    case 3:
                        op.renombrarusUsuarios(u);
                        break;
                }
            }
        }

        if (op.leerUsuarios(usuario, contraseña) == true) {//Compruebo que el usuario esta registrado
            if (hash.comprobarHashAmigos() == 0 ) {//compruebo que los datos de los amigos no hallan sido modificados 
                int opcion2;
                boolean salir2 = false;
                Scanner sn2 = new Scanner(System.in);

                while (!salir) {

                    System.out.println("Que desea hacer:");
                    System.out.println("1.Eliminar el dato");
                    System.out.println("2.Modificar el dato");
                    System.out.println("3.Marcar registro como perdida de integridad");
                    opcion = sn.nextInt();

                    switch (opcion) {
                        case 1:
                            op.eliminarAmigo(u);
                            break;
                        case 2:
                            op.modificarAmigo(u);
                            break;
                        case 3:
                            op.renombrarusUsuarios(u);
                            break;
                        default:
                            System.out.println("Solo números entre 1 y 3");
                    }
                }
            }
             System.out.println("No tienes amgios, recuerda agregar uno!");
            while (!salir) {//comienzo con el menu del usuario
                int i = op.ComprobarRol();
                op.ComprobarRolMenu(i);

                System.out.println("6. Salir");
              

                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1://agrego a un amigo
                        //1=admin 2=gestor 3=ayudante
                        if (op.ComprobarRol() == 1 || op.ComprobarRol() == 2) {//compruebo los roles
                            op.agregarAmigo(u);
                        } else {
                            System.out.println("No tienes permisos para realizar esta acción");
                        }

                        break;
                    case 2://muestro los amigos
                        if (op.ComprobarRol() == 1 || op.ComprobarRol() == 2 || op.ComprobarRol() == 3) {
                            op.leerAmigos(u);
                        } else {
                            System.out.println("No tienes permisos para realizar esta acción");
                        }

                        break;
                    case 3://agrego a un nuevo usuario
                        if (op.ComprobarRol() == 1) {
                            op.agregarUsuario(u);
                        } else {
                            System.out.println("No tienes permisos para realizar esta acción");
                            break;
                        }
                        break;
                    case 4://modifico a un amigo
                        if (op.ComprobarRol() == 1 || op.ComprobarRol() == 2) {
                            op.modificarAmigo(u);
                        } else {
                            System.out.println("No tienes permisos para realizar esta acción");
                        }
                        break;
                    case 5://elimino a un amigo
                        if (op.ComprobarRol() == 1) {
                            op.eliminarAmigo(u);
                        } else {
                            System.out.println("No tienes permisos para realizar esta acción");
                        }
                        break;
                    case 6://salir
                        salir= op.Salir(u);
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 6");
                }

            }
        } else {
            System.out.println("Usuario o contraseña incorrecta");
        }

    }
}
