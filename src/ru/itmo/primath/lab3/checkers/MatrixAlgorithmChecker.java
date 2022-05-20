package ru.itmo.primath.lab3.checkers;

import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;

import java.util.List;

public interface MatrixAlgorithmChecker {
    void check();
    List<MarkdownBlock> getMarkdownBlocks();
}
