package src.dynamic.polymorphism.task22;

public class Main {
    public static void main(String[] args) {
        Notification notification1 = new SMSNotification("123456789");
        Notification notification2 = new EmailNotification("I need help with new endpoint", "darek@gmail.com", "Help me");

        notification1.send();
        notification2.send();
    }
}