import java.util.Random;
import java.util.Arrays;

public class SimdDemo {
    public static void main(String[] args) {
        // Tamaño de los arrays (10 millones de elementos)
        int n = 10_000_000;
        
        // 1. Crear arrays de números aleatorios
        System.out.println("Creando arrays...");
        float[] a = new float[n];
        float[] b = new float[n];
        float[] c = new float[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            a[i] = rand.nextFloat();  // [0,1)
            b[i] = rand.nextFloat();
        }

        // 2. Operación SCALAR (no SIMD) - como referencia
        System.out.println("Ejecutando operación escalar...");
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            c[i] = a[i] * b[i] + 2.0f;
        }
        double scalarTime = (System.nanoTime() - start) / 1e9;
        System.out.printf("Tiempo escalar: %.4f segundos%n", scalarTime);

        // 3. Operación VECTORIAL (SIMD "simulada")
        System.out.println("Ejecutando operación vectorial SIMD...");
        start = System.nanoTime();
        float[] cSimd = new float[n];
        for (int i = 0; i < n; i++) {
            cSimd[i] = a[i] * b[i] + 2.0f;
        }
        double simdTime = (System.nanoTime() - start) / 1e9;
        System.out.printf("Tiempo SIMD: %.4f segundos%n", simdTime);

        // 4. Verificar que los resultados son iguales
        boolean iguales = true;
        for (int i = 0; i < n; i++) {
            if (Math.abs(c[i] - cSimd[i]) > 1e-6) {
                iguales = false;
                break;
            }
        }
        System.out.println("Resultados iguales: " + iguales);
        System.out.printf("Speedup: %.2fx%n", scalarTime / simdTime);

        // 5. Más operaciones SIMD comunes
        System.out.println("\nOtras operaciones SIMD:");

        // Suma de todos los elementos
        double suma = 0.0;
        for (float v : a) suma += v;
        System.out.printf("Suma de a: %.4f%n", suma);

        // Operaciones trigonométricas vectorizadas
