package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Database;
import com.rodrigo.prado.dbconnection.Planet;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanetControl {
    @FXML
    private TextField textName;
    @FXML
    private Spinner<Double> spnAxis;
    @FXML
    private TextField textOrbitalPeriod;
    @FXML
    private TextField textRotationPeriod;
    @FXML
    private TextField textQtySatellites;

    private Planet updatePlanet;

    public void initialize() {

    }

    public Planet updateResult(Planet oldPlanet) {
        updatePlanet.setName(textName.getText());
        updatePlanet.setAxis(spnAxis.getValue());
        updatePlanet.setOrbitalDays(Integer.parseInt(textOrbitalPeriod.getText()));
        updatePlanet.setRotationDays(Double.parseDouble(textRotationPeriod.getText()));
        updatePlanet.setQtySatellites(Integer.parseInt(textQtySatellites.getText()));

        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("UPDATE pln_planet SET " +
                    "pln_name = ?, pln_axis = ?, pln_orbital_days = ?, pln_rotation_days = ?, " +
                    "pln_qty_satellites = ? WHERE pln_name = ?");
            pstmt.setString(1, updatePlanet.getName());
            pstmt.setDouble(2, updatePlanet.getAxis());
            pstmt.setInt(3, updatePlanet.getOrbitalDays());
            pstmt.setDouble(4, updatePlanet.getRotationDays());
            pstmt.setInt(5, updatePlanet.getQtySatellites());
            pstmt.setString(6, oldPlanet.getName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        return updatePlanet;
    }

    public void preset(Planet planet) {
        textName.setText(planet.getName());
        spnAxis.getValueFactory().setValue(planet.getAxis());
        textOrbitalPeriod.setText(String.valueOf(planet.getOrbitalDays()));
        textRotationPeriod.setText(String.valueOf(planet.getRotationDays()));
        textQtySatellites.setText(String.valueOf(planet.getQtySatellites()));

        updatePlanet = planet;
    }

    public Planet processResult() {
        Planet planet = new Planet(textName.getText(),
                spnAxis.getValue(),
                Integer.parseInt(textOrbitalPeriod.getText()),
                Double.parseDouble(textRotationPeriod.getText()),
                Integer.parseInt(textQtySatellites.getText()));

        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO pln_planet " +
                    "(pln_name, pln_axis, pln_orbital_days, pln_rotation_days, pln_qty_satellites) " +
                    "VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, planet.getName());
            pstmt.setDouble(2, planet.getAxis());
            pstmt.setInt(3, planet.getOrbitalDays());
            pstmt.setDouble(4, planet.getRotationDays());
            pstmt.setInt(5, planet.getQtySatellites());

            pstmt.execute();

            Planet.addPlanets(planet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        return planet;
    }
}
