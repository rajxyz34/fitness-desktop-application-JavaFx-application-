package com.example;

import java.util.List;

public class FoodResponse {
    private List<Food> foods;
    //getter
    public List<Food> getFoods() {
        return foods;
    }
}

class Food {
    private String description;
    private List<Nutrient> foodNutrients;
    

    
    private Double servingSize;
    private String servingSizeUnit;
    private String householdServingFullText;
    
    public String getDescription() {
        return description;
    }

    public List<Nutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public Double getServingSize() {
        return servingSize;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public String getHouseholdServingFullText() {
        return householdServingFullText;
    }
    



}


class Nutrient {
    private String nutrientName;
    private double value;
    private String unitName;

    public String getNutrientName() {
        return nutrientName;
    }

    public double getValue() {
        return value;
    }

    public String getUnitName() {
        return unitName;
    }
}
