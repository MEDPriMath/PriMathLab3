package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.Random;
import java.util.function.Supplier;

public class RandomCSRMatrixGenerator implements MatrixGenerator {

    private double k;
    private Supplier<Double> random;
    private Random indexGenerator;

    public RandomCSRMatrixGenerator(double k, Supplier<Double> random) {
        if (k <= 0)
            throw new IllegalArgumentException();
        this.k = k;
        this.random = random;
        this.indexGenerator = new Random();
    }


    @Override
    public Matrix generate(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        int numberOfElements = (int) (k * n);
        Matrix matrix = Matrix.generateIdentityMatrix(n, 2 * n);

        for (int i = 0; i < numberOfElements; ++i) {
            if (i % 10000 == 0)
                System.out.println("i: " + i);
            double elem = random.get();
            matrix.set(elem, indexGenerator.nextInt(0, n), indexGenerator.nextInt(0, n));
        }

        return matrix;
    }
}
