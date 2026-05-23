package co.edu.poli.agro.vista;

import co.edu.poli.agro.modelo.Abono;
import co.edu.poli.agro.modelo.Fertilizante;
import co.edu.poli.agro.modelo.Pesticida;
import co.edu.poli.agro.modelo.Producto;
import co.edu.poli.agro.servicios.ImplementacionOperacionCRUD;

import java.util.Scanner;

/**
 * Clase principal de la interfaz por consola del sistema de gestión de productos agrícolas.
 * <p>
 * Presenta un menú interactivo en la terminal que permite realizar las operaciones
 * CRUD sobre los productos: registrar, consultar, modificar y eliminar, así como
 * guardar y cargar datos desde el archivo {@code productos.txt}.
 * Sirve como punto de entrada alternativo cuando no se usa la interfaz gráfica JavaFX.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see ImplementacionOperacionCRUD
 */
public class Principal {

    /** Objeto Scanner para leer la entrada del usuario desde la consola. */
    static Scanner sc = new Scanner(System.in);

    /** Instancia de la implementación CRUD y de archivo para gestionar los productos. */
    static ImplementacionOperacionCRUD impl = new ImplementacionOperacionCRUD();

    /**
     * Método principal que inicia la aplicación de consola.
     * <p>
     * Carga automáticamente los datos del archivo {@code productos.txt} si existe,
     * y presenta el menú principal en bucle hasta que el usuario elija salir (opción 0).
     * Maneja excepciones de entrada para evitar cierres inesperados.
     * </p>
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {

        java.io.File archivo = new java.io.File("productos.txt");
        if (archivo.exists()) {
            impl.desereralizar();
            System.out.println("[Sistema] Datos cargados desde productos.txt (" +
                               impl.getContador() + " productos).");
        }

        int opcion = -1;

        do {
            try {
                System.out.println("\n╔══════════════════════════════════════╗");
                System.out.println("║   REGISTRO DE PRODUCTOS AGRICOLAS   ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║  1. Registrar producto               ║");
                System.out.println("║  2. Ver producto por indice          ║");
                System.out.println("║  3. Ver todos los productos          ║");
                System.out.println("║  4. Modificar producto               ║");
                System.out.println("║  5. Eliminar producto                ║");
                System.out.println("║  6. Serializar datos                 ║");
                System.out.println("║  7. Deserializar datos               ║");
                System.out.println("║  0. Salir                            ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.print("Seleccione una opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                if (opcion < 0 || opcion > 7) {
                    System.out.println("Esa opcion no es valida, por favor escoja una opcion valida (0 - 7).");
                    opcion = -1;
                    continue;
                }

                switch (opcion) {
                    case 1: registrar();    break;
                    case 2: verUno();       break;
                    case 3: verTodos();     break;
                    case 4: modificar();    break;
                    case 5: eliminar();     break;
                    case 6: serializar();   break;
                    case 7: deserializar(); break;
                    case 0: System.out.println("Hasta luego."); break;
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero. Por favor escoja una opcion valida.");
                sc.nextLine();
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Ocurrio un error inesperado: " + e.getMessage() + ". El programa continuara.");
                sc.nextLine();
                opcion = -1;
            }

        } while (opcion != 0);
    }

    /**
     * Solicita al usuario los datos para registrar un nuevo producto agrícola.
     * <p>
     * Pide el tipo (Fertilizante, Pesticida o Abono) y luego los campos comunes
     * y específicos del tipo elegido. Valida que la cantidad y los días (para Abono)
     * sean valores numéricos positivos.
     * </p>
     */
    static void registrar() {
        int tipo = -1;

        do {
            try {
                System.out.println("\n-- Tipo de producto --");
                System.out.println("1. Fertilizante");
                System.out.println("2. Pesticida");
                System.out.println("3. Abono");
                System.out.print("Seleccione: ");
                tipo = sc.nextInt();
                sc.nextLine();

                if (tipo < 1 || tipo > 3) {
                    System.out.println("Esa opcion no es valida, por favor escoja una opcion valida (1 - 3).");
                    tipo = -1;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero. Por favor escoja una opcion valida (1 - 3).");
                sc.nextLine();
                tipo = -1;
            }
        } while (tipo < 1 || tipo > 3);

        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            double cantidad = -1;
            do {
                try {
                    System.out.print("Cantidad disponible: ");
                    cantidad = sc.nextDouble();
                    sc.nextLine();
                    if (cantidad < 0) {
                        System.out.println("La cantidad no puede ser negativa. Intente de nuevo.");
                        cantidad = -1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Error: debe ingresar un numero valido para la cantidad.");
                    sc.nextLine();
                    cantidad = -1;
                }
            } while (cantidad < 0);

            System.out.print("Unidad de medida (kg, L, etc.): ");
            String unidad = sc.nextLine();
            System.out.print("Fecha de aplicacion (dd/mm/aaaa): ");
            String fecha = sc.nextLine();

            Producto p = null;

            switch (tipo) {
                case 1:
                    System.out.print("Tipo de formula: ");
                    String formula = sc.nextLine();
                    System.out.print("Observaciones: ");
                    String obsF = sc.nextLine();
                    p = new Fertilizante(0, nombre, cantidad, unidad, fecha, formula, obsF);
                    break;
                case 2:
                    System.out.print("Plaga objetivo: ");
                    String plaga = sc.nextLine();
                    System.out.print("Toxicidad: ");
                    String tox = sc.nextLine();
                    System.out.print("Observaciones: ");
                    String obsP = sc.nextLine();
                    p = new Pesticida(0, nombre, cantidad, unidad, fecha, plaga, tox, obsP);
                    break;
                case 3:
                    System.out.print("Origen: ");
                    String origen = sc.nextLine();
                    int dias = -1;
                    do {
                        try {
                            System.out.print("Tiempo de descomposicion (dias): ");
                            dias = sc.nextInt();
                            sc.nextLine();
                            if (dias < 0) {
                                System.out.println("Los dias no pueden ser negativos. Intente de nuevo.");
                                dias = -1;
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Error: debe ingresar un numero entero para los dias.");
                            sc.nextLine();
                            dias = -1;
                        }
                    } while (dias < 0);
                    System.out.print("Observaciones: ");
                    String obsA = sc.nextLine();
                    p = new Abono(0, nombre, cantidad, unidad, fecha, origen, dias, obsA);
                    break;
            }

            System.out.println(impl.crear(p));
            System.out.println("Capacidad actual del arreglo: " + impl.getCapacidad());

        } catch (Exception e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
            sc.nextLine();
        }
    }

    /**
     * Solicita un índice y muestra la información del producto correspondiente.
     * Valida que el índice sea un entero dentro del rango de registros actuales.
     * Si no hay productos registrados, informa al usuario y retorna sin solicitar entrada.
     */
    static void verUno() {
        if (impl.getContador() == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        int idx = -1;
        do {
            try {
                System.out.print("Ingrese el indice (0 - " + (impl.getContador() - 1) + "): ");
                idx = sc.nextInt();
                sc.nextLine();
                if (idx < 0 || idx >= impl.getContador()) {
                    System.out.println("Esa opcion no es valida, por favor escoja un indice valido (0 - " + (impl.getContador() - 1) + ").");
                    idx = -1;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero como indice.");
                sc.nextLine();
                idx = -1;
            }
        } while (idx < 0);

        try {
            Producto p = impl.leer(idx);
            if (p != null) {
                System.out.println("\n" + p.toString());
            }
        } catch (Exception e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }
    }

    /**
     * Muestra en consola la lista completa de todos los productos registrados.
     * Cada producto se presenta precedido de su índice en el arreglo interno.
     * Si no hay productos, informa al usuario y retorna sin imprimir.
     */
    static void verTodos() {
        try {
            Producto[] lista = impl.leertodo();
            if (lista.length == 0) {
                System.out.println("No hay productos registrados.");
                return;
            }
            System.out.println("\n--- Lista de productos (" + lista.length + ") ---");
            for (int i = 0; i < lista.length; i++) {
                System.out.println("[" + i + "] " + lista[i].toString());
            }
        } catch (Exception e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
    }

    /**
     * Permite modificar un producto existente seleccionado por índice.
     * <p>
     * Muestra la lista antes de solicitar el índice. Para cada campo,
     * si el usuario deja la entrada vacía se conserva el valor actual.
     * Valida cantidad y días de descomposición (Abono) como valores positivos.
     * </p>
     */
    static void modificar() {
        if (impl.getContador() == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        verTodos();

        int idx = -1;
        do {
            try {
                System.out.print("Ingrese el indice a modificar: ");
                idx = sc.nextInt();
                sc.nextLine();
                if (idx < 0 || idx >= impl.getContador()) {
                    System.out.println("Esa opcion no es valida, por favor escoja un indice valido (0 - " + (impl.getContador() - 1) + ").");
                    idx = -1;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero como indice.");
                sc.nextLine();
                idx = -1;
            }
        } while (idx < 0);

        try {
            Producto actual = impl.leer(idx);
            if (actual == null) return;

            System.out.println("Modificando: " + actual.toString());
            System.out.println("(Deje en blanco para conservar el valor actual)");

            System.out.print("Nuevo nombre [" + actual.getNombre() + "]: ");
            String nombre = sc.nextLine();
            if (nombre.isEmpty()) nombre = actual.getNombre();

            double cantidad = -1;
            do {
                try {
                    System.out.print("Nueva cantidad [" + actual.getCantidadDisponible() + "]: ");
                    String cantStr = sc.nextLine();
                    if (cantStr.isEmpty()) {
                        cantidad = actual.getCantidadDisponible();
                    } else {
                        cantidad = Double.parseDouble(cantStr);
                        if (cantidad < 0) {
                            System.out.println("La cantidad no puede ser negativa. Intente de nuevo.");
                            cantidad = -1;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: debe ingresar un numero valido para la cantidad.");
                    cantidad = -1;
                }
            } while (cantidad < 0);

            System.out.print("Nueva unidad [" + actual.getUnidadmedida() + "]: ");
            String unidad = sc.nextLine();
            if (unidad.isEmpty()) unidad = actual.getUnidadmedida();

            System.out.print("Nueva fecha [" + actual.getFechaAplicacion() + "]: ");
            String fecha = sc.nextLine();
            if (fecha.isEmpty()) fecha = actual.getFechaAplicacion();

            Producto nuevo = null;

            if (actual instanceof Fertilizante) {
                Fertilizante f = (Fertilizante) actual;
                System.out.print("Nueva formula [" + f.getTipoFormula() + "]: ");
                String formula = sc.nextLine();
                if (formula.isEmpty()) formula = f.getTipoFormula();
                System.out.print("Nuevas observaciones [" + f.getObservaciones() + "]: ");
                String obs = sc.nextLine();
                if (obs.isEmpty()) obs = f.getObservaciones();
                nuevo = new Fertilizante(0, nombre, cantidad, unidad, fecha, formula, obs);

            } else if (actual instanceof Pesticida) {
                Pesticida p = (Pesticida) actual;
                System.out.print("Nueva plaga [" + p.getPlagaObjetivo() + "]: ");
                String plaga = sc.nextLine();
                if (plaga.isEmpty()) plaga = p.getPlagaObjetivo();
                System.out.print("Nueva toxicidad [" + p.getToxicidad() + "]: ");
                String tox = sc.nextLine();
                if (tox.isEmpty()) tox = p.getToxicidad();
                System.out.print("Nuevas observaciones [" + p.getObservaciones() + "]: ");
                String obs = sc.nextLine();
                if (obs.isEmpty()) obs = p.getObservaciones();
                nuevo = new Pesticida(0, nombre, cantidad, unidad, fecha, plaga, tox, obs);

            } else if (actual instanceof Abono) {
                Abono a = (Abono) actual;
                System.out.print("Nuevo origen [" + a.getOrigen() + "]: ");
                String origen = sc.nextLine();
                if (origen.isEmpty()) origen = a.getOrigen();

                int dias = -1;
                do {
                    try {
                        System.out.print("Nuevo tiempo descomp. [" + a.getTiempoDescomposicion() + "]: ");
                        String diasStr = sc.nextLine();
                        if (diasStr.isEmpty()) {
                            dias = a.getTiempoDescomposicion();
                        } else {
                            dias = Integer.parseInt(diasStr);
                            if (dias < 0) {
                                System.out.println("Los dias no pueden ser negativos. Intente de nuevo.");
                                dias = -1;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: debe ingresar un numero entero para los dias.");
                        dias = -1;
                    }
                } while (dias < 0);

                System.out.print("Nuevas observaciones [" + a.getObservaciones() + "]: ");
                String obs = sc.nextLine();
                if (obs.isEmpty()) obs = a.getObservaciones();
                nuevo = new Abono(0, nombre, cantidad, unidad, fecha, origen, dias, obs);
            }

            System.out.println(impl.modificar(idx, nuevo));

        } catch (Exception e) {
            System.out.println("Error al modificar producto: " + e.getMessage());
            sc.nextLine();
        }
    }

    /**
     * Permite eliminar un producto existente seleccionado por índice.
     * Muestra la lista antes de pedir el índice y valida que sea un entero
     * dentro del rango de registros actuales.
     * Si no hay productos, informa al usuario y retorna sin solicitar entrada.
     */
    static void eliminar() {
        if (impl.getContador() == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        verTodos();

        int idx = -1;
        do {
            try {
                System.out.print("Ingrese el indice a eliminar: ");
                idx = sc.nextInt();
                sc.nextLine();
                if (idx < 0 || idx >= impl.getContador()) {
                    System.out.println("Esa opcion no es valida, por favor escoja un indice valido (0 - " + (impl.getContador() - 1) + ").");
                    idx = -1;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero como indice.");
                sc.nextLine();
                idx = -1;
            }
        } while (idx < 0);

        try {
            System.out.println(impl.eliminar(idx));
        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    /**
     * Guarda todos los productos en el archivo {@code productos.txt}.
     * Muestra un mensaje de error si ocurre una excepción durante el guardado.
     */
    static void serializar() {
        try {
            System.out.println(impl.serializar());
        } catch (Exception e) {
            System.out.println("Error al serializar: " + e.getMessage());
        }
    }

    /**
     * Carga los productos desde el archivo {@code productos.txt} y los muestra en consola.
     * Si el archivo no existe o está vacío, informa al usuario y retorna sin modificar el estado.
     */
    static void deserializar() {
        try {
            Producto[] lista = impl.desereralizar();
            if (lista.length == 0) {
                System.out.println("No se encontro el archivo productos.txt o esta vacio.");
                return;
            }
            System.out.println("\n=== DATOS CARGADOS DESDE ARCHIVO (" + lista.length + " productos) ===");
            for (int i = 0; i < lista.length; i++) {
                System.out.println("[" + i + "] " + lista[i].toString());
            }
        } catch (Exception e) {
            System.out.println("Error al deserializar: " + e.getMessage());
        }
    }
}