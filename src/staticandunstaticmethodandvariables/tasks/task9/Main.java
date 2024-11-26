package src.staticandunstaticmethodandvariables.tasks.task9;

public class Main {
    public static void main(String[] args) {
        Employee.companyName = "Intermediate";
        System.out.println(Employee.companyName); // dostęp do zmiennej statycznej jest dostępny bezpośrednio z klasy bez konieczności wcześniejszego stworzenia obiektu
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employee1.employeeName = "Mariusz"; // dostęp do zmiennch niestatycznych jest dopiero po utworzeniu obiektu
        employee2.employeeName = "Dariusz";
        System.out.println(employee1); // podczas wyświetlana zmienna statyczna jest wspólna dla wszystkich obiektów tej klasy
        System.out.println(employee2);
    }
}
