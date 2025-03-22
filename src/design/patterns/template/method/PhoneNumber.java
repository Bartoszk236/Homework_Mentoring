package src.design.patterns.template.method;

public class PhoneNumber extends DataValidation {
    @Override
    public boolean checkData(String data) {
        int length = data.length();
        if (length != 9) return false;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(data.charAt(i))) return false;
        }
        return true;
    }

    @Override
    public void displayTypeOfData(String data) {
        System.out.println("Typ danych: numer telefonu");
    }
}
