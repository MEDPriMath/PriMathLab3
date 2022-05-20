package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.checkers.IterationJacobiMethodChecker;
import ru.itmo.primath.lab3.checkers.LUCheck;
import ru.itmo.primath.lab3.checkers.MatrixAlgorithmChecker;
import ru.itmo.primath.lab3.checkers.SolveChecker;
import ru.itmo.primath.lab3.generators.DiagonallyDominantMatrixGenerator;
import ru.itmo.primath.lab3.generators.HilbertMatrixGenerator;
import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.generators.RandomCSRMatrixGenerator;
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
        Matrix csr = new CSRMatrix(new ArrayMatrix(new double[][] {
            {1d, 2d, 3d},
            {4d ,0d, 5d},
            {6d, 2d, -44d}
        }));

        csr.print();
        System.out.println();
        csr.set(0, 1, 1);
        csr.print();

        List<MatrixAlgorithmChecker> matrixAlgorithmCheckers = new ArrayList<>();

        List<MatrixGenerator> matrixGenerators1 = new ArrayList<>();
        matrixGenerators1.add(new RandomMatrixGenerator(() -> (double) new Random().nextInt(1, 101)));
        LUCheck luCheck = new LUCheck(matrixGenerators1, 10);

        matrixAlgorithmCheckers.add(luCheck);

        List<LinearEquationSolver> linearEquationSolvers = new ArrayList<>();
        linearEquationSolvers.add(new LULinearEquationSolver(0d, 1d));
        linearEquationSolvers.add(new JacobiIterationLinearEquationSolver(5));

        List<MatrixGenerator> matrixGenerators = new ArrayList<>();
        for (int k = 1; k < 5; ++k)
            matrixGenerators.add(new DiagonallyDominantMatrixGenerator(k));
        matrixGenerators.add(new HilbertMatrixGenerator());

        SolveChecker solveChecker = new SolveChecker(matrixGenerators, linearEquationSolvers, 10);

        matrixAlgorithmCheckers.add(solveChecker);

        List<MatrixGenerator> matrixGenerators2 = new ArrayList<>();
        matrixGenerators2.add(new RandomCSRMatrixGenerator(3, () -> (double) new Random().nextInt(1, 101)));

        IterationJacobiMethodChecker iterationJacobiMethodChecker = new IterationJacobiMethodChecker(matrixGenerators2, 10E-4, 10000);

        matrixAlgorithmCheckers.add(iterationJacobiMethodChecker);

        List<MarkdownBlock> markdownBlocks = new ArrayList<>();

        matrixAlgorithmCheckers.forEach(matrixAlgorithmChecker -> {
            matrixAlgorithmChecker.check();
            markdownBlocks.addAll(matrixAlgorithmChecker.getMarkdownBlocks());
        });

        MarkdownDocument markdownDocument = new MarkdownDocument(markdownBlocks);
        markdownDocument.toMarkdownFile("report.md");

        MarkdownDocument.toHTML("report.md", "..\\ReportLab3\\index.html");

        //commitAndPush();
    }

    private static int commitAndPush() throws Exception {
        Process p = Runtime.getRuntime().exec("..\\ReportLab3\\pushScript.cmd");
        return p.waitFor();
    }
}
