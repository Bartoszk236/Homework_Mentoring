package src.design.patterns.factory.method.with.pattern;

public class FinanseReport extends Report {

    @Override
    Report generateReport() {
        this.reportDescription = "Finanse Report description";
        return this;
    }
}
