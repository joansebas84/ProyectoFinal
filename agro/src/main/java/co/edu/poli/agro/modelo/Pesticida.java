package co.edu.poli.agro.modelo;

public class Pesticida extends Producto {

    private String plagaObjetivo;
    private String toxicidad;
    private String observaciones;

    public Pesticida() {}

    public Pesticida(int id, String nombre, double cantidadDisponible,
                     String unidadmedida, String fechaAplicacion,
                     String plagaObjetivo, String toxicidad, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.plagaObjetivo = plagaObjetivo;
        this.toxicidad = toxicidad;
        this.observaciones = observaciones;
    }

    public String getPlagaObjetivo() { return plagaObjetivo; }
    public void setPlagaObjetivo(String plagaObjetivo) { this.plagaObjetivo = plagaObjetivo; }

    public String getToxicidad() { return toxicidad; }
    public void setToxicidad(String toxicidad) { this.toxicidad = toxicidad; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "[PESTICIDA] " + super.toString() +
               " | Plaga: " + plagaObjetivo +
               " | Toxicidad: " + toxicidad +
               " | Obs: " + observaciones;
    }
}