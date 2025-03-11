package src.design.patterns.factory.method.with.pattern;

public class ProductionReport extends Report {

    @Override
    Report generateReport() {
        this.reportDescription = "Production is too small";
        return this;
    }
}
