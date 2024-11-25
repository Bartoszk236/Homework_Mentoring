package src.staticandunstaticmethodandvariables.tasks.task16;

public class Utility {
    private int number;
    public static void example(){
//        System.out.println(number - 1);
        // nie możemy użyć niestatycznej zmiennej w statycznej metodzie ponieważ statyczna metoda nie zraca uwagi na aktualny stan obiektu, przez co nie możemy opierać logiki metody która działa bez obeiktu na czymś co jest bezpośrednio powiązane z obeiktem. powoduje to błędy takie jak, gdy chcemy użyc metody statycznej a obiekt jeszcze nie istnieje rodziło by to wiele problemów. Przez co na stałe jest nie doszowlone stosowanie zmiennych niestatycznych w statycznych metodach.
    }
}
