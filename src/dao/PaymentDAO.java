package dao;

import model.Payment;
import model.PaymentStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentDAO {

    private List<Payment> payments = new ArrayList<>();


    public void create(Payment payment) {
        payments.add(payment);
    }

    public Optional<Payment> findById(String id) {

        return payments.stream()
                .filter(payment ->
                        payment.getPaymentId().equals(id)
                )
                .findFirst();
    }

    public List<Payment> findBySubscription(String subscriptionId) {

        return payments.stream()
                .filter(payment ->
                        payment.getSubscriptionId()
                                .equals(subscriptionId)
                )
                .collect(Collectors.toList());
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments);
    }

    public boolean update(Payment updatedPayment) {

        Optional<Payment> result =
                findById(updatedPayment.getPaymentId());

        if (result.isPresent()) {

            Payment payment = result.get();

            payment.setSubscriptionId(
                    updatedPayment.getSubscriptionId()
            );

            payment.setDueDate(
                    updatedPayment.getDueDate()
            );

            payment.setPaymentDate(
                    updatedPayment.getPaymentDate()
            );

            payment.setPaymentType(
                    updatedPayment.getPaymentType()
            );

            payment.setStatus(
                    updatedPayment.getStatus()
            );

            return true;
        }

        return false;
    }

    public boolean delete(String id) {

        return payments.removeIf(
                payment -> payment.getPaymentId().equals(id)
        );
    }


    public List<Payment> findUnpaidBySubscription(
            String subscriptionId) {

        return payments.stream()

                .filter(payment ->
                        payment.getSubscriptionId()
                                .equals(subscriptionId)
                )

                .filter(payment ->
                        payment.getStatus() == PaymentStatus.UNPAID
                                || payment.getStatus() == PaymentStatus.LATE
                )

                .collect(Collectors.toList());
    }


    public List<Payment> findLastPayments() {

        return payments.stream()

                .sorted(
                        Comparator
                                .comparing(Payment::getDueDate)
                                .reversed()
                )

                .limit(5)

                .collect(Collectors.toList());
    }
}