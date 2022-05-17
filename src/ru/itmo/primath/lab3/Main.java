package ru.itmo.primath.lab3;

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
                {1, 1},
                {8, 1},
        });
        CSRMatrix<Integer> csrMatrix = new CSRMatrix<>(arrayMatrix, 0);
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(csrMatrix.get(i, j) + " ");
            }
            System.out.println();
        }

        LUDecomposition<Integer> luDecomposition = new LUDecomposition<>(csrMatrix, 0, 1);
        luDecomposition.decompose();
        Matrix<Double> l = luDecomposition.getlMatrix();
        Matrix<Double> u = luDecomposition.getuMatrix();

        System.out.println("l");
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(l.get(i, j) + " ");
            }
            System.out.println();
        }

        System.out.println("u");
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(u.get(i, j) + " ");
            }
            System.out.println();
        }

        System.out.println("Check1: ");
        Matrix<Double> checkMatrix = l.multiply(u);
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(checkMatrix.get(i, j) + " ");
            }
            System.out.println();
        }

        InvertMatrix<Integer> invert = new InvertMatrix<>(csrMatrix, 0, 1);
        invert.invert();
        Matrix<Double> invertMatrix = invert.getInvertibleMatrix();

        System.out.println("invert: ");
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(invertMatrix.get(i, j) + " ");
            }
            System.out.println();
        }

        Matrix<Double> checkMatrix1 = csrMatrix.multiply(invertMatrix);

        System.out.println("Check2: ");
        for (int i = 0; i < csrMatrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix.getMatrixDimensionN(); ++j){
                System.out.print(checkMatrix1.get(i, j) + " ");
            }
            System.out.println();
        }

        LinearEquationsSolver<Integer> equationsSolver = new LinearEquationsSolver<>(csrMatrix, Arrays.asList(1,2), 0, 1);

        List<Double> ans = equationsSolver.solve();
        System.out.println(ans);
    }
}
