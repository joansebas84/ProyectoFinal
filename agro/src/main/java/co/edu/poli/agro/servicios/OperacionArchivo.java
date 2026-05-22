package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Producto;

public interface OperacionArchivo {

    String serializar();
    Producto[] desereralizar();
}