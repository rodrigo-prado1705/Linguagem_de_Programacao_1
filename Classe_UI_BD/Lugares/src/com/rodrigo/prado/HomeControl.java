package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Database;
import com.rodrigo.prado.dbconnection.Place;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class HomeControl {
    @FXML
    private BorderPane mainPane;
    @FXML
    private ListView<Place> listPlaces;
    @FXML
    private TextArea areaPlacesDetails;
    @FXML
    private ContextMenu placeContextMenu;
    
    public void initialize() {
        refreshPlacesTable();

        placeContextMenu = new ContextMenu();

        MenuItem deleteMenuItem = new MenuItem("Deletar");
        deleteMenuItem.setOnAction(actionEvent -> {
            Place place = listPlaces.getSelectionModel().getSelectedItem();

            deletePlace(place);
        });

        placeContextMenu.getItems().add(deleteMenuItem);

        listPlaces.getSelectionModel().selectedItemProperty().addListener((observableValue,
                                                                            oldPlanet, newPlanet) -> {
            Place planet = listPlaces.getSelectionModel().getSelectedItem();
            areaPlacesDetails.setText(planet.getDescription());
        });

        listPlaces.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Place> call(ListView<Place> planetListView) {
                ListCell<Place> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Place place, boolean empty) {
                        super.updateItem(place, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(place.getName());

                            if (place.isVisited()) {
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(placeContextMenu);
                    }
                });

                return cell;
            }
        });

        listPlaces.setItems(Place.getPlaces());
        listPlaces.getSelectionModel().selectFirst();
    }

    @FXML
    public void addPlaces() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("Adicione um novo lugar");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("place.fxml"));

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
            PlaceControl controller = fxmlLoader.getController();
            Place newPlace = controller.processResult();

            listPlaces.getSelectionModel().select(newPlace);
        }
    }

    @FXML
    public void updatePlace() {
        Database database = new Database();

        Place place = listPlaces.getSelectionModel().getSelectedItem();

        if (place != null) {
            try {
                Connection connection = database.open();

                PreparedStatement pstmt = connection.prepareStatement("UPDATE plc_place SET " +
                        "plc_visited = ? WHERE plc_name = ?");
                pstmt.setInt(1, 1);
                pstmt.setString(2, place.getName());

                pstmt.execute();
                
                Place.updatePlace(place);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                database.close();
            }
        }
    }

    public void deletePlace(Place place) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Deletar lugar");
        alert.setHeaderText("Deletar " + place.getName());
        alert.setContentText("Tem certeza? Esta ação é irreversível. Aperte OK (continuar) ou CANCELAR (voltar).");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Database database = new Database();

            try {
                Connection connection = database.open();

                PreparedStatement pstmt = connection.prepareStatement("DELETE FROM plc_place " +
                        "WHERE plc_name = ?");
                pstmt.setString(1, place.getName());

                pstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                database.close();
            }

            Place.deletePlace(place);
        }
    }

    public void refreshPlacesTable() {
        Database database = new Database();

        try {
            Connection connection = database.open();

            ResultSet resultSet = connection.prepareStatement("SELECT * FROM plc_place").executeQuery();

            Place.clearPlaces();

            while (resultSet.next()) {
                Place planet = new Place(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3) != 0);

                Place.addPlaces(planet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}
