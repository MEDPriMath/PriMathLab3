package ru.itmo.primath.lab3.invertible;

import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class InvertMatrix {
    private Matrix matrix;
    private ArrayMatrix invertibleMatrix;

    public InvertMatrix(Matrix matrix) {
        if (!matrix.isSquare())
            throw new IllegalArgumentException();
        this.matrix = matrix;
    }

    public void invert() {
        int n = matrix.getMatrixDimensionN();
        invertibleMatrix = new ArrayMatrix(n);

        LUDecomposition luDecomposition = new LUDecomposition(matrix);

        luDecomposition.decompose();

        Matrix l = luDecomposition.L();
        Matrix u = luDecomposition.U();

        invertibleMatrix.set(1d / u.get(n - 1, n - 1), n - 1, n - 1);
        for (int i = n - 1; i >= 0; --i) {
            for (int j = n - 1; j >= 0; --j) {
                if (i == n - 1 && j == n - 1)
                    continue;
                if (i < j) {
                    double temp = 0;
                    for (int k = i + 1; k < n; ++k) {
                        temp += u.get(i, k) * invertibleMatrix.get(k, j);
                    }
                    invertibleMatrix.set(-1 / u.get(i, i) * temp, i, j);
                }
                if (i == j) {
                    double temp = 0;
                    for (int k = i + 1; k < n; ++k) {
                        temp += u.get(i, k) * invertibleMatrix.get(k, j);
                    }
                    invertibleMatrix.set(1 / u.get(i, i) * (1 - temp), i, j);
                }
                if (j < i) {
                    double temp = 0;
                    for (int k = j + 1; k < n; ++k) {
                        temp += invertibleMatrix.get(i, k) * l.get(k, j);
                    }
                    invertibleMatrix.set(-temp, i, j);
                }
            }
        }
    }

    public ArrayMatrix getInvertibleMatrix() {
        return invertibleMatrix;
    }
}
