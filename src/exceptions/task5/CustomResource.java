package src.exceptions.task5;

public class CustomResource implements AutoCloseable {
    private final String resourceName;

    public CustomResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    @Override
    public void close() throws Exception {
        throw new Exception("Custom Resource closed: " + resourceName);
    }

    public void run() {
        System.out.println("Running " + resourceName);
    }
}