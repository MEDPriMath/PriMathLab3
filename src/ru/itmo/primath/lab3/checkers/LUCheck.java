package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.invertible.InvertMatrix;
import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.markdown.blocks.*;
import ru.itmo.primath.lab3.markdown.description.DescriptionStorage;
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

        matrixGenerators.forEach(matrixGenerator -> {

            markdownBlocks.add(new MarkdownHeader("Check LU decomposition", 1, true));
            markdownBlocks.add(new MarkdownQuote(DescriptionStorage.luDescription));

            Matrix<Double> matrix = matrixGenerator.generate(matrixSize);
            MarkdownBlock textBlock = new MarkdownText("Matrix:");
            markdownBlocks.add(textBlock);
            System.out.println("Matrix: ");

            MarkdownBlock matrixBlock = new MarkdownTable(matrix, true);
            markdownBlocks.add(matrixBlock);
            matrix.print();

            CSRMatrix<Double> csrMatrix = new CSRMatrix<>(matrix, 0d);

            LUDecomposition<Double> luDecomposition = new LUDecomposition<>(csrMatrix, 0d, 1d);
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

            MarkdownBlock checkTest = new MarkdownText("L∙U =");
            markdownBlocks.add(checkTest);

            Matrix<Double> checkLU = l.multiply(u);

            MarkdownBlock checkLUTable = new MarkdownTable(checkLU, true);
            markdownBlocks.add(checkLUTable);
            checkLU.print();

            markdownBlocks.add(new MarkdownHeader("Check invert Matrix algorithm", 1, true));

            MarkdownBlock textInvert = new MarkdownText("Invert matrix:");
            markdownBlocks.add(textInvert);

            InvertMatrix<Double> invertMatrix = new InvertMatrix<>(matrix, 0d, 1d);
            invertMatrix.invert();
            Matrix<Double> invert = invertMatrix.getInvertibleMatrix();
            MarkdownBlock invertTable = new MarkdownTable(invert,5, true);
            markdownBlocks.add(invertTable);

            MarkdownBlock textInvertCheck = new MarkdownText("A∙A<sup>-1</sup>:");
            markdownBlocks.add(textInvertCheck);

            Matrix<Double> checkInvert = matrix.multiply(invert);
            MarkdownBlock checkInvertTable = new MarkdownTable(checkInvert, true);
            markdownBlocks.add(checkInvertTable);

        });
    }

    @Override
    public List<MarkdownBlock> getMarkdownBlocks() {
        return markdownBlocks;
    }
}