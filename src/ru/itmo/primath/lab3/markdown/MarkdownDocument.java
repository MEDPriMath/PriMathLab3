package ru.itmo.primath.lab3.markdown;

import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;

import java.util.ArrayList;
import java.util.List;

public class MarkdownDocument {
    private List<MarkdownBlock> markdownBlocks;

    public MarkdownDocument(){
        this.markdownBlocks = new ArrayList<>();
    }
    public MarkdownDocument(List<MarkdownBlock> markdownBlocks) {
        this.markdownBlocks = new ArrayList<>(markdownBlocks);
    }

    public void AddBlock(MarkdownBlock markdownBlock){
        markdownBlocks.add(markdownBlock);
    }

    public String toMarkdown(){
        StringBuilder stringBuilder = new StringBuilder();
        markdownBlocks.forEach(markdownBlock -> {
            try {
                stringBuilder.append(markdownBlock.toMarkdown()).append("\n");
            } catch (Exception ignored) {
            }
        });

        return stringBuilder.toString();
    }
}
