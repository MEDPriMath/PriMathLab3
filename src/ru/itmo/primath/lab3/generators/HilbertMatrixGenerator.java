package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class HilbertMatrixGenerator implements MatrixGenerator {
    @Override
    public Matrix generate(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        Matrix matrix = new ArrayMatrix(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double elem = 1d / (i + j + 1);
                matrix.set(elem, i, j);
            }
        }

        return matrix;
    }
}
