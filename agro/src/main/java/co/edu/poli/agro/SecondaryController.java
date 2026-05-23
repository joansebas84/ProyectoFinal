package co.edu.poli.agro;

import java.io.IOException;

import co.edu.poli.agro.vista.App;
import javafx.fxml.FXML;

/**
 * Controlador secundario de la interfaz gráfica de la aplicación agrícola.
 * <p>
 * Gestiona los eventos de la ventana secundaria y permite la navegación
 * de regreso a la vista principal mediante {@link App#setRoot(String)}.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 * @see App
 */
public class SecondaryController {

    /**
     * Navega de regreso a la vista principal cargando el archivo FXML
     * {@code Primary.fxml} mediante {@link App#setRoot(String)}.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void switchToPrimary() throws IOException {
    	co.edu.poli.agro.vista.App.setRoot("Primary");
    }
}