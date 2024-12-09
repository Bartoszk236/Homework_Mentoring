package src.dynamic.polymorphism.task22;

public class SMSNotification extends Notification {
    private String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    void send() {
        System.out.println("SMS was sent to " + phoneNumber);
    }
}