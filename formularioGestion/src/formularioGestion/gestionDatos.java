
package formularioGestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class gestionDatos {
    
    //ATRIBUTOS 
    private int codigo, cantidad;
    private String nombre, descripcion, prodAlmacenados;
    private boolean busquedaEncontrada;
    private static boolean codigoNumerico;
    private static boolean cantidadNumerico;
    private boolean codRepetido;
    
    //CONSTRUCTORES
    public gestionDatos() {
        this.codigo = 0;
        this.cantidad = 0;
        this.nombre = "";
        this.descripcion = "";
        this.prodAlmacenados = "";
        this.busquedaEncontrada = false;
        this.codigoNumerico = false;
        this.cantidadNumerico = false;
        this.codRepetido = false;
              
    }
        
    //GETTERS Y SETTERS
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProdAlmacenados() {
        return prodAlmacenados;
    }

    public void setProdAlmacenados(String prodAlmacenados) {
        this.prodAlmacenados = prodAlmacenados;
    }

    public boolean isBusquedaEncontrada() {
        return busquedaEncontrada;
    }

    public void setBusquedaEncontrada(boolean busquedaEncontrada) {
        this.busquedaEncontrada = busquedaEncontrada;
    }

    public static boolean isCodigoNumerico() {
        return codigoNumerico;
    }

    public static void setCodigoNumerico(boolean codigoNumerico) {
        gestionDatos.codigoNumerico = codigoNumerico;
    }

    public static boolean isCantidadNumerico() {
        return cantidadNumerico;
    }

    public static void setCantidadNumerico(boolean cantidadNumerico) {
        gestionDatos.cantidadNumerico = cantidadNumerico;
    }

    public boolean isCodRepetido() {
        return codRepetido;
    }

    public void setCodRepetido(boolean codRepetido) {
        this.codRepetido = codRepetido;
    }
    
    
    // MÉTODOS

    public void insertaDatos(int codigo, String nombre, int cantidad, String descripcion){
        
        try{
            //Crea el archivo
            File ruta = new File("productos.txt");  
            FileWriter fw = new FileWriter (ruta, true);
            BufferedWriter bw = new BufferedWriter(fw);


            //Escribe en el archivo incluyendo texto informativo de que es cada dato.
            bw.write("Código: ");
            bw.write(Integer.toString(codigo));
            bw.write(", Nombre: ");
            bw.write(nombre);
            bw.write(", Cantidad: ");
            bw.write(Integer.toString(cantidad));
            bw.write(", Descripción: ");
            bw.write(descripcion);
            //Aquí después de escribir todo, damos un salto de linea para estar preparado para el siguiente producto.
            bw.newLine();
           //Actualiza el archivo
            bw.flush();

            //Y cerramos la escritura
            bw.close();
         
            
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());

            
        }
        
    }
    
    public void muestraDatos() {
        
        
        //Marcamos la ubicación del archivo a leer
        try{
            File ruta = new File("productos.txt");
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader (fr);
            String linea;
            
            /*Leemos el archivo y entre cada linea hacemos un salto de linea y lo guardamos
            todo en un String "prodAlmacenados" para enviarlo a nuestra caja de texto*/
            while ((linea = br.readLine()) != null ){
                
              prodAlmacenados = prodAlmacenados + linea + "\n";
                           
            }
            //Cerramos la lectura
            br.close();
         
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        
             
    }
    //Busqueda de producto usando el número del código
    public void buscaProducto(String codigo){
        //Marcamos el archivo a leer
        try{
        File ruta = new File("productos.txt");
        FileReader fr = new FileReader(ruta);
        BufferedReader br = new BufferedReader(fr);
        
        String linea;
        String resultado = "";
        String modificaciones ="";
        
        boolean busquedaOk = false;
        //Leemos todas las lineas
        while ((linea = br.readLine()) != null ){
              resultado = linea;
              //Eliminamos la primera parte que pone "Código"
              modificaciones = linea.replace("Código: ", "");
              // Sacamos únicamente la parte númerica donde aparece el código y la comparamos con la que le hemos pasado.
              busquedaOk = (Integer.parseInt(codigo)) == Integer.parseInt(modificaciones.substring(0, modificaciones.indexOf(","))) ;
              //Si la comparación da true entraría en este condicional.
              if(busquedaOk == true){
                    /*Este código de nuestro if, se encarga de ir borrando las partes no necesarias, para quedarnos únicamente con
                  la parte del Código, Nombre, Cantidad y Descripción del producto que estamos buscando.*/ 
                    resultado = resultado.replace("Código: ", "");
                    this.codigo = Integer.parseInt(resultado.substring(0, resultado.indexOf(',')));
                    resultado = resultado.replaceFirst(this.codigo + ", ", "");

                    resultado = resultado.replace("Nombre: ", "");
                    this.nombre = resultado.substring(0, resultado.indexOf(','));
                    resultado = resultado.replaceFirst(this.nombre + ", ", "");

                    resultado = resultado.replace("Cantidad: ", "");
                    this.cantidad = Integer.parseInt(resultado.substring(0, resultado.indexOf(',')));
                    resultado = resultado.replaceFirst(this.cantidad + ", ", "");

                    resultado = resultado.replace("Descripción: ", "");
                    this.descripcion = resultado.substring(0, resultado.length());
                    resultado = resultado.replaceFirst(this.descripcion + ", ", "");
                    
                    /*Con esto le diremos a nuestro programa que la busqueda ha sido realizada con exito, 
                    en el caso contrario, aparecería una ventana de error/información.*/
                    busquedaEncontrada = true;
                    
              }     
            }
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
         
    }
    /*Esto es un validador númerico, el cual se encarga de validar que los datos como código y cantidad
    sean númericos, y no de pie a escribir texto*/
    public static void validadorNumerico (String codigo, String cantidad){
        
    /*Comprueba si el código es númerico devolviendo un boolean "true" o "false"
    en el caso de que al hacer un parseInt de algún tipo de error.*/
    try{
        //Aquí comprobamos si el código es númerico
        Integer.parseInt(codigo);
        
        codigoNumerico = true;
    }
    catch(NumberFormatException codNoNum){
        codigoNumerico = false;
    }
    
    
    try{
        // Y aquí comprobamos si la cantidad es númerica.
        Integer.parseInt(cantidad);
        
        cantidadNumerico = true;
    }
    catch (NumberFormatException cantidadNoNum){
        cantidadNumerico = false;
    }
    
    }
    
    public void comprobadorCodigos(String codigo){
        //Marcamos el archivo a leer
        try{
        File ruta = new File("productos.txt");
        FileReader fr = new FileReader(ruta);
        BufferedReader br = new BufferedReader(fr);
        
        String linea;
        String resultado = "";
        String modificaciones ="";
        
        //Leemos todas las lineas
        while ((linea = br.readLine()) != null ){
              resultado = linea;
              //Eliminamos la primera parte que pone "Código"
              modificaciones = linea.replace("Código: ", "");
              /*Y directamente lo añadimos como condición para nuestro if, en el caso de que sean el mismo,
              pasará nuestro boolean codRepetido a true para comprobar que no haya repeticiones.*/
              if((Integer.parseInt(codigo)) == Integer.parseInt(modificaciones.substring(0, modificaciones.indexOf(",")))){
                  codRepetido = true;
              }
              
            }
        }
        catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    
    }
    
    
    
}
