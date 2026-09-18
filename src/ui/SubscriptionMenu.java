package ui;

import model.Subscription;
import model.SubscriptionStatus;
import model.SubscriptionWithCommitment;
import model.SubscriptionWithoutCommitment;
import service.SubscriptionService;
import util.DateUtil;
import util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SubscriptionMenu {

    private Scanner scanner;
    private SubscriptionService subscriptionService;

    public SubscriptionMenu(
            Scanner scanner,
            SubscriptionService subscriptionService) {

        this.scanner = scanner;
        this.subscriptionService = subscriptionService;
    }

    public void start() {

        int choice;

        do {

            System.out.println();
            System.out.println("===== SUBSCRIPTIONS =====");
            System.out.println("1. Create subscription");
            System.out.println("2. List subscriptions");
            System.out.println("3. Find subscription");
            System.out.println("4. Update subscription");
            System.out.println("5. Delete subscription");
            System.out.println("6. Cancel subscription");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createSubscription();
                    break;

                case 2:
                    listSubscriptions();
                    break;

                case 3:
                    findSubscription();
                    break;

                case 4:
                    updateSubscription();
                    break;

                case 5:
                    deleteSubscription();
                    break;

                case 6:
                    cancelSubscription();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }

    private void createSubscription() {

        System.out.println("1. With commitment");
        System.out.println("2. Without commitment");
        System.out.print("Choose type: ");

        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Service name: ");
        String serviceName = scanner.nextLine();

        if (!ValidationUtil.isValidName(serviceName)) {
            System.out.println("Invalid service name.");
            return;
        }

        System.out.print("Monthly amount: ");
        double monthlyAmount = scanner.nextDouble();
        scanner.nextLine();

        if (!ValidationUtil.isValidAmount(monthlyAmount)) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.print("Start date (dd/MM/yyyy): ");
        String startDateInput = scanner.nextLine();

        if (!DateUtil.isValidDate(startDateInput)) {
            System.out.println("Invalid date.");
            return;
        }

        LocalDate startDate = DateUtil.parseDate(startDateInput);

        System.out.print("End date (dd/MM/yyyy): ");
        String endDateInput = scanner.nextLine();

        if (!DateUtil.isValidDate(endDateInput)) {
            System.out.println("Invalid date.");
            return;
        }

        LocalDate endDate = DateUtil.parseDate(endDateInput);

        if (type == 1) {

            System.out.print("Commitment duration in months: ");
            int duration = scanner.nextInt();
            scanner.nextLine();

            if (!ValidationUtil.isValidCommitmentDuration(duration)) {
                System.out.println("Invalid duration.");
                return;
            }

            Subscription subscription =
                    new SubscriptionWithCommitment(
                            serviceName,
                            monthlyAmount,
                            startDate,
                            endDate,
                            SubscriptionStatus.ACTIVE,
                            duration
                    );

            subscriptionService.createSubscription(subscription);

        } else if (type == 2) {

            Subscription subscription =
                    new SubscriptionWithoutCommitment(
                            serviceName,
                            monthlyAmount,
                            startDate,
                            endDate,
                            SubscriptionStatus.ACTIVE
                    );

            subscriptionService.createSubscription(subscription);

        } else {

            System.out.println("Invalid subscription type.");
            return;
        }

        System.out.println("Subscription created successfully.");
    }

    private void listSubscriptions() {

        List<Subscription> subscriptions =
                subscriptionService.getAllSubscriptions();

        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }

        subscriptions.forEach(this::displaySubscription);
    }

    private void findSubscription() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        Optional<Subscription> subscription =
                subscriptionService.getSubscriptionById(id);

        if (subscription.isPresent()) {
            displaySubscription(subscription.get());
        } else {
            System.out.println("Subscription not found.");
        }
    }

    private void updateSubscription() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        Optional<Subscription> result =
                subscriptionService.getSubscriptionById(id);

        if (!result.isPresent()) {
            System.out.println("Subscription not found.");
            return;
        }

        Subscription subscription = result.get();

        System.out.print("New service name: ");
        String serviceName = scanner.nextLine();

        System.out.print("New monthly amount: ");
        double monthlyAmount = scanner.nextDouble();
        scanner.nextLine();

        subscription.setServiceName(serviceName);
        subscription.setMonthlyAmount(monthlyAmount);

        boolean updated =
                subscriptionService.updateSubscription(subscription);

        if (updated) {
            System.out.println("Subscription updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void deleteSubscription() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        boolean deleted =
                subscriptionService.deleteSubscription(id);

        if (deleted) {
            System.out.println("Subscription deleted.");
        } else {
            System.out.println("Subscription not found.");
        }
    }

    private void cancelSubscription() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        boolean cancelled =
                subscriptionService.cancelSubscription(id);

        if (cancelled) {
            System.out.println("Subscription cancelled.");
        } else {
            System.out.println("Subscription not found.");
        }
    }

    private void displaySubscription(Subscription subscription) {

        System.out.println();
        System.out.println("ID: " + subscription.getId());
        System.out.println("Service: " + subscription.getServiceName());
        System.out.println("Amount: " + subscription.getMonthlyAmount());
        System.out.println(
                "Start date: "
                        + DateUtil.formatDate(subscription.getStartDate())
        );
        System.out.println(
                "End date: "
                        + DateUtil.formatDate(subscription.getEndDate())
        );
        System.out.println("Status: " + subscription.getStatus());
        System.out.println("----------------------------");
    }
}