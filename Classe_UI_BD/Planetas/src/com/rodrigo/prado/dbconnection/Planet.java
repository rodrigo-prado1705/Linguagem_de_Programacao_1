package com.rodrigo.prado.dbconnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Planet {
    private String name;
    private double axis;

    private int orbitalDays;
    private double rotationDays;
    private int qtySatellites;

    private static final ObservableList<Planet> planets = FXCollections.observableArrayList();

    public Planet(String name, double axis, int orbitalDays, double rotationDays, int qtySatellites) {
        this.name = name;
        this.axis = axis;
        this.orbitalDays = orbitalDays;
        this.rotationDays = rotationDays;
        this.qtySatellites = qtySatellites;
    }

    public static ObservableList<Planet> getPlanets() {
        return planets;
    }

    public static void addPlanets(Planet planet) {
        planets.add(planet);
    }

    public static void updatePlanet(Planet oldPlanet, Planet newPlanet) {
        int i = planets.indexOf(oldPlanet);
        planets.set(i, newPlanet);
    }

    public static void deletePlanet(Planet planet) {
        planets.remove(planet);
    }

    public static void clearPlanets() {
        planets.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAxis() {
        return axis;
    }

    public void setAxis(double axis) {
        this.axis = axis;
    }

    public int getOrbitalDays() {
        return orbitalDays;
    }

    public void setOrbitalDays(int orbitalDays) {
        this.orbitalDays = orbitalDays;
    }

    public double getRotationDays() {
        return rotationDays;
    }

    public void setRotationDays(double rotationDays) {
        this.rotationDays = rotationDays;
    }

    public int getQtySatellites() {
        return qtySatellites;
    }

    public void setQtySatellites(int qtySatellites) {
        this.qtySatellites = qtySatellites;
    }
}
