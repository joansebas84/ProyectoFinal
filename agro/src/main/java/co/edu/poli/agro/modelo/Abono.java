package co.edu.poli.agro.modelo;

/**
 * Clase que representa un abono agrícola.
 * <p>
 * Extiende {@link Producto} e incorpora atributos específicos de los abonos,
 * como el origen del material, el tiempo de descomposición estimado en días
 * y observaciones adicionales de uso.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see Producto
 */
public class Abono extends Producto {

    /** Origen del abono (por ejemplo: animal, vegetal, mineral). */
    private String origen;

    /** Tiempo estimado de descomposición del abono expresado en días. */
    private int tiempoDescomposicion;

    /** Observaciones adicionales sobre el uso o aplicación del abono. */
    private String observaciones;

    /**
     * Constructor vacío requerido para procesos de deserialización.
     */
    public Abono() {}

    /**
     * Constructor parametrizado que inicializa todos los atributos del abono.
     *
     * @param id                   identificador único del producto
     * @param nombre               nombre del abono
     * @param cantidadDisponible   cantidad disponible en inventario
     * @param unidadmedida         unidad de medida de la cantidad (kg, L, etc.)
     * @param fechaAplicacion      fecha de aplicación del producto (dd/mm/aaaa)
     * @param origen               origen del abono (animal, vegetal, mineral, etc.)
     * @param tiempoDescomposicion tiempo de descomposición estimado en días
     * @param observaciones        observaciones adicionales sobre el uso
     */
    public Abono(int id, String nombre, double cantidadDisponible,
                 String unidadmedida, String fechaAplicacion,
                 String origen, int tiempoDescomposicion, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.origen = origen;
        this.tiempoDescomposicion = tiempoDescomposicion;
        this.observaciones = observaciones;
    }

    /**
     * Retorna el origen del abono.
     *
     * @return el origen del abono (animal, vegetal, mineral, etc.)
     */
    public String getOrigen() { return origen; }

    /**
     * Establece el origen del abono.
     *
     * @param origen el nuevo origen a asignar
     */
    public void setOrigen(String origen) { this.origen = origen; }

    /**
     * Retorna el tiempo de descomposición estimado del abono en días.
     *
     * @return los días estimados de descomposición
     */
    public int getTiempoDescomposicion() { return tiempoDescomposicion; }

    /**
     * Establece el tiempo de descomposición estimado del abono.
     *
     * @param tiempoDescomposicion el nuevo tiempo en días
     */
    public void setTiempoDescomposicion(int tiempoDescomposicion) {
        this.tiempoDescomposicion = tiempoDescomposicion;
    }

    /**
     * Retorna las observaciones adicionales del abono.
     *
     * @return las observaciones de uso o aplicación
     */
    public String getObservaciones() { return observaciones; }

    /**
     * Establece las observaciones adicionales del abono.
     *
     * @param observaciones las nuevas observaciones a registrar
     */
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    /**
     * Retorna una representación textual completa del abono,
     * incluyendo los datos heredados de {@link Producto} y los atributos propios.
     *
     * @return cadena con tipo, datos base, origen, tiempo de descomposición y observaciones
     */
    @Override
    public String toString() {
        return "[ABONO] " + super.toString() +
               " | Origen: " + origen +
               " | Descomp: " + tiempoDescomposicion + " dias" +
               " | Obs: " + observaciones;
    }
}