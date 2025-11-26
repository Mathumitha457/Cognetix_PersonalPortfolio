import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortfolioApp {

    // Simple data model for a person record
    static class Person {
        String name;
        String email;
        String phone;
        List<String> skills;
        List<String> achievements;

        Person(String name, String email, String phone, List<String> skills, List<String> achievements) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.skills = skills;
            this.achievements = achievements;
        }
    }

    // Storage for multiple records
    private static final List<Person> records = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Personal Portfolio Console App ===");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addRecord();
                    break;
                case "2":
                    listRecords();
                    break;
                case "3":
                    showRecordDetail();
                    break;
                case "4":
                    deleteRecord();
                    break;
                case "5":
                    exportSampleOutput();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1 - Add new personal record");
        System.out.println("2 - List all records (summary)");
        System.out.println("3 - Display a record (detailed)");
        System.out.println("4 - Delete a record");
        System.out.println("5 - Show sample output (demo)");
        System.out.println("0 - Exit");
        System.out.print("Enter option: ");
    }

    // Add record flow
    private static void addRecord() {
        System.out.println("\n--- Add New Record ---");

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (isValidEmail(email)) break;
            System.out.println("Invalid email. Must contain '@' and at least one character before/after. Try again.");
        }

        String phone;
        while (true) {
            System.out.print("Phone (digits only): ");
            phone = scanner.nextLine().trim();
            if (isValidPhone(phone)) break;
            System.out.println("Invalid phone. Use digits only (e.g., 9876543210). Try again.");
        }

        System.out.print("Skills (comma-separated, e.g. Java, SQL, Git): ");
        List<String> skills = parseCsvToList(scanner.nextLine());

        System.out.print("Achievements (comma-separated): ");
        List<String> achievements = parseCsvToList(scanner.nextLine());

        Person p = new Person(name, email, phone, skills, achievements);
        records.add(p);

        System.out.println("Record added successfully! Current total records: " + records.size());
    }

    // List summary of records
    private static void listRecords() {
        System.out.println("\n--- All Records (Summary) ---");
        if (records.isEmpty()) {
            System.out.println("No records yet.");
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            Person p = records.get(i);
            System.out.printf("%d) %s | %s | %s\n", i + 1, p.name, p.email, joinList(p.skills));
        }
    }

    // Display detailed record by index
    private static void showRecordDetail() {
        System.out.print("\nEnter record number to display (from 'List all records'): ");
        String s = scanner.nextLine().trim();
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx < 0 || idx >= records.size()) {
                System.out.println("Invalid record number.");
                return;
            }
            Person p = records.get(idx);
            System.out.println("\n--- Personal Details ---");
            System.out.println("Name: " + p.name);
            System.out.println("Email: " + p.email);
            System.out.println("Phone: " + p.phone);
            System.out.println("\n--- Skills ---");
            if (p.skills.isEmpty()) System.out.println("None");
            else p.skills.forEach(skill -> System.out.println("- " + skill));
            System.out.println("\n--- Achievements ---");
            if (p.achievements.isEmpty()) System.out.println("None");
            else p.achievements.forEach(a -> System.out.println("- " + a));
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Delete a record
    private static void deleteRecord() {
        System.out.print("\nEnter record number to delete: ");
        String s = scanner.nextLine().trim();
        try {
            int idx = Integer.parseInt(s) - 1;
            if (idx < 0 || idx >= records.size()) {
                System.out.println("Invalid record number.");
                return;
            }
            records.remove(idx);
            System.out.println("Record removed. Remaining records: " + records.size());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Sample output to help demos
    private static void exportSampleOutput() {
        System.out.println("\n--- Sample Demo Output ---");
        System.out.println("Name: Mathu Mitha");
        System.out.println("Email: example@mail.com");
        System.out.println("Phone: 9876543210");
        System.out.println("\nSkills:");
        System.out.println("- Java");
        System.out.println("- Git");
        System.out.println("- SQL");
        System.out.println("\nAchievements:");
        System.out.println("- College coding contest finalist");
        System.out.println("- Built a small Java web app");
    }

    // Helpers
    private static boolean isValidEmail(String email) {
        if (email == null) return false;
        email = email.trim();
        int atIndex = email.indexOf('@');
        int lastDot = email.lastIndexOf('.');
        // simple checks: contains '@', not first/last char, and has a dot after '@' optionally
        return atIndex > 0 && atIndex < email.length() - 1 && (lastDot == -1 || lastDot > atIndex);
    }

    private static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        // allow '+' at start optionally, otherwise digits only
        String candidate = phone.startsWith("+") ? phone.substring(1) : phone;
        return candidate.matches("\\d+"); // digits only
    }

    private static List<String> parseCsvToList(String csv) {
        List<String> out = new ArrayList<>();
        if (csv == null || csv.trim().isEmpty()) return out;
        String[] parts = csv.split(",");
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }

    private static String joinList(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(", ", list);
    }
}
