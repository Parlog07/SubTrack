package service;

import dao.PaymentDAO;
import dao.SubscriptionDAO;
import model.Payment;
import model.PaymentStatus;
import model.Subscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentService {

    private PaymentDAO paymentDAO;
    private SubscriptionDAO subscriptionDAO;

    public PaymentService(
            PaymentDAO paymentDAO,
            SubscriptionDAO subscriptionDAO) {

        this.paymentDAO = paymentDAO;
        this.subscriptionDAO = subscriptionDAO;
    }

    public void createPayment(Payment payment) {
        paymentDAO.create(payment);
    }

    public Optional<Payment> getPaymentById(String id) {
        return paymentDAO.findById(id);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    public List<Payment> getPaymentsBySubscription(String subscriptionId) {
        return paymentDAO.findBySubscription(subscriptionId);
    }

    public boolean updatePayment(Payment payment) {
        return paymentDAO.update(payment);
    }

    public boolean deletePayment(String id) {
        return paymentDAO.delete(id);
    }

    public List<Payment> getUnpaidPayments(String subscriptionId) {
        return paymentDAO.findUnpaidBySubscription(subscriptionId);
    }

    public List<Payment> getLastPayments() {
        return paymentDAO.findLastPayments();
    }

    public void detectLatePayments() {

        LocalDate today = LocalDate.now();

        paymentDAO.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.UNPAID)
                .filter(payment ->
                        payment.getDueDate().isBefore(today))
                .forEach(payment -> {
                    payment.setStatus(PaymentStatus.LATE);
                    paymentDAO.update(payment);
                });
    }

    public double getTotalPaidBySubscription(String subscriptionId) {

        Optional<Subscription> subscription =
                subscriptionDAO.findById(subscriptionId);

        if (!subscription.isPresent()) {
            return 0;
        }

        double monthlyAmount =
                subscription.get().getMonthlyAmount();

        long paidPayments =
                paymentDAO.findBySubscription(subscriptionId)
                        .stream()
                        .filter(payment ->
                                payment.getStatus() == PaymentStatus.PAID)
                        .count();

        return monthlyAmount * paidPayments;
    }

    public double getTotalUnpaidBySubscription(String subscriptionId) {

        Optional<Subscription> subscription =
                subscriptionDAO.findById(subscriptionId);

        if (!subscription.isPresent()) {
            return 0;
        }

        double monthlyAmount =
                subscription.get().getMonthlyAmount();

        long unpaidPayments =
                paymentDAO.findBySubscription(subscriptionId)
                        .stream()
                        .filter(payment ->
                                payment.getStatus() == PaymentStatus.UNPAID
                                        || payment.getStatus() == PaymentStatus.LATE)
                        .count();

        return monthlyAmount * unpaidPayments;
    }

    public double getMonthlyTotal(int month, int year) {

        return paymentDAO.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.PAID)
                .filter(payment ->
                        payment.getPaymentDate() != null)
                .filter(payment ->
                        payment.getPaymentDate().getMonthValue() == month
                                && payment.getPaymentDate().getYear() == year)
                .mapToDouble(this::getPaymentAmount)
                .sum();
    }

    public double getAnnualTotal(int year) {

        return paymentDAO.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.PAID)
                .filter(payment ->
                        payment.getPaymentDate() != null)
                .filter(payment ->
                        payment.getPaymentDate().getYear() == year)
                .mapToDouble(this::getPaymentAmount)
                .sum();
    }

    public double getTotalUnpaid() {

        return paymentDAO.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.UNPAID
                                || payment.getStatus() == PaymentStatus.LATE)
                .mapToDouble(this::getPaymentAmount)
                .sum();
    }

    private double getPaymentAmount(Payment payment) {

        return subscriptionDAO
                .findById(payment.getSubscriptionId())
                .map(Subscription::getMonthlyAmount)
                .orElse(0.0);
    }
}