package src.design.patterns.template.method;

public class ZipCode extends DataValidation {
    @Override
    public boolean checkData(String data) {
        if (data.contains("-")) data = data.replace("-", "");
        else return false;
        int length = data.length();
        if (length != 5) return false;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(data.charAt(i))) return false;
        }
        return true;
    }

    @Override
    public void displayTypeOfData(String data) {
        System.out.println("Typ danych: kod pocztowy");
    }
}
