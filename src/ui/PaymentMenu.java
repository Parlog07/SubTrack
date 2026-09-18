package ui;

import model.Payment;
import model.PaymentStatus;
import service.PaymentService;
import util.DateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PaymentMenu {

    private Scanner scanner;
    private PaymentService paymentService;

    public PaymentMenu(
            Scanner scanner,
            PaymentService paymentService) {

        this.scanner = scanner;
        this.paymentService = paymentService;
    }

    public void start() {

        int choice;

        do {

            System.out.println();
            System.out.println("===== PAYMENTS =====");
            System.out.println("1. Register payment");
            System.out.println("2. List payments");
            System.out.println("3. Payments by subscription");
            System.out.println("4. Unpaid payments");
            System.out.println("5. Last 5 payments");
            System.out.println("6. Delete payment");
            System.out.println("7. Detect late payments");
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
                    registerPayment();
                    break;

                case 2:
                    displayPayments(paymentService.getAllPayments());
                    break;

                case 3:
                    paymentsBySubscription();
                    break;

                case 4:
                    unpaidPayments();
                    break;

                case 5:
                    displayPayments(paymentService.getLastPayments());
                    break;

                case 6:
                    deletePayment();
                    break;

                case 7:
                    paymentService.detectLatePayments();
                    System.out.println("Late payments updated.");
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }

    private void registerPayment() {

        System.out.print("Subscription ID: ");
        String subscriptionId = scanner.nextLine();

        System.out.print("Due date (dd/MM/yyyy): ");
        String dueDateInput = scanner.nextLine();

        if (!DateUtil.isValidDate(dueDateInput)) {
            System.out.println("Invalid due date.");
            return;
        }

        System.out.print("Payment date (dd/MM/yyyy): ");
        String paymentDateInput = scanner.nextLine();

        if (!DateUtil.isValidDate(paymentDateInput)) {
            System.out.println("Invalid payment date.");
            return;
        }

        System.out.print("Payment type: ");
        String paymentType = scanner.nextLine();

        LocalDate dueDate = DateUtil.parseDate(dueDateInput);
        LocalDate paymentDate = DateUtil.parseDate(paymentDateInput);

        PaymentStatus status;

        if (paymentDate.isAfter(dueDate)) {
            status = PaymentStatus.LATE;
        } else {
            status = PaymentStatus.PAID;
        }

        Payment payment = new Payment(
                subscriptionId,
                dueDate,
                paymentDate,
                paymentType,
                status
        );

        paymentService.createPayment(payment);

        System.out.println("Payment registered successfully.");
    }

    private void paymentsBySubscription() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        displayPayments(
                paymentService.getPaymentsBySubscription(id)
        );
    }

    private void unpaidPayments() {

        System.out.print("Subscription ID: ");
        String id = scanner.nextLine();

        displayPayments(
                paymentService.getUnpaidPayments(id)
        );
    }

    private void deletePayment() {

        System.out.print("Payment ID: ");
        String id = scanner.nextLine();

        if (paymentService.deletePayment(id)) {
            System.out.println("Payment deleted.");
        } else {
            System.out.println("Payment not found.");
        }
    }

    private void displayPayments(List<Payment> payments) {

        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }

        payments.forEach(payment -> {

            System.out.println();
            System.out.println("ID: " + payment.getPaymentId());
            System.out.println(
                    "Subscription: " + payment.getSubscriptionId()
            );
            System.out.println(
                    "Due date: "
                            + DateUtil.formatDate(payment.getDueDate())
            );
            System.out.println(
                    "Payment date: "
                            + DateUtil.formatDate(payment.getPaymentDate())
            );
            System.out.println("Type: " + payment.getPaymentType());
            System.out.println("Status: " + payment.getStatus());
            System.out.println("----------------------------");
        });
    }
}