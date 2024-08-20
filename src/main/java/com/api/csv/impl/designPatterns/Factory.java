package com.api.csv.impl.designPatterns;

import java.util.Scanner;

public class Factory {

    interface Food {
        String getType();
    }

    public static class Pizza implements Food {
        public String getType() {
            return "Someone ordered a Fast Food!";
        }
    }

    public static class Cake implements Food {
        public String getType() {
            return "Someone ordered a Dessert!";
        }
    }

    public static class FoodFactory {
        public Food getFood(String order) {
            if (order.equals("pizza")) {
                return new Pizza();
            }
            return new Cake();
        }
    }

    public static class Solution {
        public static void main(String[] args) {
            try {

                FoodFactory foodFactory = new FoodFactory();

                Scanner scanner = new Scanner(System.in);
                //creating the factory

                String line = scanner.nextLine();

                //factory instantiates an object
                Food food = foodFactory.getFood(line);

                System.out.println("The factory returned " + food.getClass().getName());
                System.out.println(food.getType());

            } catch (Exception e) {
                System.out.println("Unsuccessful Termination!!");
            }
        }
    }
}
