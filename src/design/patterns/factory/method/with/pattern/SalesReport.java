package src.design.patterns.factory.method.with.pattern;

public class SalesReport extends Report {

    @Override
    Report generateReport() {
        this.reportDescription = "We have 3% more sales then last month";
        return this;
    }
}
