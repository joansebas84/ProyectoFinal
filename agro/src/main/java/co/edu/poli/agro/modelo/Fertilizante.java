package co.edu.poli.agro.modelo;

public class Fertilizante extends Producto {

    private String tipoFormula;
    private String observaciones;

    public Fertilizante() {}

    public Fertilizante(int id, String nombre, double cantidadDisponible,
                        String unidadmedida, String fechaAplicacion,
                        String tipoFormula, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.tipoFormula = tipoFormula;
        this.observaciones = observaciones;
    }

    public String getTipoFormula() { return tipoFormula; }
    public void setTipoFormula(String tipoFormula) { this.tipoFormula = tipoFormula; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "[FERTILIZANTE] " + super.toString() +
               " | Formula: " + tipoFormula +
               " | Obs: " + observaciones;
    }
}