public class CashPayment implements Payment {
    private String receiptNumber;

    public CashPayment(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount);
        System.out.println("Receipt Number: " + receiptNumber);
        System.out.println("Payment received!");
    }

    @Override
    public String getPaymentDetails() {
        return "Cash Payment - Receipt #" + receiptNumber;
    }
}