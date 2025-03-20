package src.design.patterns.proxy;

public class ProxyVideo implements Video {
    private RealVideo realVideo;
    private String title;

    public ProxyVideo(String title) {
        this.title = title;
    }

    @Override
    public void play() {
        if (realVideo == null) {
            realVideo = new RealVideo(title);
        }
        realVideo.play();
    }
}
