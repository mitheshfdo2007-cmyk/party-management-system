import java.util.ArrayList;
import java.util.Scanner;

// Main class containing the scenario enactment
public class Project {
    public static void main(String[] args) {
        // Use an ArrayList<Party> for polymorphism
        ArrayList<Party> parties = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Menu loop
        do {
            System.out.println("\n*** Party Management System ***");
            System.out.println("1. Add Party");
            System.out.println("2. Display All Parties");
            System.out.println("3. Search by Party Name");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            // Input validation
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = 0;
            }

            switch (choice) {
                case 1:
                    addParty(parties, scanner);
                    break;
                case 2:
                    displayAllParties(parties);
                    break;
                case 3:
                    searchPartyByName(parties, scanner);
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addParty(ArrayList<Party> parties, Scanner scanner) {
        System.out.print("Enter type (1=Birthday Party, 2=Wedding Party): ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Common party details
        System.out.print("Enter Party Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        Party newParty = null;

        if (typeChoice == 1) { // Birthday Party
            System.out.print("Enter Birthday Person: ");
            String birthdayPerson = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            System.out.print("Enter Cake Budget: ");
            double cakeBudget = scanner.nextDouble();
            scanner.nextLine(); 

            newParty = new BirthdayParty(name, location, date, birthdayPerson, age, cakeBudget);
        } else if (typeChoice == 2) { // Wedding Party
            System.out.print("Enter Guest Count: ");
            int guestCount = scanner.nextInt();
            System.out.print("Enter Cost Per Guest: ");
            double costPerGuest = scanner.nextDouble();
            System.out.print("Enter Hall Charge: ");
            double hallCharge = scanner.nextDouble();
            scanner.nextLine(); 

            newParty = new WeddingParty(name, location, date, guestCount, costPerGuest, hallCharge);
        } else {
            System.out.println("Invalid party type.");
            return;
        }

        parties.add(newParty);
        System.out.println("Party added successfully.");
    }

    private static void displayAllParties(ArrayList<Party> parties) {
        if (parties.isEmpty()) {
            System.out.println("No parties to display.");
            return;
        }
        System.out.println("\n*** All Parties ***");
        for (Party party : parties) {
            // Polymorphism in action
            System.out.println(party.toString()); 
            System.out.printf("Total Cost: Rs. %.2f%n", party.calculateTotalCost());
            System.out.println("---");
        }
    }

    private static void searchPartyByName(ArrayList<Party> parties, Scanner scanner) {
        System.out.print("\nEnter Party Name to Search: ");
        String searchName = scanner.nextLine();
        boolean found = false;

        for (Party party : parties) {
            // Case-insensitive search
            if (party.getPartyName().equalsIgnoreCase(searchName)) { 
                System.out.println("\nParty Found!");
                System.out.println(party.toString()); 
                System.out.printf("Total Cost: Rs. %.2f%n", party.calculateTotalCost());
                found = true;
                break;
            }
        }

        if (!found) { // if-statement use
            System.out.println("Party not found.");
        }
    }
}

// Superclass: Party
abstract class Party {
    // Private attributes
    private String partyName;
    private String location;
    private String date;

    // Constructor
    public Party(String partyName, String location, String date) {
        this.partyName = partyName;
        this.location = location;
        this.date = date;
    }

    // Abstract method to be implemented by subclasses
    public abstract double calculateTotalCost();

    // Getters (Required by assignment specifications)
    public String getPartyName() {
        return partyName;
    }

    // toString() method (Required by assignment specifications)
    @Override
    public String toString() {
        return "Name: " + partyName + 
               "\nLocation: " + location + 
               "\nDate: " + date;
    }
}

// Subclass 1: Birthday Party
class BirthdayParty extends Party {
    // Additional attributes
    private String birthdayPerson;
    private int age;
    private double cakeBudget;

    // Constructor
    public BirthdayParty(String partyName, String location, String date, 
                         String birthdayPerson, int age, double cakeBudget) {
        super(partyName, location, date);
        this.birthdayPerson = birthdayPerson;
        this.age = age;
        this.cakeBudget = cakeBudget;
    }

    // Method to calculate total cost
    @Override
    public double calculateTotalCost() {
        // cakeBudget + 5000 (fixed decoration fee)
        return cakeBudget + 5000.0;
    }

    // toString() method to return all details
    @Override
    public String toString() {
        return super.toString() +
               "\nType: Birthday Party" +
               "\nBirthday Person: " + birthdayPerson + 
               "\nAge: " + age;
    }
}

// Subclass 2: Wedding Party
class WeddingParty extends Party {
    // Additional attributes
    private int guestCount;
    private double costPerGuest;
    private double hallCharge;

    // Constructor
    public WeddingParty(String partyName, String location, String date, 
                        int guestCount, double costPerGuest, double hallCharge) {
        super(partyName, location, date);
        this.guestCount = guestCount;
        this.costPerGuest = costPerGuest;
        this.hallCharge = hallCharge;
    }

    // Method to calculate total cost
    @Override
    public double calculateTotalCost() {
        // (guestCount * costPerGuest) + hallCharge
        return (guestCount * costPerGuest) + hallCharge;
    }

    // toString() method to return all details
    @Override
    public String toString() {
        return super.toString() +
               "\nType: Wedding Party" +
               "\nGuest Count: " + guestCount + 
               "\nCost Per Guest: " + costPerGuest +
               "\nHall Charge: " + hallCharge;
    }
}