package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.checkers.LUCheck;
import ru.itmo.primath.lab3.checkers.SolveChecker;
import ru.itmo.primath.lab3.generators.*;
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
        Matrix.generateIdentityMatrix(5).print();

        Matrix<Integer> matrix = new ArrayMatrix<>(new Integer[][]{
                {1, 1, 5, 0},
                {8, 1, 0, 0},
                {0, 3, 3, 1},
                {1, 15, 5, 4}
        });

        List<MatrixGenerator> matrixGenerators1 = new ArrayList<>();
        matrixGenerators1.add(new RandomMatrixGenerator(() -> new Random().nextInt(1, 101)));
        LUCheck luCheck = new LUCheck(matrixGenerators1);
        luCheck.check(10);
        List<MarkdownBlock> markdownBlocks1 = luCheck.getMarkdownBlocks();


        List<LinearEquationSolver<Double>> linearEquationSolvers = new ArrayList<>();
        linearEquationSolvers.add(new LULinearEquationSolver<Double>(0d, 1d));
        linearEquationSolvers.add(new JacobiIterationLinearEquationSolver<>(5));

        List<MatrixGenerator> matrixGenerators = new ArrayList<>();
        for (int k = 1; k < 5; ++k)
            matrixGenerators.add(new DiagonallyDominantMatrixGenerator(k));
        matrixGenerators.add(new HilbertMatrixGenerator());

        SolveChecker solveChecker = new SolveChecker(matrixGenerators, linearEquationSolvers);
        solveChecker.check(10);
        List<MarkdownBlock> markdownBlocks2 = solveChecker.getMarkdownBlocks();

        List<MatrixGenerator> matrixGenerators2 = new ArrayList<>();
        matrixGenerators2.add(new RandomCSRMatrixGenerator<Integer>(3, () -> new Random().nextInt(1, 101)));

        List<LinearEquationSolver<Double>> linearEquationSolvers1 = new ArrayList<>();
        linearEquationSolvers1.add(new JacobiIterationLinearEquationSolver<>(5));

        SolveChecker largeMatrixChecker = new SolveChecker(matrixGenerators2, linearEquationSolvers1);
        largeMatrixChecker.check(10000);
        List<MarkdownBlock> markdownBlocks3 = largeMatrixChecker.getMarkdownBlocks();

        List<MarkdownBlock> markdownBlocks = new ArrayList<>();
        markdownBlocks.addAll(markdownBlocks1);
        markdownBlocks.addAll(markdownBlocks2);
        markdownBlocks.addAll(markdownBlocks3);

        MarkdownDocument markdownDocument = new MarkdownDocument(markdownBlocks);
        markdownDocument.toMarkdownFile("report.md");

        MarkdownDocument.toHTML("report.md", "index.html");

    }
}
