package src.design.patterns.factory.method.with.pattern;

import java.time.LocalDateTime;

public abstract class Report {
    protected LocalDateTime createTime;
    protected String reportDescription;

    public Report() {
        this.createTime = LocalDateTime.now();
    }

    abstract Report generateReport();

    public void displayReport() {
        System.out.println(this.getClass().getSimpleName() + ":");
        System.out.println("Create Time: " + this.createTime);
        System.out.println("Description: " + this.reportDescription);
    }
}
