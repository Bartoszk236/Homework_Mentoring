package src.nested.classes.task16;

public class Computer {
    private Processor processor;
    private String name;

    public Computer(String name, String processorName, String clocking) {
        this.name = name;
        this.processor = new Processor(processorName, clocking);
    }

    @Override
    public String toString() {
        return "name: " + name + "\n" + processor;
    }

    private class Processor {
        private String processorName;
        private String clocking;

        public Processor(String processorName, String clocking) {
            this.processorName = processorName;
            this.clocking = clocking;
        }

        @Override
        public String toString() {
            return "processorName: " + processorName + ", clocking: " + clocking;
        }
    }
}