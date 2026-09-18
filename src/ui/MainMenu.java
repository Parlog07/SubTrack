package ui;

import service.PaymentService;
import service.SubscriptionService;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private SubscriptionMenu subscriptionMenu;
    private PaymentMenu paymentMenu;

    public MainMenu(
            SubscriptionService subscriptionService,
            PaymentService paymentService) {

        this.scanner = new Scanner(System.in);

        this.subscriptionMenu =
                new SubscriptionMenu(scanner, subscriptionService);

        this.paymentMenu =
                new PaymentMenu(scanner, paymentService);
    }

    public void start() {

        int choice;

        do {

            System.out.println();
            System.out.println("===== SUBTRACK =====");
            System.out.println("1. Subscription Management");
            System.out.println("2. Payment Management");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    subscriptionMenu.start();
                    break;

                case 2:
                    paymentMenu.start();
                    break;

                case 0:
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }
}