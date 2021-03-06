package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.invertible.InvertMatrix;
import ru.itmo.primath.lab3.lu.LUDecomposition;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBold;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownHeader;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownQuote;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownTable;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownText;
import ru.itmo.primath.lab3.markdown.description.DescriptionStorage;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class LUCheck implements MatrixAlgorithmChecker {
    private List<MatrixGenerator> matrixGenerators;
    private int matrixSize;

    public LUCheck(List<MatrixGenerator> matrixGenerators, int matrixSize) {
        this.matrixGenerators = matrixGenerators;
        this.matrixSize = matrixSize;
    }
    private List<MarkdownBlock> markdownBlocks = new ArrayList<>();

    @Override
    public void check(){

        matrixGenerators.forEach(matrixGenerator -> {

            markdownBlocks.add(new MarkdownHeader("Check LU decomposition", 1, true));
            markdownBlocks.add(new MarkdownQuote(DescriptionStorage.luDescription));

            Matrix matrix = matrixGenerator.generate(matrixSize);
            MarkdownBlock textBlock = new MarkdownText("Matrix:", true);
            markdownBlocks.add(textBlock);
            System.out.println("Matrix: ");

            MarkdownBlock matrixBlock = new MarkdownTable(matrix, true);
            markdownBlocks.add(matrixBlock);
//            matrix.print();

            CSRMatrix csrMatrix = new CSRMatrix(matrix);

            LUDecomposition luDecomposition = new LUDecomposition(csrMatrix);
            luDecomposition.decompose();
            Matrix l = luDecomposition.L();
            Matrix u = luDecomposition.U();

            MarkdownBlock textLBlock = new MarkdownText("L matrix:", true);
            markdownBlocks.add(textLBlock);
            System.out.println("L matrix:");

            MarkdownBlock lMatrixTable = new MarkdownTable(l, true);
            markdownBlocks.add(lMatrixTable);
//            l.print();

            MarkdownBlock checkLText;
            if (l.isLowerTriangular()) {
                checkLText = new MarkdownBold("L matrix is lower triangular", true);
            } else {
                checkLText = new MarkdownBold("L matrix is not lower triangular", true);
            }
            markdownBlocks.add(checkLText);

            MarkdownBlock textUBlock = new MarkdownText("U matrix:", true);
            markdownBlocks.add(textUBlock);
            System.out.println("U matrix:");

            MarkdownBlock uMatrixTable = new MarkdownTable(u, true);
            markdownBlocks.add(uMatrixTable);
//            u.print();

            MarkdownBlock checkUText;
            if (u.isUpperTriangular()) {
                checkUText = new MarkdownBold("U matrix is upper triangular",true);
            } else {
                checkUText = new MarkdownBold("U matrix is not upper triangular", true);
            }
            markdownBlocks.add(checkUText);

            MarkdownBlock checkTest = new MarkdownText("L???U =", true);
            markdownBlocks.add(checkTest);

            Matrix checkLU = l.multiply(u);

            MarkdownBlock checkLUTable = new MarkdownTable(checkLU, true);
            markdownBlocks.add(checkLUTable);
//            checkLU.print();

            MarkdownBlock checkLUText;
            if (matrix.equals(checkLU))
                checkLUText = new MarkdownBold("Matrix equals to source one");
            else
                checkLUText = new MarkdownBold("Matrix not equals to source one");

            markdownBlocks.add(checkLUText);


            markdownBlocks.add(new MarkdownHeader("Check invert Matrix algorithm", 1, true));

            MarkdownBlock textInvert = new MarkdownText("Invert matrix:", true);
            markdownBlocks.add(textInvert);
            MarkdownBlock invertDescription = new MarkdownQuote(DescriptionStorage.invertMatrixDescription);
            markdownBlocks.add(invertDescription);

            InvertMatrix invertMatrix = new InvertMatrix(matrix);
            invertMatrix.invert();
            Matrix invert = invertMatrix.getInvertibleMatrix();
            MarkdownBlock invertTable = new MarkdownTable(invert,5, true);
            markdownBlocks.add(invertTable);

            MarkdownBlock textInvertCheck = new MarkdownText("A???A<sup>-1</sup>:", true);
            markdownBlocks.add(textInvertCheck);

            Matrix checkInvert = matrix.multiply(invert);
            MarkdownBlock checkInvertTable = new MarkdownTable(checkInvert, true);
            markdownBlocks.add(checkInvertTable);

            MarkdownBlock checkInvertText;
            if (checkInvert.equals(Matrix.generateIdentityMatrix(checkInvert.getMatrixDimensionM()))){
                checkInvertText = new MarkdownBold("It is Identity matrix");
            } else {
                checkInvertText = new MarkdownBold("It is not Identity matrix");
            }
            markdownBlocks.add(checkInvertText);
        });
    }

    @Override
    public List<MarkdownBlock> getMarkdownBlocks() {
        return markdownBlocks;
    }
}
