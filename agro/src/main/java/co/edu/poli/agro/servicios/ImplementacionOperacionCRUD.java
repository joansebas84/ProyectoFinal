package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Abono;
import co.edu.poli.agro.modelo.Fertilizante;
import co.edu.poli.agro.modelo.Pesticida;
import co.edu.poli.agro.modelo.Producto;
import co.edu.poli.agro.servicios.OperacionArchivo;
import co.edu.poli.agro.servicios.OperacionCRUD;

/**
 * Clase que implementa las operaciones CRUD y de persistencia en archivo
 * para la gestión de productos agrícolas.
 * <p>
 * Los productos se almacenan en un arreglo dinámico que se duplica automáticamente
 * cuando alcanza su capacidad máxima. La persistencia se realiza en el archivo
 * {@code productos.txt} con un formato delimitado por el carácter {@code |}.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see OperacionCRUD
 * @see OperacionArchivo
 */
public class ImplementacionOperacionCRUD implements OperacionCRUD, OperacionArchivo {

    /** Arreglo dinámico que almacena los productos registrados en el sistema. */
    private Producto[] ArreglooObjetos;

    /** Número de productos actualmente registrados en el arreglo. */
    private int contador;

    /**
     * Constructor que inicializa el arreglo con capacidad inicial de 2
     * y el contador en cero.
     */
    public ImplementacionOperacionCRUD() {
        this.ArreglooObjetos = new Producto[2];
        this.contador = 0;
    }

    /**
     * Duplica la capacidad del arreglo interno cuando este se encuentra lleno.
     * <p>
     * Crea un nuevo arreglo con el doble de capacidad, copia los elementos
     * existentes y reemplaza el arreglo original.
     * </p>
     */
    private void duplicarArreglo() {
        Producto[] nuevoArreglo = new Producto[ArreglooObjetos.length * 2];
        for (int i = 0; i < ArreglooObjetos.length; i++) {
            nuevoArreglo[i] = ArreglooObjetos[i];
        }
        ArreglooObjetos = nuevoArreglo;
        System.out.println("[Sistema] Arreglo ampliado a " + ArreglooObjetos.length + " posiciones.");
    }

    // ─── OperacionCRUD ───────────────────────────────────────────────────────

    /**
     * Registra un nuevo producto en el sistema.
     * <p>
     * Si el arreglo está lleno, se duplica su capacidad antes de insertar.
     * Asigna automáticamente un ID secuencial al producto.
     * </p>
     *
     * @param producto el objeto {@link Producto} a registrar
     * @return mensaje con el resultado de la operación e ID asignado
     */
    @Override
    public String crear(Producto producto) {
        if (contador == ArreglooObjetos.length) {
            duplicarArreglo();
        }
        producto.setId(contador + 1);
        ArreglooObjetos[contador] = producto;
        contador++;
        return "Producto registrado correctamente con ID: " + producto.getId();
    }

    /**
     * Recupera el producto ubicado en la posición indicada del arreglo interno.
     *
     * @param indice la posición del producto en el arreglo (base 0)
     * @return el {@link Producto} en la posición indicada, o {@code null} si el índice es inválido
     */
    @Override
    public Producto leer(int indice) {
        if (indice < 0 || indice >= contador) {
            System.out.println("Indice fuera de rango.");
            return null;
        }
        return ArreglooObjetos[indice];
    }

    /**
     * Recupera todos los productos actualmente registrados en el sistema.
     *
     * @return arreglo con todos los productos registrados; arreglo vacío si no hay ninguno
     */
    @Override
    public Producto[] leertodo() {
        Producto[] resultado = new Producto[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = ArreglooObjetos[i];
        }
        return resultado;
    }

    /**
     * Modifica el producto ubicado en la posición indicada, conservando su ID original.
     *
     * @param indice   la posición del producto a modificar (base 0)
     * @param producto el nuevo objeto {@link Producto} con los datos actualizados
     * @return mensaje indicando el resultado de la operación
     */
    @Override
    public String modificar(int indice, Producto producto) {
        if (indice < 0 || indice >= contador) {
            return "Indice fuera de rango. No se pudo modificar.";
        }
        producto.setId(ArreglooObjetos[indice].getId());
        ArreglooObjetos[indice] = producto;
        return "Producto en posicion " + indice + " modificado correctamente.";
    }

    /**
     * Elimina el producto ubicado en la posición indicada y desplaza los elementos
     * posteriores hacia atrás para mantener la continuidad del arreglo.
     *
     * @param indice la posición del producto a eliminar (base 0)
     * @return mensaje indicando el resultado de la operación
     */
    @Override
    public String eliminar(int indice) {
        if (indice < 0 || indice >= contador) {
            return "Indice fuera de rango. No se pudo eliminar.";
        }
        for (int i = indice; i < contador - 1; i++) {
            ArreglooObjetos[i] = ArreglooObjetos[i + 1];
        }
        ArreglooObjetos[contador - 1] = null;
        contador--;
        return "Producto eliminado correctamente.";
    }

    // ─── OperacionArchivo ────────────────────────────────────────────────────

    /** Nombre del archivo de texto donde se persisten los datos. */
    private static final String ARCHIVO = "productos.txt";

    /**
     * Serializa todos los productos registrados y los guarda en el archivo
     * {@code productos.txt}, sobreescribiendo cualquier contenido previo.
     * <p>
     * Formato por línea: {@code TIPO|id|nombre|cantidad|unidad|fecha|[campos específicos]}
     * </p>
     *
     * @return mensaje indicando si el guardado fue exitoso o si ocurrió un error
     */
    @Override
    public String serializar() {
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(
                    new java.io.FileWriter(ARCHIVO, false));
            for (int i = 0; i < contador; i++) {
                Producto p = ArreglooObjetos[i];
                if (p instanceof Fertilizante) {
                    Fertilizante f = (Fertilizante) p;
                    pw.println("FERTILIZANTE|" + f.getId() + "|" + f.getNombre() + "|" +
                               f.getCantidadDisponible() + "|" + f.getUnidadmedida() + "|" +
                               f.getFechaAplicacion() + "|" + f.getTipoFormula() + "|" +
                               f.getObservaciones());
                } else if (p instanceof Pesticida) {
                    Pesticida ps = (Pesticida) p;
                    pw.println("PESTICIDA|" + ps.getId() + "|" + ps.getNombre() + "|" +
                               ps.getCantidadDisponible() + "|" + ps.getUnidadmedida() + "|" +
                               ps.getFechaAplicacion() + "|" + ps.getPlagaObjetivo() + "|" +
                               ps.getToxicidad() + "|" + ps.getObservaciones());
                } else if (p instanceof Abono) {
                    Abono a = (Abono) p;
                    pw.println("ABONO|" + a.getId() + "|" + a.getNombre() + "|" +
                               a.getCantidadDisponible() + "|" + a.getUnidadmedida() + "|" +
                               a.getFechaAplicacion() + "|" + a.getOrigen() + "|" +
                               a.getTiempoDescomposicion() + "|" + a.getObservaciones());
                }
            }
            pw.close();
            return "Datos guardados correctamente en " + ARCHIVO;
        } catch (java.io.IOException e) {
            return "Error al guardar: " + e.getMessage();
        }
    }

    /**
     * Deserializa los productos almacenados en el archivo {@code productos.txt}
     * y los carga en el arreglo interno, reemplazando los datos actuales.
     * <p>
     * Las líneas vacías son ignoradas. Si el archivo no existe, retorna un arreglo vacío.
     * </p>
     *
     * @return arreglo con los productos cargados; arreglo vacío si el archivo no existe o hay error
     */
    @Override
    public Producto[] desereralizar() {
        java.io.File archivo = new java.io.File(ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("No se encontro el archivo " + ARCHIVO);
            return new Producto[0];
        }
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo));
            int lineas = 0;
            while (br.readLine() != null) lineas++;
            br.close();

            ArreglooObjetos = new Producto[Math.max(lineas, 2)];
            contador = 0;

            br = new java.io.BufferedReader(new java.io.FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split("\\|");
                String tipo     = partes[0];
                int id          = Integer.parseInt(partes[1]);
                String nombre   = partes[2];
                double cantidad = Double.parseDouble(partes[3]);
                String unidad   = partes[4];
                String fecha    = partes[5];

                Producto p = null;
                if (tipo.equals("FERTILIZANTE")) {
                    p = new Fertilizante(id, nombre, cantidad, unidad, fecha,
                                        partes[6], partes[7]);
                } else if (tipo.equals("PESTICIDA")) {
                    p = new Pesticida(id, nombre, cantidad, unidad, fecha,
                                     partes[6], partes[7], partes[8]);
                } else if (tipo.equals("ABONO")) {
                    p = new Abono(id, nombre, cantidad, unidad, fecha,
                                 partes[6], Integer.parseInt(partes[7]), partes[8]);
                }
                if (p != null) {
                    ArreglooObjetos[contador] = p;
                    contador++;
                }
            }
            br.close();
            return leertodo();
        } catch (java.io.IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
            return new Producto[0];
        }
    }

    // ─── Getters utilitarios ─────────────────────────────────────────────────

    /**
     * Retorna el número de productos actualmente registrados en el sistema.
     *
     * @return el conteo de productos registrados
     */
    public int getContador() { return contador; }

    /**
     * Retorna la capacidad actual del arreglo interno de productos.
     *
     * @return la capacidad máxima actual del arreglo
     */
    public int getCapacidad() { return ArreglooObjetos.length; }
}