package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Producto;

/**
 * Interfaz que define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * para la gestión de productos agrícolas en el sistema.
 * <p>
 * Las clases que implementen esta interfaz deben proveer la lógica
 * de almacenamiento y manipulación de objetos {@link Producto}.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see Producto
 */
public interface OperacionCRUD {

    /**
     * Registra un nuevo producto en el sistema.
     *
     * @param producto el objeto {@link Producto} a registrar
     * @return mensaje indicando el resultado de la operación
     */
    String crear(Producto producto);

    /**
     * Recupera un producto del sistema según su índice en el arreglo interno.
     *
     * @param indice la posición del producto en el arreglo (base 0)
     * @return el {@link Producto} en la posición indicada, o {@code null} si el índice es inválido
     */
    Producto leer(int indice);

    /**
     * Recupera todos los productos registrados en el sistema.
     *
     * @return arreglo con todos los productos registrados; arreglo vacío si no hay ninguno
     */
    Producto[] leertodo();

    /**
     * Modifica un producto existente en la posición indicada del arreglo interno.
     *
     * @param indice   la posición del producto a modificar (base 0)
     * @param producto el nuevo objeto {@link Producto} con los datos actualizados
     * @return mensaje indicando el resultado de la operación
     */
    String modificar(int indice, Producto producto);

    /**
     * Elimina el producto ubicado en la posición indicada del arreglo interno.
     *
     * @param indice la posición del producto a eliminar (base 0)
     * @return mensaje indicando el resultado de la operación
     */
    String eliminar(int indice);
}