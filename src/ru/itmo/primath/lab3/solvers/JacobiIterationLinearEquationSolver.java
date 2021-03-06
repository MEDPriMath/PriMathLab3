package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.markdown.description.DescriptionStorage;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class JacobiIterationLinearEquationSolver implements LinearEquationSolver {

    List<Double> b = new ArrayList<>();
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
    public List<Double> solve(Matrix matrix, List<Double> b) {
        if (!matrix.isSquare())
            throw new IllegalArgumentException();
        if (matrix.getMatrixDimensionM() != b.size())
            throw new IllegalArgumentException();

        int n = matrix.getMatrixDimensionN();

        List<Double> result = new ArrayList<>(n);

        for (int i = 0; i < n; ++i) {
            result.add(b.get(i) / matrix.get(i, i));
        }

        for (int k = 1; k < iterationsCount; ++k) {
            List<Double> x = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                double sum = 0;
                for (int j = 0; j < n; ++j) {
                    if (i == j)
                        continue;
                    sum += matrix.get(i, j) * result.get(j);
                }
                x.add((b.get(i) - sum) / matrix.get(i, i));
            }
            result = x;
        }

        return result;
    }

    @Override
    public String toString() {
        return DescriptionStorage.jacobiDescription;
    }
}
