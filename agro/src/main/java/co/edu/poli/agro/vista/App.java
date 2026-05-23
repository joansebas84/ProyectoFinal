package co.edu.poli.agro.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX del sistema de gestión de productos agrícolas.
 * <p>
 * Extiende {@link Application} e inicializa la ventana principal cargando la vista
 * {@code primary.fxml} como pantalla inicial. Provee el método estático
 * {@link #setRoot(String)} para la navegación entre vistas desde los controladores.
 * </p>
 *
 * @author Joan Florez - Mateo Paredes
 * @version 1.0
 * @since 22/05/2026
 */
public class App extends Application {

    /** Escena principal de la aplicación compartida entre todas las vistas. */
    private static Scene scene;

    /**
     * Método de inicio de la aplicación JavaFX.
     * <p>
     * Crea la escena principal con la vista {@code primary.fxml} y la muestra
     * en el {@link Stage} proporcionado por el framework.
     * </p>
     *
     * @param stage el escenario principal proporcionado por JavaFX
     * @throws IOException si ocurre un error al cargar el archivo FXML inicial
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la vista raíz de la escena principal cargando el archivo FXML indicado.
     *
     * @param fxml nombre del archivo FXML a cargar (sin extensión ni ruta completa)
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carga y retorna el nodo raíz del archivo FXML especificado,
     * ubicado en el paquete {@code co/edu/poli/agro/}.
     *
     * @param fxml nombre del archivo FXML a cargar (sin extensión ni ruta completa)
     * @return el nodo {@link Parent} raíz del archivo FXML cargado
     * @throws IOException si ocurre un error al localizar o cargar el archivo FXML
     */
    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/edu/poli/agro/" + fxml + ".fxml"));
    	return fxmlLoader.load();
    }

    /**
     * Punto de entrada principal de la aplicación.
     * <p>
     * Invoca {@link Application#launch(String...)} para iniciar el ciclo de vida de JavaFX.
     * </p>
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        launch();
    }

}