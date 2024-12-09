package src.dynamic.polymorphism.task22;

public class EmailNotification extends Notification {
    private String subject;
    private String body;
    private String email;

    public EmailNotification(String body, String email, String subject) {
        this.body = body;
        this.email = email;
        this.subject = subject;
    }

    @Override
    void send() {
        System.out.println("Email was sent to " + email + "\nsubject: " + subject + "\nbody: " + body);
    }
}
