package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class JacobiIterationLinearEquationSolver<T extends Number> implements LinearEquationSolver<T>{

    private Matrix<T> matrix;
    List<T> b = new ArrayList<>();
    private int iterationsCount;

    public JacobiIterationLinearEquationSolver(int iterationsCount) {
        if (iterationsCount <= 0)
            throw new IllegalArgumentException();
        this.iterationsCount = iterationsCount;
    }

    public int getIterationsCount() {
        return iterationsCount;
    }

    @Override
    public List<Double> solve(Matrix<T> matrix, List<Double> b) {
        if (!matrix.isSquare())
            throw new IllegalArgumentException();
        if (matrix.getMatrixDimensionM() != b.size())
            throw new IllegalArgumentException();

        int n = matrix.getMatrixDimensionN();

        List<Double> result = new ArrayList<>(n);

        for (int i = 0; i < n; ++i){
            result.add(b.get(i) / matrix.get(i, i).doubleValue());
        }

        for (int k = 1; k < iterationsCount; ++k){
            List<Double> x = new ArrayList<>(n);
            for (int i = 0; i < n; ++i){
                double sum = 0;
                for (int j = 0; j < n; ++j){
                    if (i == j)
                        continue;
                    sum += matrix.get(i, j).doubleValue() * result.get(j);
                }
                x.add((b.get(i) - sum) / matrix.get(i, i).doubleValue());
            }
            result = x;
        }

        return result;
    }

    @Override
    public String toString() {
        return "Работает и заебись!";
    }
}
