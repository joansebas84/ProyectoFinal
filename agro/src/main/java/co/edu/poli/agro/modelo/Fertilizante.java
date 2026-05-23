package co.edu.poli.agro.modelo;

/**
 * Clase que representa un fertilizante agrícola.
 * <p>
 * Extiende {@link Producto} e incorpora atributos específicos de los fertilizantes,
 * como el tipo de fórmula química utilizada y observaciones adicionales de uso.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see Producto
 */
public class Fertilizante extends Producto {

    /** Tipo de fórmula química del fertilizante (por ejemplo: NPK 10-20-10). */
    private String tipoFormula;

    /** Observaciones adicionales sobre el uso o aplicación del fertilizante. */
    private String observaciones;

    /**
     * Constructor vacío requerido para procesos de deserialización.
     */
    public Fertilizante() {}

    /**
     * Constructor parametrizado que inicializa todos los atributos del fertilizante.
     *
     * @param id                 identificador único del producto
     * @param nombre             nombre del fertilizante
     * @param cantidadDisponible cantidad disponible en inventario
     * @param unidadmedida       unidad de medida de la cantidad (kg, L, etc.)
     * @param fechaAplicacion    fecha de aplicación del producto (dd/mm/aaaa)
     * @param tipoFormula        tipo de fórmula química del fertilizante
     * @param observaciones      observaciones adicionales sobre el uso
     */
    public Fertilizante(int id, String nombre, double cantidadDisponible,
                        String unidadmedida, String fechaAplicacion,
                        String tipoFormula, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.tipoFormula = tipoFormula;
        this.observaciones = observaciones;
    }

    /**
     * Retorna el tipo de fórmula química del fertilizante.
     *
     * @return el tipo de fórmula (por ejemplo: NPK 10-20-10)
     */
    public String getTipoFormula() { return tipoFormula; }

    /**
     * Establece el tipo de fórmula química del fertilizante.
     *
     * @param tipoFormula el nuevo tipo de fórmula a asignar
     */
    public void setTipoFormula(String tipoFormula) { this.tipoFormula = tipoFormula; }

    /**
     * Retorna las observaciones adicionales del fertilizante.
     *
     * @return las observaciones de uso o aplicación
     */
    public String getObservaciones() { return observaciones; }

    /**
     * Establece las observaciones adicionales del fertilizante.
     *
     * @param observaciones las nuevas observaciones a registrar
     */
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    /**
     * Retorna una representación textual completa del fertilizante,
     * incluyendo los datos heredados de {@link Producto} y los atributos propios.
     *
     * @return cadena con tipo, datos base, fórmula y observaciones
     */
    @Override
    public String toString() {
        return "[FERTILIZANTE] " + super.toString() +
               " | Formula: " + tipoFormula +
               " | Obs: " + observaciones;
    }
}