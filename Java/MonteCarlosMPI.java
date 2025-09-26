import mpi.*;  // Librería de MPJ Express
import java.util.Random;

public class MonteCarloMPI {
    public static void main(String[] args) throws Exception {
        // 1. Inicializar el entorno MPI
        MPI.Init(args);
        Comm comm = MPI.COMM_WORLD;
        int rank = comm.Rank();  // Identificador único del proceso
        int size = comm.Size();  // Número total de procesos

        int totalSamples = 1_000_000;
        int samplesPerProcess = totalSamples / size;

        // 2. Cada proceso calcula su parte INDEPENDIENTE
        int localCount = 0;
        Random random = new Random(rank);  // Semilla diferente por proceso
        for (int i = 0; i < samplesPerProcess; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                localCount++;
            }
        }

        System.out.println("Proceso " + rank + ": calculó " + localCount + " puntos.");

        // 3. Recolectar todos los resultados en el proceso 0
        int[] localCounts = new int[]{localCount};
        int[] allCounts = new int[size];

        comm.Gather(localCounts, 0, 1, MPI.INT,
                    allCounts, 0, 1, MPI.INT, 0);

        // 4. El proceso coordinador (rank 0) calcula el resultado final
        if (rank == 0) {
            int globalCount = 0;
            for (int count : allCounts) {
                globalCount += count;
            }
            double piApprox = (4.0 * globalCount) / totalSamples;

            System.out.println("\nNúmero total de puntos: " + totalSamples);
            System.out.println("Puntos dentro del círculo: " + globalCount);
            System.out.println("Aproximación de pi: " + piApprox);
            System.o
