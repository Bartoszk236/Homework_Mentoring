package src.string.operation.task9;

public class Main {
    public static void main(String[] args) {
        String one = "hello";
        System.out.println(one == one.concat(" world"));
        //gdyby zmodyfikował stary obiekt, to pod starym adresem widniała by nowa wartość
    }
}