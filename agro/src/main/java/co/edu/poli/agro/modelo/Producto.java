package co.edu.poli.agro.modelo;

public abstract class Producto {

    private int id;
    private String nombre;
    private double cantidadDisponible;
    private String unidadmedida;
    private String fechaAplicacion;

    public Producto() {}

    public Producto(int id, String nombre, double cantidadDisponible,
                    String unidadmedida, String fechaAplicacion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.unidadmedida = unidadmedida;
        this.fechaAplicacion = fechaAplicacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getUnidadmedida() { return unidadmedida; }
    public void setUnidadmedida(String unidadmedida) { this.unidadmedida = unidadmedida; }

    public String getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Nombre: " + nombre +
               " | Cantidad: " + cantidadDisponible + " " + unidadmedida +
               " | Fecha: " + fechaAplicacion;
    }
}