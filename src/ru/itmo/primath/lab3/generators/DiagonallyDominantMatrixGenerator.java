package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.Random;

public class DiagonallyDominantMatrixGenerator implements MatrixGenerator{
    private int k;

    public DiagonallyDominantMatrixGenerator(int k) {
        this.k = k;
    }

    @Override
    public <T extends Number> Matrix<T> generate(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        Matrix<T> matrix = new ArrayMatrix<T>(n);
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                T elem = (T) Double.valueOf(r.nextInt(-4, 1));
                sum += elem.doubleValue();
                matrix.set(elem, i, j);
            }
        }
        for (int i = 0; i < n; ++i){
            matrix.set((T) Double.valueOf(-sum + Math.pow(10, -k)), i, i);
        }

        return matrix;
    }
}
