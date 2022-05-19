package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RandomCSRMatrixGenerator<T extends Number> implements MatrixGenerator<T>{

    private int k;
    private Supplier<T> random;
    private Random indexGenerator;

    public RandomCSRMatrixGenerator(int k, Supplier<T> random) {
        if (k <= 0)
            throw new IllegalArgumentException();
        this.k = k;
        this.random = random;
        this.indexGenerator = new Random();
    }


    @Override
    public Matrix<T> generate(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        int numberOfElements = k * n;
        Matrix<T> matrix = new CSRMatrix<>(n);
        for (int i = 0; i < n; ++i){
            matrix.set((T)Double.valueOf(n), i, i);
        }

        for (int i = 0; i < numberOfElements; ++i){
            T elem = random.get();
            matrix.set(elem, indexGenerator.nextInt(0, n), indexGenerator.nextInt(0, n));
        }

        return matrix;
    }
}
