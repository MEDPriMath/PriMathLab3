package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownHeader;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownTable;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownText;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class LUCheck implements MatrixAlgorithmChecker {
    private List<MatrixGenerator> matrixGenerators;

    public LUCheck(List<MatrixGenerator> matrixGenerators) {
        this.matrixGenerators = matrixGenerators;
    }
    private List<MarkdownBlock> markdownBlocks = new ArrayList<>();

    @Override
    public void check(int matrixSize){

        markdownBlocks.add(new MarkdownHeader("       Check LU decomposition", 1));

        matrixGenerators.forEach(matrixGenerator -> {
            Matrix matrix = matrixGenerator.generate(matrixSize);
            MarkdownBlock textBlock = new MarkdownText("Matrix:");
            markdownBlocks.add(textBlock);
            System.out.println("Matrix: ");

            MarkdownBlock matrixBlock = new MarkdownTable(matrix, true);
            markdownBlocks.add(matrixBlock);
            matrix.print();

            CSRMatrix csrMatrix = new CSRMatrix<>(matrix, 0);

            LUDecomposition<Integer> luDecomposition = new LUDecomposition<>(csrMatrix, 0, 1);
            luDecomposition.decompose();
            Matrix<Double> l = luDecomposition.getlMatrix();
            Matrix<Double> u = luDecomposition.getuMatrix();

            MarkdownBlock textLBlock = new MarkdownText("L matrix:");
            markdownBlocks.add(textLBlock);
            System.out.println("L matrix:");

            MarkdownBlock lMatrixBlock = new MarkdownTable(l, true);
            markdownBlocks.add(lMatrixBlock);
            l.print();

            MarkdownBlock textUBlock = new MarkdownText("U matrix:");
            markdownBlocks.add(textUBlock);
            System.out.println("U matrix:");

            MarkdownBlock uMatrixBlock = new MarkdownTable(u, true);
            markdownBlocks.add(uMatrixBlock);
            u.print();


        });
    }

    @Override
    public List<MarkdownBlock> getMarkdownBlocks() {
        return markdownBlocks;
    }
}
