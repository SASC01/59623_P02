import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class MonteCarloPiSpeedup {

    private static long globalCount = 0; 
    private static final ReentrantLock lock = new ReentrantLock();

    static class MonteCarloTask implements Runnable {
        private final int numSamples;
        private final int threadId;
        private final Random random;

        public MonteCarloTask(int numSamples, int threadId) {
            this.numSamples = numSamples;
            this.threadId = threadId;
            this.random = new Random();
        }

        @Override
        public void run() {
            long localCount = 0;

            for (int i = 0; i < numSamples; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();

                if (x * x + y * y <= 1.0) {
                    localCount++;
                }
            }

            lock.lock();
            try {
                globalCount += localCount;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        int totalSamples = 10_000_000; // muestras
        int numThreads = 4;            // número de hilos
        int samplesPerThread = totalSamples / numThreads;

        // =========================
        // 1. Versión Secuencial
        // =========================
        long startSequential = System.currentTimeMillis();
        long countInsideCircle = 0;
        Random random = new Random();

        for (int i = 0; i < totalSamples; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if
