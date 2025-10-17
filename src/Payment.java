// Interface for payment processing
public interface Payment {
    void processPayment(double amount);
    String getPaymentDetails();
}