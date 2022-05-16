package ru.itmo.primath.lab3.invertible;

import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class InvertMatrix<T extends Number> {
    private T zero;
    private T identityElement; // 1
    private Matrix<T> matrix;
    private ArrayMatrix<Double> invertibleMatrix;

    public InvertMatrix(Matrix<T> matrix, T zero, T identityElement){
        this.matrix = matrix;
        this.zero = zero;
        this.identityElement = identityElement;
    }

    public void invert(){
        int n = matrix.getMatrixDimensionN();
        invertibleMatrix = new ArrayMatrix<>(n);

        LUDecomposition<T> luDecomposition = new LUDecomposition<>(matrix, zero, identityElement);

        luDecomposition.decompose();

        Matrix<Double> l = luDecomposition.getlMatrix();
        Matrix<Double> u = luDecomposition.getuMatrix();

        invertibleMatrix.set(1d / u.get(n - 1, n - 1), n - 1, n - 1);
        for (int i = n - 1; i >= 0; --i){
            for (int j = n - 1; j >= 0; --j){
                if (i == n - 1 && j == n - 1)
                    continue;
                if (i < j){
                    double temp = 0;
                    for (int k = i + 1; k < n; ++k){
                        temp += u.get(i, k) * invertibleMatrix.get(k ,j);
                    }
                    invertibleMatrix.set(-1 / u.get(i, i) * temp, i, j);
                }
                if (i == j){
                    double temp = 0;
                    for (int k = i + 1; k < n; ++k){
                        temp += u.get(i, k) * invertibleMatrix.get(k, j);
                    }
                    invertibleMatrix.set(1 / u.get(i, i) * (1 - temp), i, j);
                }
                if (j < i){
                    double temp = 0;
                    for (int k = j + 1; k < n; ++k){
                        temp += invertibleMatrix.get(i, k) * l.get(k, j);
                    }
                    invertibleMatrix.set(-temp, i, j);
                }
            }
        }
    }

    public ArrayMatrix<Double> getInvertibleMatrix() {
        return invertibleMatrix;
    }
}
