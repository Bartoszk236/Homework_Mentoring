package src.design.patterns.template.method;

public class Main {
    public static void main(String[] args) {
        DataValidation email = new Email();
        email.validate("email@gmail.com");

        DataValidation phoneNumber = new PhoneNumber();
        phoneNumber.validate("123456789");

        DataValidation zipCode = new ZipCode();
        zipCode.validate("12-345");
    }
}
