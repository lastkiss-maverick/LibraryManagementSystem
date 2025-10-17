public class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card holder: " + cardHolder);
        System.out.println("Card: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Payment successful!");
    }

    @Override
    public String getPaymentDetails() {
        return "Credit Card - " + cardHolder;
    }
}