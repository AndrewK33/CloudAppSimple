package com.geekbrains.CloudClient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Controller {

    @FXML
    VBox leftPanel, rightPanel;

    //private NetworkWorker networkWorker;


    public void exitAction(ActionEvent event) {
        Platform.exit();
    }


    public void copyAction(ActionEvent event) {
        PanelController leftPanelC = (PanelController) leftPanel.getProperties().get("controllerReference");
        PanelController rightPanelC = (PanelController) rightPanel.getProperties().get("controllerReference");

        if (leftPanelC.getSelectedFilename() == null && rightPanelC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No files was selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController sourcePanelC = null, destinationPanelC = null;

        if (leftPanelC.getSelectedFilename() != null) {
            sourcePanelC = leftPanelC;
            destinationPanelC = rightPanelC;
        }

        if (rightPanelC.getSelectedFilename() != null) {
            sourcePanelC = rightPanelC;
            destinationPanelC = leftPanelC;
        }

        Path sourcePath = Paths.get(sourcePanelC.getCurrentPath(), sourcePanelC.getSelectedFilename());
        Path distinctPath = Paths.get(destinationPanelC.getCurrentPath()).resolve(sourcePath.getFileName().toString());

        try {
            Files.copy(sourcePath, distinctPath);
            destinationPanelC.updateList(Paths.get(destinationPanelC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to copy chosen file", ButtonType.OK);
            alert.showAndWait();
        }

    }









    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        //networkWorker = new NetworkWorker();
    }*/


}
