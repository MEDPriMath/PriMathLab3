package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.checkers.LUCheck;
import ru.itmo.primath.lab3.checkers.SolveChecker;
import ru.itmo.primath.lab3.generators.DiagonallyDominantMatrixGenerator;
import ru.itmo.primath.lab3.generators.HilbertMatrixGenerator;
import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.generators.RandomMatrixGenerator;
import ru.itmo.primath.lab3.markdown.MarkdownDocument;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;
import ru.itmo.primath.lab3.solvers.JacobiIterationLinearEquationSolver;
import ru.itmo.primath.lab3.solvers.LULinearEquationSolver;
import ru.itmo.primath.lab3.solvers.LinearEquationSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Here we go again");

        Matrix<Integer> matrix = new ArrayMatrix<>(new Integer[][]{
                {1, 1, 5, 0},
                {8, 1, 0, 0},
                {0, 3, 3, 1},
                {1, 15, 5, 4}
        });

        Matrix mm = matrix.multiply(matrix, CSRMatrix.class);
        System.out.println(mm.getClass().getSimpleName());

        Matrix mmm = matrix.multiply(matrix, ArrayMatrix.class);
        System.out.println(mmm.getClass().getSimpleName());

        Matrix m = new CSRMatrix(matrix, 0);
        m.print();
        System.out.println();
        m.set(3, 2, 0);
        m.print();

        List<MatrixGenerator> matrixGenerators1 = new ArrayList<>();
        matrixGenerators1.add(new RandomMatrixGenerator(() -> new Random().nextInt(1, 101)));
        LUCheck luCheck = new LUCheck(matrixGenerators1);
        luCheck.check(5);
        List<MarkdownBlock> markdownBlocks1 = luCheck.getMarkdownBlocks();


        List<LinearEquationSolver<Double>> linearEquationSolvers = new ArrayList<>();
        linearEquationSolvers.add(new LULinearEquationSolver<Double>(0d, 1d));
        linearEquationSolvers.add(new JacobiIterationLinearEquationSolver<>(5));

        List<MatrixGenerator> matrixGenerators = new ArrayList<>();
        for (int k = 1; k < 5; ++k)
            matrixGenerators.add(new DiagonallyDominantMatrixGenerator(k));
        matrixGenerators.add(new HilbertMatrixGenerator());

        SolveChecker solveChecker = new SolveChecker(matrixGenerators, linearEquationSolvers);
        solveChecker.check(5);
        List<MarkdownBlock> markdownBlocks2 = solveChecker.getMarkdownBlocks();

        List<MarkdownBlock> markdownBlocks = new ArrayList<>();
        markdownBlocks.addAll(markdownBlocks1);
        markdownBlocks.addAll(markdownBlocks2);

        MarkdownDocument markdownDocument = new MarkdownDocument(markdownBlocks);
        markdownDocument.toMarkdownFile("report.md");

        MarkdownDocument.toHTML("report.md", "index.html");

//        FileWriter fileWriter = new FileWriter("report.md");
//        fileWriter.write(new MarkdownTable(csrMatrix).toMarkdown());
//        fileWriter.close();
    }
}
