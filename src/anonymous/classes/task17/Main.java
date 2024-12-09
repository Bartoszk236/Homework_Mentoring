package src.anonymous.classes.task17;

public class Main {
    public static void main(String[] args) {
        Button button = new Button();

        button.setClickListener(new Button.ClickListener() {
            @Override
            public void onClick() {
                System.out.println("Program 1");
            }
        });

        button.click();

        button.setClickListener(new Button.ClickListener() {
            @Override
            public void onClick() {
                System.out.println("Program 2");
            }
        });

        button.click();
    }
}