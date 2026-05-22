package co.edu.poli.agro.servicios;

import co.edu.poli.agro.modelo.Abono;
import co.edu.poli.agro.modelo.Fertilizante;
import co.edu.poli.agro.modelo.Pesticida;
import co.edu.poli.agro.modelo.Producto;
import co.edu.poli.agro.servicios.OperacionArchivo;
import co.edu.poli.agro.servicios.OperacionCRUD;

public class ImplementacionOperacionCRUD implements OperacionCRUD, OperacionArchivo {

    private Producto[] ArreglooObjetos;
    private int contador;

    public ImplementacionOperacionCRUD() {
        this.ArreglooObjetos = new Producto[2];
        this.contador = 0;
    }

    // ─── Duplica el arreglo cuando se llena ──────────────────────────────────
    private void duplicarArreglo() {
        Producto[] nuevoArreglo = new Producto[ArreglooObjetos.length * 2];
        for (int i = 0; i < ArreglooObjetos.length; i++) {
            nuevoArreglo[i] = ArreglooObjetos[i];
        }
        ArreglooObjetos = nuevoArreglo;
        System.out.println("[Sistema] Arreglo ampliado a " + ArreglooObjetos.length + " posiciones.");
    }

    // ─── OperacionCRUD ───────────────────────────────────────────────────────

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

    @Override
    public Producto leer(int indice) {
        if (indice < 0 || indice >= contador) {
            System.out.println("Indice fuera de rango.");
            return null;
        }
        return ArreglooObjetos[indice];
    }

    @Override
    public Producto[] leertodo() {
        Producto[] resultado = new Producto[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = ArreglooObjetos[i];
        }
        return resultado;
    }

    @Override
    public String modificar(int indice, Producto producto) {
        if (indice < 0 || indice >= contador) {
            return "Indice fuera de rango. No se pudo modificar.";
        }
        producto.setId(ArreglooObjetos[indice].getId());
        ArreglooObjetos[indice] = producto;
        return "Producto en posicion " + indice + " modificado correctamente.";
    }

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

    private static final String ARCHIVO = "productos.txt";

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

    public int getContador() { return contador; }
    public int getCapacidad() { return ArreglooObjetos.length; }
}