package src.design.patterns.facade;

public class SecurityConfigFacade {
    private final AirRadar airRadar;
    private final MainGate mainGate;
    private final SecurityAlarm securityAlarm;

    public SecurityConfigFacade(AirRadar airRadar, MainGate mainGate, SecurityAlarm securityAlarm) {
        this.airRadar = airRadar;
        this.mainGate = mainGate;
        this.securityAlarm = securityAlarm;
    }

    public void armament() {
        airRadar.on();
        mainGate.close();
        securityAlarm.on();
        System.out.println("Base is protected");
    }

    public void disarmament() {
        airRadar.off();
        mainGate.open();
        securityAlarm.off();
        System.out.println("Base is unprotected");
    }
}
