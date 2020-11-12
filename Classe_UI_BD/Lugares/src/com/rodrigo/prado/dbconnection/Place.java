package com.rodrigo.prado.dbconnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Place {
    private String name;
    private String description;
    private boolean visited;

    private static final ObservableList<Place> places = FXCollections.observableArrayList();

    public Place(String name, String description, boolean visited) {
        this.name = name;
        this.description = description;
        this.visited = visited;
    }

    public static ObservableList<Place> getPlaces() {
        return places;
    }

    public static void addPlaces(Place place) {
        places.add(place);
    }

    public static void deletePlace(Place place) {
        places.remove(place);
    }

    public static void updatePlace(Place place) {
        place.setVisited(true);

        int i = places.indexOf(place);
        places.set(i, place);
    }

    public static void clearPlaces() {
        places.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
