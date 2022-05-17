package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.generators.DiagonallyDominantMatrixGenerator;
import ru.itmo.primath.lab3.generators.HilbertMatrixGenerator;
import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.invertible.InvertMatrix;
import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;
import ru.itmo.primath.lab3.solvers.LinearEquationsSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Here we go again");
        /*List<Integer> data = Arrays.asList(1, 1, 1, 3, 2);
        List<Integer> indices = Arrays.asList(0, 2, 1, 0, 1);
        List<Integer> indPtr = Arrays.asList(1, 3, 4, 6);
        CSRMatrix2<Integer> csrMatrix = new CSRMatrix2<>(3, 3, 0, data, indices, indPtr);
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(csrMatrix.get(i, j) + " ");
            }
            System.out.println();
        }*/

        ArrayMatrix<Integer> arrayMatrix = new ArrayMatrix<>(new Integer[][]{
                {1, 1, 5, 0},
                {8, 1, 0, 0},
                {0, 3, 3, 1},
                {1, 15, 5, 4}
        });
        CSRMatrix<Integer> csrMatrix = new CSRMatrix<>(arrayMatrix, 0);
        csrMatrix.print();

        LUDecomposition<Integer> luDecomposition = new LUDecomposition<>(csrMatrix, 0, 1);
        luDecomposition.decompose();
        Matrix<Double> l = luDecomposition.getlMatrix();
        Matrix<Double> u = luDecomposition.getuMatrix();

        System.out.println("l");
        l.print();

        System.out.println("u");
        u.print();

        System.out.println("Check1: ");
        Matrix<Double> checkMatrix = l.multiply(u);
        checkMatrix.print();

        InvertMatrix<Integer> invert = new InvertMatrix<>(csrMatrix, 0, 1);
        invert.invert();
        Matrix<Double> invertMatrix = invert.getInvertibleMatrix();

        System.out.println("invert: ");
        invertMatrix.print();

        Matrix<Double> checkMatrix1 = csrMatrix.multiply(invertMatrix);

        System.out.println("Check2: ");
        checkMatrix1.print();

        LinearEquationsSolver<Integer> equationsSolver = new LinearEquationsSolver<>(csrMatrix, Arrays.asList(6,2,14,1), 0, 1);

        List<Double> ans = equationsSolver.solve();
        System.out.println(ans);

        List<MatrixGenerator> matrixGenerators = new ArrayList<>();
        matrixGenerators.add(new DiagonallyDominantMatrixGenerator(1));
        matrixGenerators.add(new HilbertMatrixGenerator());
        matrixGenerators.forEach(matrixGenerator -> {
            Matrix<Integer> m = matrixGenerator.generate(8);
            System.out.println("generated with " + matrixGenerator.getClass().getSimpleName() + ":");
            m.print(3);
        });
    }
}
