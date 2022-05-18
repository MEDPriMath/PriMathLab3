package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.markdown.MarkdownDocument;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.List;

public class LUCheck {
    private List<MatrixGenerator> matrixGenerators;

    public LUCheck(List<MatrixGenerator> matrixGenerators) {
        this.matrixGenerators = matrixGenerators;
    }

    public void Check(int matrixSize){
        matrixGenerators.forEach(matrixGenerator -> {
            Matrix matrix = matrixGenerator.generate(matrixSize);
            System.out.println("Matrix: ");
            matrix.print();

            CSRMatrix csrMatrix = new CSRMatrix<>(matrix, 0);

            LUDecomposition<Integer> luDecomposition = new LUDecomposition<>(csrMatrix, 0, 1);
            luDecomposition.decompose();
            Matrix<Double> l = luDecomposition.getlMatrix();
            Matrix<Double> u = luDecomposition.getuMatrix();

            System.out.println("l matrix:");
            l.print();

            System.out.println("u matrix: ");
            u.print();


        });
    }
}
