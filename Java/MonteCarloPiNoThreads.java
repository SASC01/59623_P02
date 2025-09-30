import java.util.Random;

public class MonteCarloPiNoThreads {

    public static void main(String[] args) {
        int totalSamples = 1_000_000;
        long countInsideCircle = 0;
        Random random = new Random();

        // Generar todos los puntos en un solo hilo (sin concurrencia)
        for (int i = 0; i < totalSamples; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1.0) {
                countInsideCircle++;
            }
        }

        // Calcular aproximación de π
        double piApprox = (4.0 * countInsideCircle) / totalSamples;

        System.out.println("Número total de puntos: " + totalSamples);
        System.out.println("Puntos dentro del círculo: " + countInsideCircle);
        System.out.println("Aproximación de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
    }
}
