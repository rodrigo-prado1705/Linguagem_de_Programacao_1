package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Database;
import com.rodrigo.prado.dbconnection.Planet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class HomeController {
    @FXML
    private BorderPane planetsPane;
    @FXML
    private ListView<Planet> listPlanets;
    @FXML
    private TextArea areaPlanetsDetails;
    @FXML
    private ContextMenu planetContextMenu;
    
    public void initialize() {
        refreshPlanetsTable();

        planetContextMenu = new ContextMenu();

        MenuItem deleteMenuItem = new MenuItem("Deletar");
        deleteMenuItem.setOnAction(actionEvent -> {
            Planet planet = listPlanets.getSelectionModel().getSelectedItem();

            deletePlanet(planet);
        });

        MenuItem updateMenuItem = new MenuItem("Editar");
        updateMenuItem.setOnAction(actionEvent -> {
            Planet planet = listPlanets.getSelectionModel().getSelectedItem();

            updatePlanet(planet);
        });

        planetContextMenu.getItems().setAll(deleteMenuItem, updateMenuItem);

        listPlanets.getSelectionModel().selectedItemProperty().addListener((observableValue,
                                                                            oldPlanet, newPlanet) -> {
            Planet planet = listPlanets.getSelectionModel().getSelectedItem();
            areaPlanetsDetails.setText(String.format(
                    "- Eixo: %.2f\n\n" +
                    "- Tempo de translação (dias terrestres): %d\n\n" +
                    "- Tempo de rotação (dias terrestres): %.2f\n\n" +
                    "- Número de satélites naturais: %d", planet.getAxis(), planet.getOrbitalDays(),
                    planet.getRotationDays(), planet.getQtySatellites()));
        });

        listPlanets.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Planet> call(ListView<Planet> planetListView) {
                ListCell<Planet> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Planet planet, boolean empty) {
                        super.updateItem(planet, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(planet.getName());
                        }
                    }
                };

                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(planetContextMenu);
                    }
                });

                return cell;
            }
        });

        listPlanets.setItems(Planet.getPlanets());
        listPlanets.getSelectionModel().selectFirst();
    }

    @FXML
    public void addPlanet () {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(planetsPane.getScene().getWindow());
        dialog.setTitle("Adicione um novo planeta");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("planet.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            PlanetControl controller = fxmlLoader.getController();
            Planet newPlanet = controller.processResult();

            listPlanets.getSelectionModel().select(newPlanet);
        }
    }

    public void deletePlanet(Planet planet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Deletar planeta");
        alert.setHeaderText("Deletar " + planet.getName());
        alert.setContentText("Tem certeza? Esta ação é irreversível. Aperte OK (continuar) ou CANCELAR (voltar).");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Database database = new Database();

            try {
                Connection connection = database.open();

                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM pln_planet " +
                        "WHERE pln_name = ?");
                pstmt.setString(1, planet.getName());

                pstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                database.close();
            }

            Planet.deletePlanet(planet);
        }
    }

    public void updatePlanet(Planet planet) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(planetsPane.getScene().getWindow());
        dialog.setTitle("Atualizar dados de " + planet.getName());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("planet.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        PlanetControl controller = fxmlLoader.getController();
        controller.preset(planet);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Planet updatedPlanet = controller.updateResult(planet);

            Planet.updatePlanet(planet, updatedPlanet);

            listPlanets.getSelectionModel().select(updatedPlanet);
        }
    }

    public void refreshPlanetsTable() {
        Database database = new Database();

        try {
            Connection connection = database.open();

            ResultSet resultSet = connection.prepareStatement("SELECT * FROM pln_planet").executeQuery();

            Planet.clearPlanets();

            while (resultSet.next()) {
                Planet planet = new Planet(resultSet.getString(1),
                        resultSet.getDouble(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5));

                Planet.addPlanets(planet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}
