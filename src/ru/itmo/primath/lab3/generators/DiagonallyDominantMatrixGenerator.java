package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.Random;

public class DiagonallyDominantMatrixGenerator implements MatrixGenerator {
    private int k;

    public DiagonallyDominantMatrixGenerator(int k) {
        this.k = k;
    }

    @Override
    public Matrix generate(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        Matrix matrix = new ArrayMatrix(n);
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double elem = r.nextInt(-4, 1);
                sum += elem;
                matrix.set(elem, i, j);
            }
        }
        for (int i = 0; i < n; ++i) {
            double temp = 0;
            for (int k = 0; k < n; ++k) {
                if (k == i)
                    continue;
                temp += matrix.get(i, k) + matrix.get(k, i);
            }
            matrix.set(-temp + Math.pow(10, -k), i, i);
        }
//        matrix.set((double) Double.valueOf(Math.pow(10, -k)), n - 1, n - 1);

        return matrix;
    }
}
