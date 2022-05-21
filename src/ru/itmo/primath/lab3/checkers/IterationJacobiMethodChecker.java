package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBold;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownHeader;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownTable;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownText;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.CSRMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;
import ru.itmo.primath.lab3.solvers.JacobiIterationLinearEquationSolver;

import java.util.ArrayList;
import java.util.List;

public class IterationJacobiMethodChecker implements MatrixAlgorithmChecker {

    private List<MarkdownBlock> markdownBlocks = new ArrayList<>();
    private List<MatrixGenerator> matrixGenerators;
    private double precision;
    private int matrixSize;

    public IterationJacobiMethodChecker(List<MatrixGenerator> matrixGenerators, double precision, int matrixSize) {
        this.matrixGenerators = matrixGenerators;
        this.precision = precision;
        this.matrixSize = matrixSize;
    }

    @Override
    public void check() {
        markdownBlocks.add(new MarkdownHeader("Check Jacobi method for iterations", 1, true));
        markdownBlocks.add(new MarkdownBold("Precision: " + precision, true));

        matrixGenerators.forEach(matrixGenerator -> {
            Matrix generatedMatrix = matrixGenerator.generate(matrixSize);


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
            Matrix right;
            try {
                right = generatedMatrix.multiply(x, CSRMatrix.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            List<Double> b = new ArrayList<>();
            for (int i = 0; i < matrixSize; ++i) {
                b.add(right.get(i, 0));
            }
            for (int i = 1; i < 50; ++i) {
                JacobiIterationLinearEquationSolver solver = new JacobiIterationLinearEquationSolver(i);

                System.out.println("solution: ");
                List<Double> solution = solver.solve(generatedMatrix, b);

                MarkdownBlock solutionBlock;
                if (solution.size() <= 50) {
                    solutionBlock = new MarkdownBold("Solution: " + solution.toString(), true);
                } else {
                    solutionBlock = new MarkdownBold("Solution is too big", true);
                }
                markdownBlocks.add(solutionBlock);

                double sum = 0;
                for (int k = 0; k < matrixSize; ++k) {
                    sum += Math.abs((x.get(k, 0) - solution.get(k)) / x.get(k, 0));
                }

                sum /= matrixSize;

                MarkdownBlock iterationsText = new MarkdownText("Iterations: " + solver.getIterationsCount());
                markdownBlocks.add(iterationsText);
                MarkdownBlock error = new MarkdownText(String.format("Avg error = %.2f%%\n", sum * 100d));
                markdownBlocks.add(error);
                System.out.printf("Avg error = %.20f%%\n", sum * 100d);

                if (sum * 100d < precision) {
                    System.out.println(precision);
                    return;
                }
            }
        });

    }

    @Override
    public List<MarkdownBlock> getMarkdownBlocks() {
        return markdownBlocks;
    }
}
