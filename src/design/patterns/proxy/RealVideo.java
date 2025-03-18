package src.design.patterns.proxy;

public class RealVideo implements Video {
    private final String title;

    public RealVideo(String title) {
        this.title = title;
        loadVideo();
    }

    @Override
    public void play() {
        System.out.println("Odtwarzanie filmu");
    }

    private void loadVideo() {
        System.out.println("Ładowanie filmu");
    }
}
