package src.design.patterns.template.method;

public abstract class DataValidation {
    public final void validate(String data) {
        downloadData(data);
        displayTypeOfData(data);
        showResult(checkData(data));
    }

    public abstract boolean checkData(String data);

    public abstract void displayTypeOfData(String data);

    public void downloadData(String data) {
        System.out.println("Poprawnie pobrano: " + data);
    }

    public void showResult(Boolean result) {
        if (result) {
            System.out.println("Dane są prawidłowe");
        } else {
            System.out.println("Dane są nieprawidłowe");
        }
    }
}
