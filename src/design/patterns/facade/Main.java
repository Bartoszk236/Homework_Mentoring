package src.design.patterns.facade;

public class Main {
    public static void main(String[] args) {
        AirRadar airRadar = new AirRadar();
        MainGate mainGate = new MainGate();
        SecurityAlarm securityAlarm = new SecurityAlarm();

        SecurityConfigFacade securityConfig = new SecurityConfigFacade(airRadar, mainGate, securityAlarm);
        securityConfig.armament();
    }
}
