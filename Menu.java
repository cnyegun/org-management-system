package FinalProject;

import java.util.Scanner;

public final class Menu {
    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void start(OrgManager initManager) {
        OrgManager currentManager = initManager;
        MenuChoice userChoice = MenuChoice.INVALID;
        while (userChoice != MenuChoice.QUIT) {
            displayMenu();
            userChoice = mapInputToMenuChoice(scanner.nextLine());
            currentManager = handleChoice(currentManager, userChoice);
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.print("""
                Organization management system
                ------------------------------
                \s
                1. Create and print hard coded organization
                2. Print organization, add person to it and finally print it
                3. Print organization, remove person from it and finally print it
                Q. Quit the application
                \s
                Your choice:\s""");
    }

    private OrgManager handleChoice(OrgManager currentManager, MenuChoice userChoice) {
        return switch (userChoice) {
            case CREATE -> handleCreateOption();
            case ADD -> handleAddOption(currentManager);
            case REMOVE -> handleRemoveOption(currentManager);
            case QUIT -> handleQuitOption(currentManager);
            case INVALID -> handleInvalidOption(currentManager);
        };
    }

    private OrgManager handleCreateOption() {
        OrgManager nextManager = new OrgManager(SampleOrgData.createSampleOrg());
        nextManager.displayOrg();
        return nextManager;
    }

    private OrgManager handleInvalidOption(OrgManager currentManager) {
        System.out.println("\nERROR: Invalid Input, Please enter 1, 2, 3, q or Q.\n");
        return currentManager;
    }

    private OrgManager handleAddOption(OrgManager currentManager) {
        if (currentManager.rootUnit() == null) {
            System.out.println("\nERROR: Organization is not created yet. Create it first in step 1.\n");
            return currentManager;
        }

        currentManager.displayOrg();
        System.out.print("Give unit name: ");
        String groupName = scanner.nextLine();
        Worker newWorker = inputNewWorker();
        if (newWorker == null) return currentManager;

        OrgManager.OperationResult result = OrgManager.appendWorker(currentManager, groupName, newWorker);

        if (result.success()) {
            result.manager().displayOrg();
            return result.manager();
        } else {
            System.out.println("\nERROR: Organization not found. Give it again.\n");
            return currentManager;
        }
    }
    private Worker inputNewWorker() {
        System.out.print("Give person name: ");
        String name = scanner.nextLine().trim();

        if (!name.matches("^[A-Z][a-z]+ [A-Z][a-z]+$")) {
            System.out.println("\nERROR: Invalid name. Please enter a valid name like John Smith.\n");
            return null;
        }

        System.out.print("Give person role: ");
        String role = scanner.nextLine().trim();
        return new Worker(name, role);
    }

    private OrgManager handleRemoveOption(OrgManager currentManager) {
        if (currentManager.rootUnit() == null) {
            System.out.println("\nERROR: Organization is not created yet. Create it first in step 1.\n");
            return currentManager;
        }

        currentManager.displayOrg();
        System.out.print("Give person name: ");
        String personName = scanner.nextLine();

        if (!personName.matches("^[A-Z][a-z]+ [A-Z][a-z]+$")) {
            System.out.println("\nERROR: Invalid name. Please enter a valid name like John Smith.\n");
            return currentManager;
        }

        OrgManager.OperationResult result = OrgManager.removeWorker(currentManager, personName);

        if (result.success()) {
            result.manager().displayOrg();
            return result.manager();
        } else {
            System.out.println("ERROR: Person not found. Give it again.");
            return currentManager;
        }
    }

    private OrgManager handleQuitOption(OrgManager currentManager) {
        System.out.println("Exiting...");
        return currentManager;
    }

    private MenuChoice mapInputToMenuChoice(String input) {
        return switch (input.toUpperCase()) {
            case "1" -> MenuChoice.CREATE;
            case "2" -> MenuChoice.ADD;
            case "3" -> MenuChoice.REMOVE;
            case "Q" -> MenuChoice.QUIT;
            default -> MenuChoice.INVALID;
        };
    }

    enum MenuChoice {CREATE, ADD, REMOVE, QUIT, INVALID}

}
