package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.checkers.IterationJacobiMethodChecker;
import ru.itmo.primath.lab3.checkers.LUCheck;
import ru.itmo.primath.lab3.checkers.MatrixAlgorithmChecker;
import ru.itmo.primath.lab3.checkers.SolveChecker;
import ru.itmo.primath.lab3.generators.*;
import ru.itmo.primath.lab3.markdown.MarkdownDocument;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.solvers.JacobiIterationLinearEquationSolver;
import ru.itmo.primath.lab3.solvers.LULinearEquationSolver;
import ru.itmo.primath.lab3.solvers.LinearEquationSolver;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        List<MatrixAlgorithmChecker> matrixAlgorithmCheckers = new ArrayList<>();

        List<MatrixGenerator> matrixGenerators1 = new ArrayList<>();
        matrixGenerators1.add(new RandomMatrixGenerator(() -> new Random().nextInt(1, 101)));
        LUCheck luCheck = new LUCheck(matrixGenerators1, 10);

        matrixAlgorithmCheckers.add(luCheck);

//        luCheck.check(10);
//        List<MarkdownBlock> markdownBlocks1 = luCheck.getMarkdownBlocks();


        List<LinearEquationSolver<Double>> linearEquationSolvers = new ArrayList<>();
        linearEquationSolvers.add(new LULinearEquationSolver<Double>(0d, 1d));
        linearEquationSolvers.add(new JacobiIterationLinearEquationSolver<>(5));

        List<MatrixGenerator> matrixGenerators = new ArrayList<>();
        for (int k = 1; k < 5; ++k)
            matrixGenerators.add(new DiagonallyDominantMatrixGenerator(k));
        matrixGenerators.add(new HilbertMatrixGenerator());

        SolveChecker solveChecker = new SolveChecker(matrixGenerators, linearEquationSolvers, 10);

        matrixAlgorithmCheckers.add(solveChecker);

//        solveChecker.check(10);
//        List<MarkdownBlock> markdownBlocks2 = solveChecker.getMarkdownBlocks();

        List<MatrixGenerator> matrixGenerators2 = new ArrayList<>();
        matrixGenerators2.add(new RandomCSRMatrixGenerator<Integer>(3, () -> new Random().nextInt(1, 101)));

        IterationJacobiMethodChecker iterationJacobiMethodChecker = new IterationJacobiMethodChecker(matrixGenerators2, 10E-4, 1000);

        matrixAlgorithmCheckers.add(iterationJacobiMethodChecker);

//        iterationJacobiMethodChecker.check(10000);
//        List<MarkdownBlock> markdownBlocks3 = iterationJacobiMethodChecker.getMarkdownBlocks();

        List<MarkdownBlock> markdownBlocks = new ArrayList<>();

        matrixAlgorithmCheckers.forEach(matrixAlgorithmChecker -> {
            matrixAlgorithmChecker.check();
            markdownBlocks.addAll(matrixAlgorithmChecker.getMarkdownBlocks());
        });
//        markdownBlocks.addAll(markdownBlocks1);
//        markdownBlocks.addAll(markdownBlocks2);
//        markdownBlocks.addAll(markdownBlocks3);

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
