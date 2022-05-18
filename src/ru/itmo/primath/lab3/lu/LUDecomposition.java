package ru.itmo.primath.lab3.lu;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

public class LUDecomposition<T extends Number> {

    private T zero;
    private T identityElement; // 1
    private Matrix<T> matrix;

    private Matrix<Double> lMatrix;
    private Matrix<Double> uMatrix;


    public LUDecomposition(Matrix<T> matrix, T zero, T identityElement){
        if (!matrix.isSquare())
            throw new IllegalArgumentException();

        this.matrix = matrix;
        this.zero = zero;
        this.identityElement = identityElement;
    }

    public void decompose(){
        int n = matrix.getMatrixDimensionM();
        Matrix<Double> lMatrix = new CSRMatrix<>(n, zero.doubleValue());
        Matrix<Double> uMatrix = new CSRMatrix<>(n, zero.doubleValue());
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                uMatrix.set(zero.doubleValue(), i, j);
                lMatrix.set(zero.doubleValue(), i, j);
            }
        }
        for (int i = 0; i < n; ++i){
            lMatrix.set(identityElement.doubleValue(), i, i);
        }

        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                if (i <= j){
                    double temp = zero.doubleValue();
                    for (int k = 0; k < i; ++k){
                        temp += lMatrix.get(i, k) * uMatrix.get(k, j);
                    }
                    uMatrix.set(matrix.get(i, j).doubleValue() - temp, i, j);
                }
                if (i > j){
                    double temp = zero.doubleValue();
                    for (int k = 0; k < j; ++k){
                        temp += lMatrix.get(i, k) * uMatrix.get(k, j);
                    }
                    lMatrix.set((matrix.get(i, j).doubleValue() - temp) / uMatrix.get(j, j), i, j);
                }
            }
        }

        this.lMatrix = lMatrix;
        this.uMatrix = uMatrix;
    }

    public Matrix<Double> getlMatrix() {
        return lMatrix;
    }

    public Matrix<Double> getuMatrix() {
        return uMatrix;
    }
}
