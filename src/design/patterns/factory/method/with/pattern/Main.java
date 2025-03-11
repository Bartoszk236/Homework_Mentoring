package src.design.patterns.factory.method.with.pattern;

import static src.design.patterns.factory.method.with.pattern.ReportType.*;

public class Main {
    public static void main(String[] args) {
        Report report = ReportFactory.getReport(SALES);
        report.generateReport().displayReport();
    }
}
