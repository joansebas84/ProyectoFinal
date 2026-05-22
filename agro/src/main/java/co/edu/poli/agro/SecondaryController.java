package co.edu.poli.agro;

import java.io.IOException;

import co.edu.poli.agro.vista.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
    	co.edu.poli.agro.vista.App.setRoot("Primary");
    }
}