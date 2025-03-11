package src.design.patterns.factory.method.without.pattern;

public class Main {
    public static void main(String[] args) {
        generateReport(ReportType.PRODUCTION);
    }

    public static void generateReport(ReportType reportType) {
        switch (reportType) {
            case SALES:
                System.out.println("Sales report");
                break;
            case FINANCE:
                System.out.println("Report finance");
                break;
            case PRODUCTION:
                System.out.println("Report production");
                break;
        }
    }
}
