package src.design.patterns.template.method;

public class Email extends DataValidation {
    @Override
    public boolean checkData(String data) {
        return data.contains("@");
    }

    @Override
    public void displayTypeOfData(String data) {
        System.out.println("Typ danych: email");
    }
}
