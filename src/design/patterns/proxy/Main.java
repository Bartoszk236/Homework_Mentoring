package src.design.patterns.proxy;

public class Main {
    public static void main(String[] args) {
        Video video = new ProxyVideo("Iron Man");
        System.out.println("Film nie został załadowany");
        video.play();
        video.play();
    }
}
