package src.design.patterns.factory.abstractmethod.without.pattern;

import src.design.patterns.factory.abstractmethod.without.pattern.platforms.Linux;
import src.design.patterns.factory.abstractmethod.without.pattern.platforms.MacOS;
import src.design.patterns.factory.abstractmethod.without.pattern.platforms.OSType;
import src.design.patterns.factory.abstractmethod.without.pattern.platforms.Windows;

public class Main {
    public static void main(String[] args) {
        String osName = "Mac OS";
        OSType osType = chooseOsType(osName);
        osType.showButton();
        osType.showDialogWindow();
        osType.showButton();
    }

    public static OSType chooseOsType(String osName) {
        return switch (osName) {
            case "Linux" -> new Linux();
            case "Mac OS" -> new MacOS();
            case "Windows" -> new Windows();
            default -> throw new IllegalArgumentException("Unknown OS type: " + osName);
        };
    }
}
