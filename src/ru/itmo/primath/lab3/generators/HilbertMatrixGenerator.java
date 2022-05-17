package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class HilbertMatrixGenerator implements MatrixGenerator{
    @Override
    public <T extends Number> Matrix<T> generate(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        Matrix<T> matrix = new ArrayMatrix<T>(n);
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                T elem = (T) Double.valueOf(1d / (i + j + 1));
                matrix.set(elem, i, j);
            }
        }

        return matrix;
    }
}
