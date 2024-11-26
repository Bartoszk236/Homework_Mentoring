package src.staticandunstaticmethodandvariables.tasks.task10;

public class StaticExample {
    public static void printHello(){
        System.out.println("Hello World!");
//        printInstance(); nie można wywołać metody niestatycznej wewnątrz mietody statycznej. nie można tak zrobić ponieważ metody niestatyczne zależą od stanu obiektu, jeśli chcemy posiadać metody statyczną - czyli nie zależną od stanu obiektu to nie możemy w środku jej implementacji wywołać metodę która jest zależna od obiektu klasy.
    }
    public void printInstance(){
    }
}