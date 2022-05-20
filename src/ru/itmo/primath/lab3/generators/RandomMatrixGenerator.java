package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.function.Supplier;

public class RandomMatrixGenerator implements MatrixGenerator {

    private Supplier<Double> random;

    public RandomMatrixGenerator(Supplier<Double> random) {
        this.random = random;
    }

    @Override
    public Matrix generate(int n) {
        Matrix matrix = new ArrayMatrix(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double elem = random.get();
                matrix.set(elem, i, j);
            }
        }

        return matrix;
    }
}
