package marksman.server;

public final class Main {
    public static void main(final String[] args) {
        Application application = new Application(12345);
        application.start();
    }
}
