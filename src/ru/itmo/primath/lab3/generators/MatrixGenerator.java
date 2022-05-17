package ru.itmo.primath.lab3.generators;

import ru.itmo.primath.lab3.matrix.Matrix;

public interface MatrixGenerator {
    <T extends Number>Matrix<T> generate(int n);
}
