package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Producto;

/**
 * Interfaz que define las operaciones de persistencia en archivo
 * para los productos agrícolas del sistema.
 * <p>
 * Las clases que implementen esta interfaz deben proveer la lógica
 * para guardar y recuperar objetos {@link Producto} desde un archivo de texto
 * con formato delimitado por el carácter {@code |}.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see Producto
 */
public interface OperacionArchivo {

    /**
     * Serializa todos los productos registrados en el sistema y los guarda
     * en el archivo {@code productos.txt}, sobreescribiendo el contenido previo.
     *
     * @return mensaje indicando el resultado de la operación de guardado
     */
    String serializar();

    /**
     * Deserializa los productos almacenados en el archivo {@code productos.txt}
     * y los carga en el arreglo interno del sistema.
     *
     * @return arreglo con los productos cargados desde el archivo;
     *         arreglo vacío si el archivo no existe o está vacío
     */
    Producto[] desereralizar();
}