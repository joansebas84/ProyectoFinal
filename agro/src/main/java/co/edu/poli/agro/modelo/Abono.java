package co.edu.poli.agro.modelo;

public class Abono extends Producto {

    private String origen;
    private int tiempoDescomposicion;
    private String observaciones;

    public Abono() {}

    public Abono(int id, String nombre, double cantidadDisponible,
                 String unidadmedida, String fechaAplicacion,
                 String origen, int tiempoDescomposicion, String observaciones) {
        super(id, nombre, cantidadDisponible, unidadmedida, fechaAplicacion);
        this.origen = origen;
        this.tiempoDescomposicion = tiempoDescomposicion;
        this.observaciones = observaciones;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public int getTiempoDescomposicion() { return tiempoDescomposicion; }
    public void setTiempoDescomposicion(int tiempoDescomposicion) {
        this.tiempoDescomposicion = tiempoDescomposicion;
    }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "[ABONO] " + super.toString() +
               " | Origen: " + origen +
               " | Descomp: " + tiempoDescomposicion + " dias" +
               " | Obs: " + observaciones;
    }
}