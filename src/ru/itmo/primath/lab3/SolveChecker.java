package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.generators.MatrixGenerator;
import ru.itmo.primath.lab3.markdown.MarkdownDocument;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownBold;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownTable;
import ru.itmo.primath.lab3.markdown.blocks.MarkdownText;
import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.Matrix;
import ru.itmo.primath.lab3.solvers.LinearEquationSolver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolveChecker {
    private List<MatrixGenerator> matrixGenerators;
    private List<LinearEquationSolver<Double>> linearEquationSolvers;

    public SolveChecker(List<MatrixGenerator> matrixGenerators, List<LinearEquationSolver<Double>> linearEquationSolvers) {
        this.matrixGenerators = new ArrayList<>(matrixGenerators);
        this.linearEquationSolvers = new ArrayList<>(linearEquationSolvers);
    }

    public void check(int matrixSize) throws IOException {
        MarkdownDocument markdownDocument = new MarkdownDocument();

        linearEquationSolvers.forEach(linearEquationSolver -> {
            MarkdownBlock solver = new MarkdownBold(linearEquationSolver.getClass().getSimpleName());
            markdownDocument.AddBlock(solver);
            matrixGenerators.forEach(matrixGenerator -> {

                Matrix<Double> generatedMatrix = matrixGenerator.generate(matrixSize);

                MarkdownBlock generated = new MarkdownText("generated with " + matrixGenerator.getClass().getSimpleName() + ":");
                markdownDocument.AddBlock(generated);
                System.out.println("generated with " + matrixGenerator.getClass().getSimpleName() + ":");

                MarkdownBlock genMatrix = new MarkdownTable(generatedMatrix);
                markdownDocument.AddBlock(genMatrix);
                generatedMatrix.print(3);

                Matrix<Integer> x = new ArrayMatrix<>(matrixSize, 1);
                for (int i = 0; i < matrixSize; ++i){
                    x.set(i + 1, i, 0);
                }
                Matrix<Double> right = generatedMatrix.multiply(x);

                List<Double> b = new ArrayList<>();
                for (int i = 0; i < matrixSize; ++i){
                    b.add(right.get(i, 0));
                }

                System.out.println("solution: ");
                List<Double> solution = linearEquationSolver.solve(generatedMatrix, b);
                System.out.println(solution);
                double sum = 0;
                for (int i = 0; i < matrixSize; ++i){
                    sum += Math.abs((x.get(i, 0) - solution.get(i)) / x.get(i, 0));
                }

                sum /= matrixSize;

                MarkdownBlock error = new MarkdownText(String.format("Avg error = %.20f%%\n", sum * 100d));
                markdownDocument.AddBlock(error);
                System.out.printf("Avg error = %.20f%%\n", sum * 100d);
            });
        });

        FileWriter fileWriter = new FileWriter("report.md");
        fileWriter.write(markdownDocument.toMarkdown());
        fileWriter.close();
    }
}
