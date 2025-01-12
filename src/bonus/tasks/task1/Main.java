package src.bonus.tasks.task1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, List<String>> countriesAndCities = new HashMap<>();
        countriesAndCities.put("Poland", Arrays.asList("Warsaw", "Wroclaw", "Cracow"));
        countriesAndCities.put("Germany", Arrays.asList("Berlin", "Munich", "Dortmund"));
        countriesAndCities.put("Spain", Arrays.asList("Valencia", "Barcelona", "Madrid"));
        countriesAndCities.forEach((country, cities) -> {
            System.out.println("Country: " + country + " -> cities: " + cities);
        });
    }
}