package ru.itmo.primath.lab3.solvers;

import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class LULinearEquationSolver<T extends Number> implements LinearEquationSolver<T> {
    private Matrix<T> matrix;
    List<T> b = new ArrayList<>();
    private T zero;
    private T identityElement;

    public LULinearEquationSolver(T zero, T identityElement) {
        this.zero = zero;
        this.identityElement = identityElement;
    }

    @Override
    public List<Double> solve(Matrix<T> matrix, List<Double> b){
        if (!matrix.isSquare())
            throw new IllegalArgumentException();
        if (matrix.getMatrixDimensionM() != b.size())
            throw new IllegalArgumentException();

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
        y.set(0, b.get(0));
        for (int i = 1; i < n; i++){
            double temp = 0d;
            for (int k = 0; k < i; ++k){
                temp += y.get(k) * l.get(i, k);
            }
            y.set(i, b.get(i) - temp);
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

    @Override
    public String toString() {
        return "Решим СЛАУ вида A∙x=b\n" +
                "Разложим матрицу A на L и U матрицы\n" +
                "L∙U∙x=b\n" +
                "Обозначит U∙x=y\n" +
                "Решим последовательно\n" +
                "L∙y=b\n" +
                "U∙x=y\n" +
                "\n" +
                "$$l\\_{11} \\cdot y\\_{1}=b\\_{1}$$\n" +
                "\n" +
                "$$l\\_{21} y\\_{1}+l\\_{22}y\\_{2}=b\\_{2}$$\n" +
                "\n" +
                "$$l\\_{31} y\\_{1}+l\\_{32}y\\_{2}+l\\_{33}y\\_{3}=b\\_{3}$$\n" +
                "\n" +
                "$$\\sum\\_{i=1}^{k}(l\\_{ki}y\\_{i})=b\\_{k}$$\n" +
                "\n" +
                "Так как $$l\\_{ii}=1$$\n" +
                "\n" +
                "$$y\\_{k}=b\\_{k}-\\sum\\_{i=1}^{k-1}(l\\_{ki}y\\_{i})$$\n" +
                "\n" +
                "---\n" +
                "\n" +
                "$$u\\_{nn} \\cdot x\\_{n}=y\\_{n}$$\n" +
                "\n" +
                "$$u\\_{n-1,n} x\\_{n}+u\\_{n-1,n-1}x\\_{n-1}=y\\_{n-1}$$\n" +
                "\n" +
                "$$u\\_{n-2,n} x\\_{n}+u\\_{n-2,n-1}x\\_{n-1}+u\\_{n-2,n}x\\_{n}=y\\_{n-2}$$\n" +
                "\n" +
                "$$\\sum\\_{i=k}^{n}(u\\_{ki}x\\_{i})=y\\_{k}$$\n" +
                "\n" +
                "$$x\\_{k}=\\frac{\\sum\\_{i=k+1}^{n}(u\\_{ki}x\\_{i})}{u\\_{kk}}$$ ";
    }
}
