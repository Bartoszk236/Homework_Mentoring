package src.design.patterns.factory.method.with.pattern;

public final class ReportFactory {
    private ReportFactory() {

    }
    public static Report getReport(ReportType reportType) {
        return switch (reportType) {
            case PRODUCTION -> new ProductionReport();
            case SALES -> new SalesReport();
            case FINANCE -> new FinanseReport();
            default -> throw new IllegalArgumentException("Invalid report type: " + reportType);
        };
    }
}
