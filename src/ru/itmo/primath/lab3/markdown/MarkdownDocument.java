package ru.itmo.primath.lab3.markdown;

import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;

import java.io.FileWriter;
import java.io.IOException;
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

    public void toMarkdownFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        StringBuilder stringBuilder = new StringBuilder();
        markdownBlocks.forEach(markdownBlock -> {
            try {
                stringBuilder.append(markdownBlock.toMarkdown()).append("\n");
            } catch (Exception ignored) {
            }
        });

        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }

    public String toMarkdownString(){
        StringBuilder stringBuilder = new StringBuilder();
        markdownBlocks.forEach(markdownBlock -> {
            try {
                stringBuilder.append(markdownBlock.toMarkdown()).append("\n");
            } catch (Exception ignored) {
            }
        });

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return toMarkdownString();
    }
}
