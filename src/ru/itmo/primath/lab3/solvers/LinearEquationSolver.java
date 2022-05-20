package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.List;

public interface LinearEquationSolver {
    List<Double> solve(Matrix matrix, List<Double> b);
}
