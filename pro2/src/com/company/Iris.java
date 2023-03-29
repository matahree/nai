package com.company;

import java.util.List;

public class Iris{
    private final List<Double> coordinates;
    private final String name;
    private int number_of_type;

    public Iris(String name, List<Double> coordinates) {
        this.coordinates = coordinates;
        this.name = name;
        set_number_of_type();
    }
    private void set_number_of_type(){
        switch (name){
            case "versicolor" -> number_of_type = 1;
            case "virginica" -> number_of_type = 0;
        }
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public int getNumber_of_type() {
        return number_of_type;
    }
}
