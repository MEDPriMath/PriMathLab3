package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBold;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownHeader;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownQuote;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownTable;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownText;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;
import ru.itmo.primath.lab3.solvers.JacobiIterationLinearEquationSolver;
import ru.itmo.primath.lab3.solvers.LinearEquationSolver;

import java.util.ArrayList;
import java.util.List;

public class SolveChecker implements MatrixAlgorithmChecker {
    private List<MatrixGenerator> matrixGenerators;
    private List<LinearEquationSolver> linearEquationSolvers;
    private List<MarkdownBlock> markdownBlocks = new ArrayList<>();
    private int matrixSize;

    public SolveChecker(List<MatrixGenerator> matrixGenerators, List<LinearEquationSolver> linearEquationSolvers, int matrixSize) {
        this.matrixGenerators = new ArrayList<>(matrixGenerators);
        this.linearEquationSolvers = new ArrayList<>(linearEquationSolvers);
        this.matrixSize = matrixSize;
    }

    @Override
    public void check() {

        markdownBlocks.add(new MarkdownHeader("Check solution for different matrix and solvers", 1, true));

        linearEquationSolvers.forEach(linearEquationSolver -> {
            markdownBlocks.add(new MarkdownHeader(linearEquationSolver.getClass().getSimpleName(), 2, true));
            markdownBlocks.add(new MarkdownQuote(linearEquationSolver.toString()));

            matrixGenerators.forEach(matrixGenerator -> {

                Matrix generatedMatrix = matrixGenerator.generate(matrixSize);

                MarkdownBlock solver = new MarkdownBold(linearEquationSolver.getClass().getSimpleName());
                markdownBlocks.add(solver);
                if (linearEquationSolver instanceof JacobiIterationLinearEquationSolver) {
                    MarkdownBlock iterationsText = new MarkdownBold("Iterations: " + ((JacobiIterationLinearEquationSolver) linearEquationSolver).getIterationsCount());
                    markdownBlocks.add(iterationsText);
                }

                MarkdownBlock generated = new MarkdownText("generated with " + matrixGenerator.getClass().getSimpleName() + ":", true);
                markdownBlocks.add(generated);
                System.out.println("generated with " + matrixGenerator.getClass().getSimpleName() + ":");

                MarkdownBlock genMatrix = new MarkdownTable(generatedMatrix, true);
                markdownBlocks.add(genMatrix);
//                generatedMatrix.print(3);

                Matrix x = new ArrayMatrix(matrixSize, 1);
                for (int i = 0; i < matrixSize; ++i) {
                    x.set(i + 1, i, 0);
                }
                Matrix right = generatedMatrix.multiply(x);

                List<Double> b = new ArrayList<>();
                for (int i = 0; i < matrixSize; ++i) {
                    b.add(right.get(i, 0));
                }

                System.out.println("solution: ");
                List<Double> solution = linearEquationSolver.solve(generatedMatrix, b);
//                System.out.println(solution.toString());

                MarkdownBlock solutionBlock;
                if (solution.size() <= 50) {
                    solutionBlock = new MarkdownBold("Solution: " + solution.toString(), true);
                } else {
                    solutionBlock = new MarkdownBold("Solution is too big", true);
                }
                markdownBlocks.add(solutionBlock);

                double sum = 0;
                for (int i = 0; i < matrixSize; ++i) {
                    sum += Math.abs((x.get(i, 0) - solution.get(i)) / x.get(i, 0));
                }

                sum /= matrixSize;

                MarkdownBlock error = new MarkdownText(String.format("Avg error = %.2f%%\n", sum * 100d));
                markdownBlocks.add(error);
                System.out.printf("Avg error = %.20f%%\n", sum * 100d);
            });
        });
    }

    @Override
    public List<MarkdownBlock> getMarkdownBlocks() {
        return markdownBlocks;
    }
}
