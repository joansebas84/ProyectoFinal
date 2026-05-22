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

public class PrimaryController {

    // ── Campos del formulario ────────────────────────────────────────────────
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtUnidad;
    @FXML private TextField txtFecha;
    @FXML private TextArea  txtObservaciones;

    // Banner image
    @FXML private ImageView bannerImage;

    // Paneles dinámicos
    @FXML private javafx.scene.layout.VBox panelFertilizante;
    @FXML private TextField txtFormula;

    @FXML private javafx.scene.layout.VBox panelPesticida;
    @FXML private TextField txtPlaga;
    @FXML private TextField txtToxicidad;

    @FXML private javafx.scene.layout.VBox panelAbono;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDias;

    // ── Tabla ────────────────────────────────────────────────────────────────
    @FXML private TableView<Producto>       tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colTipo;
    @FXML private TableColumn<Producto, String> colCantidad;
    @FXML private TableColumn<Producto, String> colFecha;
    @FXML private TableColumn<Producto, String> colObs;

    // ── Footer ───────────────────────────────────────────────────────────────
    @FXML private TextField txtBuscar;
    @FXML private Label     lblCapacidad;

    // ── Lógica ───────────────────────────────────────────────────────────────
    private final ImplementacionOperacionCRUD impl = new ImplementacionOperacionCRUD();
    private ObservableList<Producto> listaObservable = FXCollections.observableArrayList();
    private int indiceSeleccionado = -1; // índice en el arreglo interno

    // ════════════════════════════════════════════════════════════════════════
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
    @FXML
    private void guardarDatos() {
        try {
            String resultado = impl.serializar();
            mostrarInfo(resultado);
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

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

    private void refrescarTabla() {
        Producto[] todos = impl.leertodo();
        listaObservable.clear();
        for (Producto p : todos) listaObservable.add(p);
        tablaProductos.setItems(listaObservable);
        actualizarCapacidad();
    }

    private void actualizarCapacidad() {
        lblCapacidad.setText("Capacidad del arreglo: " + impl.getCapacidad()
            + "  |  Registros: " + impl.getContador());
    }

    private String getTipo(Producto p) {
        if (p instanceof Fertilizante) return "Fertilizante";
        if (p instanceof Pesticida)    return "Pesticida";
        if (p instanceof Abono)        return "Abono";
        return "Desconocido";
    }

    private String getObservaciones(Producto p) {
        if (p instanceof Fertilizante) return ((Fertilizante) p).getObservaciones();
        if (p instanceof Pesticida)    return ((Pesticida) p).getObservaciones();
        if (p instanceof Abono)        return ((Abono) p).getObservaciones();
        return "";
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}