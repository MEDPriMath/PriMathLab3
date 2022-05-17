package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class LinearEquationsSolver<T extends Number> {
    private Matrix<T> matrix;
    List<T> b = new ArrayList<>();
    private T zero;
    private T identityElement;

    public LinearEquationsSolver(Matrix<T> matrix, List<T> b, T zero, T identityElement) {
        if (!matrix.isSquare())
            throw new IllegalArgumentException();
        if (matrix.getMatrixDimensionM() != b.size())
            throw new IllegalArgumentException();
        this.matrix = matrix;
        this.b = new ArrayList<>(b);
        this.zero = zero;
        this.identityElement = identityElement;
    }

    public List<Double> solve(){
        int n = matrix.getMatrixDimensionN();

        LUDecomposition<T> luDecomposition = new LUDecomposition<>(matrix, zero, identityElement);

        luDecomposition.decompose();

        Matrix<Double> l = luDecomposition.getlMatrix();
        Matrix<Double> u = luDecomposition.getuMatrix();

        List<Double> result = new ArrayList<>(n);
        List<Double> y = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            result.add(0d);
            y.add(0d);
        }
        y.set(0, b.get(0).doubleValue());
        for (int i = 1; i < n; i++){
            double temp = 0d;
            for (int k = 0; k < i; ++k){
                temp += y.get(k) * l.get(i, k);
            }
            y.set(i, b.get(i).doubleValue() - temp);
        }
        result.set(n - 1, y.get(n - 1) / u.get(n - 1, n - 1));
        for (int i = n - 2; i >= 0; --i){
            double temp = 0d;
            for (int k = i + 1; k < n; ++k){
                temp += result.get(k) * u.get(i, k);
            }
            result.set(i, (y.get(i) - temp) / u.get(i, i));
        }

        return result;
    }
}
