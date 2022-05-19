package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.function.Supplier;

public class RandomMatrixGenerator<T extends Number> implements MatrixGenerator {

    private Supplier<T> random;

    public RandomMatrixGenerator(Supplier<T> random) {
        this.random = random;
    }

    @Override
    public Matrix<T> generate(int n) {
        Matrix<T> matrix = new ArrayMatrix<T>(n);
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                T elem = random.get();
                matrix.set(elem, i, j);
            }
        }

        return matrix;
    }
}
