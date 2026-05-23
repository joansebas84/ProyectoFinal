package co.edu.poli.agro.modelo;

/**
 * Clase que representa un pesticida agrícola.
 * <p>
 * Extiende {@link Producto} e incorpora atributos específicos de los pesticidas,
 * como la plaga objetivo a controlar, el nivel de toxicidad y observaciones de uso.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see Producto
 */
public class Pesticida extends Producto {

    /** Nombre o descripción de la plaga que el pesticida está diseñado para controlar. */
    private String plagaObjetivo;

    /** Nivel de toxicidad del pesticida (por ejemplo: alto, medio, bajo). */
    private String toxicidad;

    /** Observaciones adicionales sobre el uso o precauciones del pesticida. */
    private String observaciones;

    /**
     * Constructor vacío requerido para procesos de deserialización.
     */
    public Pesticida() {}

    /**
     * Constructor parametrizado que inicializa todos los atributos del pesticida.
     *
     * @param id                 identificador único del producto
     * @param nombre             nombre del pesticida
     * @param cantidadDisponible cantidad disponible en inventario
     * @param unidadmedida       unidad de medida de la cantidad (kg, L, etc.)
     * @param fechaAplicacion    fecha de aplicación del producto (dd/mm/aaaa)
     * @param plagaObjetivo      plaga que el pesticida está diseñado para controlar
     * @param toxicidad          nivel de toxicidad del pesticida
     * @param observaciones      observaciones adicionales sobre el uso
     */
    public Pesticida(int id, String nombre, double cantidadDisponible,
                     String unidadmedida, String fechaAplicacion,
                     String plagaObjetivo, String toxicidad, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.plagaObjetivo = plagaObjetivo;
        this.toxicidad = toxicidad;
        this.observaciones = observaciones;
    }

    /**
     * Retorna la plaga objetivo del pesticida.
     *
     * @return la plaga que el pesticida está diseñado para controlar
     */
    public String getPlagaObjetivo() { return plagaObjetivo; }

    /**
     * Establece la plaga objetivo del pesticida.
     *
     * @param plagaObjetivo la nueva plaga objetivo a asignar
     */
    public void setPlagaObjetivo(String plagaObjetivo) { this.plagaObjetivo = plagaObjetivo; }

    /**
     * Retorna el nivel de toxicidad del pesticida.
     *
     * @return el nivel de toxicidad (alto, medio, bajo, etc.)
     */
    public String getToxicidad() { return toxicidad; }

    /**
     * Establece el nivel de toxicidad del pesticida.
     *
     * @param toxicidad el nuevo nivel de toxicidad a asignar
     */
    public void setToxicidad(String toxicidad) { this.toxicidad = toxicidad; }

    /**
     * Retorna las observaciones adicionales del pesticida.
     *
     * @return las observaciones de uso o precauciones
     */
    public String getObservaciones() { return observaciones; }

    /**
     * Establece las observaciones adicionales del pesticida.
     *
     * @param observaciones las nuevas observaciones a registrar
     */
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    /**
     * Retorna una representación textual completa del pesticida,
     * incluyendo los datos heredados de {@link Producto} y los atributos propios.
     *
     * @return cadena con tipo, datos base, plaga objetivo, toxicidad y observaciones
     */
    @Override
    public String toString() {
        return "[PESTICIDA] " + super.toString() +
               " | Plaga: " + plagaObjetivo +
               " | Toxicidad: " + toxicidad +
               " | Obs: " + observaciones;
    }
}