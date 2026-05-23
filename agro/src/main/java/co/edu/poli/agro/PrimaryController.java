package co.edu.poli.agro;

import co.edu.poli.agro.modelo.Abono;
import co.edu.poli.agro.modelo.Fertilizante;
import co.edu.poli.agro.modelo.Pesticida;
import co.edu.poli.agro.modelo.Producto;
import co.edu.poli.agro.servicios.ImplementacionOperacionCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controlador principal de la interfaz gráfica de la aplicación agrícola.
 * <p>
 * Gestiona todos los eventos y la lógica de presentación de la ventana principal,
 * incluyendo el formulario de registro, la tabla de productos y las operaciones
 * CRUD realizadas a través de {@link ImplementacionOperacionCRUD}.
 * Esta clase está asociada al archivo FXML {@code primary.fxml} y es gestionada
 * automáticamente por JavaFX.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see ImplementacionOperacionCRUD
 * @see Producto
 */
public class PrimaryController {

    // ── Campos del formulario ────────────────────────────────────────────────

    /** Campo de texto para ingresar el nombre del producto. */
    @FXML private TextField txtNombre;

    /** ComboBox para seleccionar el tipo de producto (Fertilizante, Pesticida, Abono). */
    @FXML private ComboBox<String> cmbTipo;

    /** Campo de texto para ingresar la cantidad disponible del producto. */
    @FXML private TextField txtCantidad;

    /** Campo de texto para ingresar la unidad de medida del producto. */
    @FXML private TextField txtUnidad;

    /** Campo de texto para ingresar la fecha de aplicación del producto. */
    @FXML private TextField txtFecha;

    /** Área de texto para ingresar observaciones adicionales del producto. */
    @FXML private TextArea  txtObservaciones;

    /** Componente de imagen para mostrar el banner de la aplicación. */
    @FXML private ImageView bannerImage;

    /** Panel exclusivo para los campos del tipo Fertilizante. */
    @FXML private javafx.scene.layout.VBox panelFertilizante;

    /** Campo de texto para ingresar la fórmula química del fertilizante. */
    @FXML private TextField txtFormula;

    /** Panel exclusivo para los campos del tipo Pesticida. */
    @FXML private javafx.scene.layout.VBox panelPesticida;

    /** Campo de texto para ingresar la plaga objetivo del pesticida. */
    @FXML private TextField txtPlaga;

    /** Campo de texto para ingresar la toxicidad del pesticida. */
    @FXML private TextField txtToxicidad;

    /** Panel exclusivo para los campos del tipo Abono. */
    @FXML private javafx.scene.layout.VBox panelAbono;

    /** Campo de texto para ingresar el origen del abono. */
    @FXML private TextField txtOrigen;

    /** Campo de texto para ingresar el tiempo de descomposición del abono en días. */
    @FXML private TextField txtDias;

    // ── Tabla ────────────────────────────────────────────────────────────────

    /** Tabla que muestra la lista de productos registrados. */
    @FXML private TableView<Producto>       tablaProductos;

    /** Columna de la tabla que muestra el nombre del producto. */
    @FXML private TableColumn<Producto, String> colNombre;

    /** Columna de la tabla que muestra el tipo de producto. */
    @FXML private TableColumn<Producto, String> colTipo;

    /** Columna de la tabla que muestra la cantidad y unidad del producto. */
    @FXML private TableColumn<Producto, String> colCantidad;

    /** Columna de la tabla que muestra la fecha de aplicación. */
    @FXML private TableColumn<Producto, String> colFecha;

    /** Columna de la tabla que muestra las observaciones del producto. */
    @FXML private TableColumn<Producto, String> colObs;

    // ── Footer ───────────────────────────────────────────────────────────────

    /** Campo de texto para ingresar el filtro de búsqueda por nombre. */
    @FXML private TextField txtBuscar;

    /** Etiqueta que muestra la capacidad del arreglo y el número de registros. */
    @FXML private Label     lblCapacidad;

    // ── Lógica ───────────────────────────────────────────────────────────────

    /** Instancia de la clase de implementación CRUD y de archivo. */
    private final ImplementacionOperacionCRUD impl = new ImplementacionOperacionCRUD();

    /** Lista observable enlazada a la tabla para reflejar cambios en tiempo real. */
    private ObservableList<Producto> listaObservable = FXCollections.observableArrayList();

    /** Índice del producto actualmente seleccionado en la tabla (-1 si ninguno está seleccionado). */
    private int indiceSeleccionado = -1;

    // ════════════════════════════════════════════════════════════════════════

    /**
     * Método de inicialización invocado automáticamente por JavaFX tras cargar el FXML.
     * <p>
     * Configura el banner, el ComboBox de tipos, las columnas de la tabla
     * y carga los datos del archivo {@code productos.txt} si este existe.
     * </p>
     */
    @FXML
    public void initialize() {
        // Cargar imagen del banner
        try {
            Image imagenBanner = new Image(getClass().getResourceAsStream("/imagenes/banner-agricola.jpg"));
            bannerImage.setImage(imagenBanner);
        } catch (Exception e) {
            // Si no encuentra la imagen, el StackPane ya tiene un degradado de fondo
            System.out.println("Imagen de banner no encontrada, usando fondo degradado");
        }
        
        // ComboBox tipos
        cmbTipo.setItems(FXCollections.observableArrayList("Fertilizante", "Pesticida", "Abono"));
        cmbTipo.getSelectionModel().selectFirst();
        mostrarPanelTipo("Fertilizante");

        cmbTipo.setOnAction(e -> mostrarPanelTipo(cmbTipo.getValue()));

        // Columnas de la tabla — usamos wrapper para mostrar texto enriquecido
        colNombre.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colTipo.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(getTipo(data.getValue())));
        colCantidad.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCantidadDisponible() + " " + data.getValue().getUnidadmedida()));
        colFecha.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().getFechaAplicacion()));
        colObs.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(getObservaciones(data.getValue())));

        tablaProductos.setItems(listaObservable);

        // Cargar archivo si existe
        java.io.File f = new java.io.File("productos.txt");
        if (f.exists()) {
            impl.desereralizar();
            refrescarTabla();
            mostrarInfo("Datos cargados desde productos.txt (" + impl.getContador() + " productos).");
        }

        actualizarCapacidad();
    }

    // ════════ Mostrar/ocultar paneles dinámicos ═══════════════════════════════

    /**
     * Muestra u oculta los paneles de campos específicos según el tipo de producto
     * seleccionado en el ComboBox.
     *
     * @param tipo el tipo de producto seleccionado ("Fertilizante", "Pesticida" o "Abono")
     */
    private void mostrarPanelTipo(String tipo) {
        panelFertilizante.setVisible(false); panelFertilizante.setManaged(false);
        panelPesticida.setVisible(false);    panelPesticida.setManaged(false);
        panelAbono.setVisible(false);        panelAbono.setManaged(false);

        if (tipo == null) return;
        switch (tipo) {
            case "Fertilizante":
                panelFertilizante.setVisible(true); panelFertilizante.setManaged(true); break;
            case "Pesticida":
                panelPesticida.setVisible(true);    panelPesticida.setManaged(true);    break;
            case "Abono":
                panelAbono.setVisible(true);        panelAbono.setManaged(true);        break;
        }
    }

    // ════════ CRUD ════════════════════════════════════════════════════════════

    /**
     * Crea un nuevo producto a partir de los datos del formulario y lo registra en el sistema.
     * Limpia el formulario y actualiza la tabla tras un registro exitoso.
     * Muestra un mensaje de error si los datos son inválidos o incompletos.
     */
    @FXML
    private void agregarProducto() {
        try {
            Producto p = construirProductoDesdeFormulario();
            if (p == null) return;
            String resultado = impl.crear(p);
            refrescarTabla();
            actualizarCapacidad();
            limpiarCampos();
            mostrarInfo(resultado);
        } catch (Exception e) {
            mostrarError("Error al agregar: " + e.getMessage());
        }
    }

    /**
     * Modifica el producto seleccionado en la tabla con los datos del formulario,
     * conservando su ID original.
     * Muestra un mensaje de error si no hay producto seleccionado o los datos son inválidos.
     */
    @FXML
    private void editarProducto() {
        if (indiceSeleccionado < 0) {
            mostrarError("Seleccione un producto de la tabla para editar.");
            return;
        }
        try {
            Producto p = construirProductoDesdeFormulario();
            if (p == null) return;
            String resultado = impl.modificar(indiceSeleccionado, p);
            refrescarTabla();
            limpiarCampos();
            mostrarInfo(resultado);
        } catch (Exception e) {
            mostrarError("Error al editar: " + e.getMessage());
        }
    }

    /**
     * Elimina el producto seleccionado en la tabla tras solicitar confirmación al usuario.
     * Muestra un mensaje de error si no hay producto seleccionado.
     */
    @FXML
    private void eliminarProducto() {
        if (indiceSeleccionado < 0) {
            mostrarError("Seleccione un producto de la tabla para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Está seguro que desea eliminar este producto?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                String resultado = impl.eliminar(indiceSeleccionado);
                refrescarTabla();
                limpiarCampos();
                mostrarInfo(resultado);
            }
        });
    }

    /**
     * Limpia todos los campos del formulario y restablece el estado inicial
     * del ComboBox y la selección de la tabla.
     */
    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtCantidad.clear();
        txtUnidad.clear();
        txtFecha.clear();
        txtObservaciones.clear();
        txtFormula.clear();
        txtPlaga.clear();
        txtToxicidad.clear();
        txtOrigen.clear();
        txtDias.clear();
        cmbTipo.getSelectionModel().selectFirst();
        mostrarPanelTipo("Fertilizante");
        indiceSeleccionado = -1;
        tablaProductos.getSelectionModel().clearSelection();
    }

    // ════════ Selección en tabla ══════════════════════════════════════════════

    /**
     * Captura el producto seleccionado en la tabla y rellena el formulario con sus datos.
     * <p>
     * Determina el índice real en el arreglo interno comparando por ID,
     * garantizando el correcto funcionamiento incluso con búsqueda activa.
     * </p>
     */
    @FXML
    private void seleccionarProducto() {
        int idx = tablaProductos.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;

        // El índice visible puede diferir si hay búsqueda activa — buscamos por ID
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        // Encontrar índice real en el arreglo interno
        Producto[] todos = impl.leertodo();
        for (int i = 0; i < todos.length; i++) {
            if (todos[i].getId() == seleccionado.getId()) {
                indiceSeleccionado = i;
                break;
            }
        }

        // Rellenar formulario
        txtNombre.setText(seleccionado.getNombre());
        txtCantidad.setText(String.valueOf(seleccionado.getCantidadDisponible()));
        txtUnidad.setText(seleccionado.getUnidadmedida());
        txtFecha.setText(seleccionado.getFechaAplicacion());

        if (seleccionado instanceof Fertilizante) {
            Fertilizante f = (Fertilizante) seleccionado;
            cmbTipo.setValue("Fertilizante");
            mostrarPanelTipo("Fertilizante");
            txtFormula.setText(f.getTipoFormula());
            txtObservaciones.setText(f.getObservaciones());

        } else if (seleccionado instanceof Pesticida) {
            Pesticida p = (Pesticida) seleccionado;
            cmbTipo.setValue("Pesticida");
            mostrarPanelTipo("Pesticida");
            txtPlaga.setText(p.getPlagaObjetivo());
            txtToxicidad.setText(p.getToxicidad());
            txtObservaciones.setText(p.getObservaciones());

        } else if (seleccionado instanceof Abono) {
            Abono a = (Abono) seleccionado;
            cmbTipo.setValue("Abono");
            mostrarPanelTipo("Abono");
            txtOrigen.setText(a.getOrigen());
            txtDias.setText(String.valueOf(a.getTiempoDescomposicion()));
            txtObservaciones.setText(a.getObservaciones());
        }
    }

    // ════════ Buscar ══════════════════════════════════════════════════════════

    /**
     * Filtra la tabla de productos por nombre según el texto del campo de búsqueda.
     * Si el campo está vacío, muestra todos los productos. La búsqueda no distingue
     * entre mayúsculas y minúsculas.
     */
    @FXML
    private void buscarProducto() {
        String filtro = txtBuscar.getText().toLowerCase().trim();
        if (filtro.isEmpty()) {
            refrescarTabla();
            return;
        }
        Producto[] todos = impl.leertodo();
        ObservableList<Producto> filtrados = FXCollections.observableArrayList();
        for (Producto p : todos) {
            if (p.getNombre().toLowerCase().contains(filtro)) {
                filtrados.add(p);
            }
        }
        tablaProductos.setItems(filtrados);
    }

    // ════════ Serializar / Deserializar ═══════════════════════════════════════

    /**
     * Guarda todos los productos en el archivo {@code productos.txt}.
     * Muestra un mensaje de error si ocurre una excepción durante el guardado.
     */
    @FXML
    private void guardarDatos() {
        try {
            String resultado = impl.serializar();
            mostrarInfo(resultado);
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Carga los productos desde el archivo {@code productos.txt}.
     * Muestra un mensaje de error si el archivo no existe, está vacío
     * o ocurre una excepción durante la lectura.
     */
    @FXML
    private void cargarDatos() {
        try {
            Producto[] lista = impl.desereralizar();
            if (lista.length == 0) {
                mostrarError("No se encontró el archivo productos.txt o está vacío.");
                return;
            }
            refrescarTabla();
            actualizarCapacidad();
            mostrarInfo("Datos cargados correctamente (" + lista.length + " productos).");
        } catch (Exception e) {
            mostrarError("Error al cargar: " + e.getMessage());
        }
    }

    // ════════ Salir ═══════════════════════════════════════════════════════════

    /**
     * Solicita confirmación al usuario antes de cerrar la aplicación,
     * ofreciendo la opción de guardar los datos antes de salir.
     */
    @FXML
    private void salir() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Desea guardar los datos antes de salir?",
            ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        confirm.setTitle("Salir");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                impl.serializar();
            }
            if (btn != ButtonType.CANCEL) {
                Stage stage = (Stage) txtNombre.getScene().getWindow();
                stage.close();
            }
        });
    }

    // ════════ Utilidades privadas ═════════════════════════════════════════════

    /**
     * Construye un objeto {@link Producto} del subtipo correspondiente
     * a partir de los valores ingresados en el formulario.
     * <p>
     * Valida que los campos obligatorios estén completos y que la cantidad sea positiva.
     * Retorna {@code null} y muestra un mensaje de error si alguna validación falla.
     * </p>
     *
     * @return el objeto {@link Producto} construido, o {@code null} si los datos son inválidos
     */
    private Producto construirProductoDesdeFormulario() {
        String nombre = txtNombre.getText().trim();
        String tipo   = cmbTipo.getValue();
        String cantStr = txtCantidad.getText().trim();
        String unidad  = txtUnidad.getText().trim();
        String fecha   = txtFecha.getText().trim();
        String obs     = txtObservaciones.getText().trim();

        if (nombre.isEmpty() || cantStr.isEmpty() || unidad.isEmpty() || fecha.isEmpty()) {
            mostrarError("Por favor complete todos los campos obligatorios (Nombre, Cantidad, Unidad, Fecha).");
            return null;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantStr);
            if (cantidad < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarError("La cantidad debe ser un número positivo.");
            return null;
        }

        switch (tipo) {
            case "Fertilizante": {
                String formula = txtFormula.getText().trim();
                if (formula.isEmpty()) { mostrarError("Ingrese el tipo de fórmula."); return null; }
                return new Fertilizante(0, nombre, cantidad, unidad, fecha, formula, obs);
            }
            case "Pesticida": {
                String plaga = txtPlaga.getText().trim();
                String tox   = txtToxicidad.getText().trim();
                if (plaga.isEmpty() || tox.isEmpty()) {
                    mostrarError("Ingrese la plaga objetivo y la toxicidad."); return null;
                }
                return new Pesticida(0, nombre, cantidad, unidad, fecha, plaga, tox, obs);
            }
            case "Abono": {
                String origen  = txtOrigen.getText().trim();
                String diasStr = txtDias.getText().trim();
                if (origen.isEmpty() || diasStr.isEmpty()) {
                    mostrarError("Ingrese el origen y los días de descomposición."); return null;
                }
                int dias;
                try {
                    dias = Integer.parseInt(diasStr);
                    if (dias < 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    mostrarError("Los días de descomposición deben ser un número entero positivo.");
                    return null;
                }
                return new Abono(0, nombre, cantidad, unidad, fecha, origen, dias, obs);
            }
            default:
                mostrarError("Tipo de producto no válido.");
                return null;
        }
    }

    /**
     * Sincroniza la lista observable con el arreglo interno y actualiza la tabla
     * y el indicador de capacidad.
     */
    private void refrescarTabla() {
        Producto[] todos = impl.leertodo();
        listaObservable.clear();
        for (Producto p : todos) listaObservable.add(p);
        tablaProductos.setItems(listaObservable);
        actualizarCapacidad();
    }

    /**
     * Actualiza la etiqueta de pie de página con la capacidad actual del arreglo
     * y el número de productos registrados.
     */
    private void actualizarCapacidad() {
        lblCapacidad.setText("Capacidad del arreglo: " + impl.getCapacidad()
            + "  |  Registros: " + impl.getContador());
    }

    /**
     * Determina el tipo de un producto a partir de su clase en tiempo de ejecución.
     *
     * @param p el producto a evaluar
     * @return cadena con el tipo ("Fertilizante", "Pesticida", "Abono" o "Desconocido")
     */
    private String getTipo(Producto p) {
        if (p instanceof Fertilizante) return "Fertilizante";
        if (p instanceof Pesticida)    return "Pesticida";
        if (p instanceof Abono)        return "Abono";
        return "Desconocido";
    }

    /**
     * Obtiene el texto de observaciones de un producto según su tipo.
     *
     * @param p el producto del cual se obtendrán las observaciones
     * @return el texto de observaciones, o cadena vacía si el tipo no es reconocido
     */
    private String getObservaciones(Producto p) {
        if (p instanceof Fertilizante) return ((Fertilizante) p).getObservaciones();
        if (p instanceof Pesticida)    return ((Pesticida) p).getObservaciones();
        if (p instanceof Abono)        return ((Abono) p).getObservaciones();
        return "";
    }

    /**
     * Muestra un cuadro de diálogo informativo con el mensaje indicado.
     *
     * @param msg el mensaje a mostrar al usuario
     */
    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo de error con el mensaje indicado.
     *
     * @param msg el mensaje de error a mostrar al usuario
     */
    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}