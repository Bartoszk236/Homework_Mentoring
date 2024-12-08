package src.anonymous.classes.task17;

public class Button {
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void click() {
        clickListener.onClick();
    }

    interface ClickListener {
        void onClick();
    }
}