package com.andreychh.marksman.server;

public class Game {
    private enum State {
        START,
        PLAYING,
        PAUSED,
        STOP
    }

    private State state;
    private final Object lock = new Object();

    private final Environment environment;

    public Game(Environment environment) {
        this.environment = environment;
        this.state = State.START;
    }

    private void loop() {
        long lastTime = System.nanoTime();
        long targetTime = 1000000000 / 60;

        while (this.state != State.STOP) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            if (elapsedTime < targetTime) {
                try {
                    Thread.sleep((targetTime - elapsedTime) / 1000000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            lastTime = now;
            synchronized (lock) {
                while (this.state != State.PLAYING) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                this.environment.next();
            }
        }
    }

    public void start() {
        this.state = State.PLAYING;
        new Thread(this::loop).start();
    }

    public void stop() {
        synchronized (lock) {
            this.state = State.STOP;
            lock.notify();
        }
    }

    public void pause() {
        synchronized (lock) {
            this.state = State.PAUSED;
        }
    }

    public void resume() {
        synchronized (lock) {
            this.state = State.PLAYING;
            lock.notify();
        }
    }
}
