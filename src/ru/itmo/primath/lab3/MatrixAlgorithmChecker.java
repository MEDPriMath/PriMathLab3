package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;

import java.util.List;

public interface MatrixAlgorithmChecker {
    void check(int matrixSize);
    List<MarkdownBlock> getMarkdownBlocks();
}
