package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.Random;

public class DiagonallyDominantMatrixGenerator implements MatrixGenerator{
    @Override
    public <T extends Number> Matrix<T> generate(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        Matrix<T> matrix = new ArrayMatrix<T>(n);
        Random r = new Random();
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                matrix.set((T) Double.valueOf(r.nextInt(-4, 1)), i, j);
            }
        }
        for (int i = 0; i < n; ++i){
            double sum = 0;
            for (int k = 0; k < n; ++k){
                if (k == i)
                    continue;
                sum += matrix.get(i, k).doubleValue();
            }
            matrix.set((T) Double.valueOf(sum), i, i);
        }

        return matrix;
    }
}
