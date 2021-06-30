package com.geekbrains.CloudClient;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {

    @FXML
    TableView<FileInfo> filesTable;

    @FXML
    ComboBox<String> repoBox;

    @FXML
    TextField pathField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableInitialize();
        repoBoxInitialize();
    }



    public void tableInitialize() {
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(24);


        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Name");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        fileNameColumn.setPrefWidth(240);


        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Size");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeColumn.setPrefWidth(120);
        fileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileInfo, Long>(){
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        String text = String.format("%,d bytes", item);
                        if (item == -1L) {
                            text = "[Directory]";
                        }
                        setText(text);
                    }
                }
            };
        });


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        TableColumn<FileInfo, String> fileDateTimeColumn = new TableColumn<>("Last modified data");
        fileDateTimeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dtf)));
        fileDateTimeColumn.setPrefWidth(120);

        filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateTimeColumn);
        filesTable.getSortOrder().add(fileTypeColumn);




        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Path pathOnClick = Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFilename());
                    if (Files.isDirectory(pathOnClick)) {
                        updateList(pathOnClick);
                    }
                }
            }
        });
        updateList(Paths.get("."));
    }



    public void repoBoxInitialize() {
        repoBox.getItems().clear();
        for (Path p : FileSystems.getDefault().getRootDirectories()){
            repoBox.getItems().add(p.toString());
        }
        repoBox.getSelectionModel().select(0);

    }




    public void updateList(Path path) {
        filesTable.getItems().clear();
        try {
            pathField.setText(path.normalize().toAbsolutePath().toString());
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update the file list", ButtonType.OK);
            alert.showAndWait();
        }
    }




    public void buttonPathAction(ActionEvent event) {
        Path topPath = Paths.get(pathField.getText()).getParent();
        if (topPath != null) {
            updateList(topPath);
        }
    }



    public void selectDirAction(ActionEvent event) {
        ComboBox<String> boxElement = (ComboBox<String>) event.getSource();
        updateList(Paths.get(boxElement.getSelectionModel().getSelectedItem()));
    }





    public String getSelectedFilename() {
        if (!filesTable.isFocused()) {
            return null;
        }
        return filesTable.getSelectionModel().getSelectedItem().getFilename();
    }




    public String getCurrentPath() {
        return pathField.getText();
    }


}
