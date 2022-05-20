package ru.itmo.primath.lab3.lu;

import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class LUDecomposition {

    private Matrix matrix;

    private Matrix lMatrix;
    private Matrix uMatrix;

    public LUDecomposition(Matrix matrix) {
        if (!matrix.isSquare())
            throw new IllegalArgumentException();

        this.matrix = matrix;
    }

    public void decompose() {
        int n = matrix.getMatrixDimensionM();
        Matrix lMatrix = new CSRMatrix(n);
        Matrix uMatrix = new CSRMatrix(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                uMatrix.set(0, i, j);
                lMatrix.set(0, i, j);
            }
        }
        for (int i = 0; i < n; ++i) {
            lMatrix.set(1, i, i);
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i <= j) {
                    double temp = 0;
                    for (int k = 0; k < i; ++k) {
                        temp += lMatrix.get(i, k) * uMatrix.get(k, j);
                    }
                    uMatrix.set(matrix.get(i, j) - temp, i, j);
                }
                if (i > j) {
                    double temp = 0;
                    for (int k = 0; k < j; ++k) {
                        temp += lMatrix.get(i, k) * uMatrix.get(k, j);
                    }
                    lMatrix.set((matrix.get(i, j) - temp) / uMatrix.get(j, j), i, j);
                }
            }
        }

        this.lMatrix = lMatrix;
        this.uMatrix = uMatrix;
    }

    public Matrix L() {
        return lMatrix;
    }

    public Matrix U() {
        return uMatrix;
    }
}
