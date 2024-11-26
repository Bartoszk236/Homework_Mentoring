package src.staticandunstaticmethodandvariables.tasks.task20;

public class Main {
    public static void main(String[] args) {
        Library library = new Library(201);

        System.out.println(Library.getCount()); // zmienna statyczna żyje tak długa jak działa program
        System.out.println(library); // zmienne niestatyczne żyją tyle co obiekt

        library = null; //usuwam refenrencje do obiektu, aby garbage collector się nim zainteresował
        System.gc();

        try { // czekam dwie sekundy aby miał czas zadziałać
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Library.getCount()); // zmienna statyczna jest dostępna nawet po usunięciu obiektu
        System.out.println(library);
    }
}