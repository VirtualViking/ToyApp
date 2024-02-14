package org.example;

import model.Category;
import model.Toy;
import service.imp.ToyServiceImp;
import java.util.Scanner;


public class Main {

    private static final ToyServiceImp toyService = new ToyServiceImp();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to Toy Service!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nToy Service Main Menu");
            System.out.println("1. Add a toy");
            System.out.println("2. Increase amount of a toy");
            System.out.println("3. Decrease amount of a toy");
            System.out.println("4. Get total toys amount");
            System.out.println("5. Get total toys price");
            System.out.println("6. Get most amount toy category");
            System.out.println("7. Get less amount toy category");
            System.out.println("8. Get toys with price greater than");
            System.out.println("9. Sort toys by amount grouped by category");
            System.out.println("10. Get all toys");
            System.out.println("11. Remove toy");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    increaseAmount();
                    break;
                case 3:
                    decreaseAmount();
                    break;
                case 4:
                    System.out.println("Total toys amount: " + toyService.getTotalToysAmount());
                    break;
                case 5:
                    System.out.println("Total toys price: " + toyService.getTotalToysPrice());
                    break;
                case 6:
                    System.out.println("Most amount toy category: " + toyService.getMostAmountToyCategory());
                    break;
                case 7:
                    System.out.println("Less amount toy category: " + toyService.getLessAmountToyCategory());
                    break;
                case 8:
                    getToysWithPriceGreaterThan();
                    break;
                case 9:
                    System.out.println("Sorted toys by amount grouped by category: " + toyService.sortToysByAmountGroupedByCategory());
                    break;
                case 10:
                    System.out.println("All toys: ");
                    toyService.getAllToys().forEach(t -> System.out.println("Name: " + t.toyName() + ", Price: " + t.price()));
                    break;
                case 11:
                    deleteToy();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addToy() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter toy amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter toy category (0 for FEMENINO, 1 for MASCULINO, 2 for UNISEX): ");
        int category = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Category toyCategory = Category.fromName(category);
        System.out.print("Enter toy price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Toy toy = new Toy(name, amount, toyCategory, price);
        try {
            toyService.addToy(toy);
            System.out.println("Toy added successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private static void increaseAmount() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter amount to increase: ");
        int amount = scanner.nextInt();
        try {
            toyService.increaseAmountToy(name, amount);
            System.out.println("Amount increased successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private static void decreaseAmount() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        System.out.print("Enter amount to decrease: ");
        int amount = scanner.nextInt();
        try {
            toyService.decreaseAmountToy(name, amount);
            System.out.println("Amount decreased successfully!");
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getToysWithPriceGreaterThan() {
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.println("Toys with price greater than " + price + ": ");
        toyService.getToysWithPriceGreaterThan(price).forEach(toy -> {
            System.out.println("Toy: " + toy.getToyName() + ", " + " Price: " + toy.getToyPrice());
        });
    }

    private static void deleteToy() {
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine();
        try {
            toyService.deleteToyByName(name);
            System.out.println("Toy deleted successfully!");
        } catch (RuntimeException exception) {
            System.out.println("ERROR: " +  exception.getMessage());
        }
    }
}