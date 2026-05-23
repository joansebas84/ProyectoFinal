package co.edu.poli.agro.modelo;

/**
 * Clase abstracta que representa un producto agrícola genérico.
 * <p>
 * Sirve como clase base para los tipos específicos de productos:
 * {@link Fertilizante}, {@link Pesticida} y {@link Abono}.
 * Contiene los atributos comunes a todos los productos del sistema.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 */
public abstract class Producto {

    /** Identificador único del producto, asignado automáticamente por el sistema. */
    private int id;

    /** Nombre descriptivo del producto agrícola. */
    private String nombre;

    /** Cantidad disponible del producto en inventario. */
    private double cantidadDisponible;

    /** Unidad de medida asociada a la cantidad (por ejemplo: kg, L, g). */
    private String unidadmedida;

    /** Fecha en que se aplicará o aplicó el producto (formato dd/mm/aaaa). */
    private String fechaAplicacion;

    /**
     * Constructor vacío requerido para procesos de deserialización.
     */
    public Producto() {}

    /**
     * Constructor parametrizado que inicializa todos los atributos del producto.
     *
     * @param id                 identificador único del producto
     * @param nombre             nombre del producto agrícola
     * @param cantidadDisponible cantidad disponible en inventario
     * @param unidadmedida       unidad de medida de la cantidad (kg, L, etc.)
     * @param fechaAplicacion    fecha de aplicación del producto (dd/mm/aaaa)
     */
    public Producto(int id, String nombre, double cantidadDisponible,
                    String unidadmedida, String fechaAplicacion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.unidadmedida = unidadmedida;
        this.fechaAplicacion = fechaAplicacion;
    }

    /**
     * Retorna el identificador único del producto.
     *
     * @return el ID del producto
     */
    public int getId() { return id; }

    /**
     * Establece el identificador único del producto.
     *
     * @param id el nuevo ID a asignar
     */
    public void setId(int id) { this.id = id; }

    /**
     * Retorna el nombre del producto.
     *
     * @return el nombre del producto
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre el nuevo nombre a asignar
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Retorna la cantidad disponible del producto en inventario.
     *
     * @return la cantidad disponible
     */
    public double getCantidadDisponible() { return cantidadDisponible; }

    /**
     * Establece la cantidad disponible del producto en inventario.
     *
     * @param cantidadDisponible la nueva cantidad disponible
     */
    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * Retorna la unidad de medida del producto.
     *
     * @return la unidad de medida (kg, L, g, etc.)
     */
    public String getUnidadmedida() { return unidadmedida; }

    /**
     * Establece la unidad de medida del producto.
     *
     * @param unidadmedida la nueva unidad de medida
     */
    public void setUnidadmedida(String unidadmedida) { this.unidadmedida = unidadmedida; }

    /**
     * Retorna la fecha de aplicación del producto.
     *
     * @return la fecha de aplicación en formato dd/mm/aaaa
     */
    public String getFechaAplicacion() { return fechaAplicacion; }

    /**
     * Establece la fecha de aplicación del producto.
     *
     * @param fechaAplicacion la nueva fecha en formato dd/mm/aaaa
     */
    public void setFechaAplicacion(String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    /**
     * Retorna una representación textual del producto con sus atributos principales.
     *
     * @return cadena con ID, nombre, cantidad, unidad y fecha de aplicación
     */
    @Override
    public String toString() {
        return "ID: " + id +
               " | Nombre: " + nombre +
               " | Cantidad: " + cantidadDisponible + " " + unidadmedida +
               " | Fecha: " + fechaAplicacion;
    }
}