package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.List;

public interface LinearEquationSolver<T extends Number> {
    List<Double> solve(Matrix<T> matrix, List<T> b);
}
