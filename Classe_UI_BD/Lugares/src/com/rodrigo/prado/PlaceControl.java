package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Database;
import com.rodrigo.prado.dbconnection.Place;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlaceControl {
    @FXML
    private TextField textName;
    @FXML
    private TextArea areaDesc;

    public Place processResult() {
        Place place = new Place(textName.getText(),
                areaDesc.getText(), false);

        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO plc_place " +
                    "(plc_name, plc_desc, plc_visited) " +
                    "VALUES (?, ?, ?)");
            pstmt.setString(1, place.getName());
            pstmt.setString(2, place.getDescription());
            pstmt.setInt(3, 0);

            pstmt.execute();

            Place.addPlaces(place);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        return place;
    }
}
