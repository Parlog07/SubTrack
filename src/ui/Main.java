package ui;

import dao.PaymentDAO;
import dao.SubscriptionDAO;
import service.PaymentService;
import service.SubscriptionService;

public class Main {

    public static void main(String[] args) {

        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        SubscriptionService subscriptionService =
                new SubscriptionService(subscriptionDAO);

        PaymentService paymentService =
                new PaymentService(paymentDAO);

        MainMenu mainMenu =
                new MainMenu(subscriptionService, paymentService);

        mainMenu.start();
    }
}