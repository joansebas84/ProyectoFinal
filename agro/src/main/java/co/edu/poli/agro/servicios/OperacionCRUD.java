package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Producto;

public interface OperacionCRUD {

    String crear(Producto producto);
    Producto leer(int indice);
    Producto[] leertodo();
    String modificar(int indice, Producto producto);
    String eliminar(int indice);
}