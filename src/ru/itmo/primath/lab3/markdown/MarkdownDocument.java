package ru.itmo.primath.lab3.markdown;

import ru.itmo.primath.lab3.markdown.blocks.MarkdownBlock;
import ru.itmo.primath.lab3.markdown.description.DescriptionStorage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MarkdownDocument {
    private List<MarkdownBlock> markdownBlocks;

    public MarkdownDocument() {
        this.markdownBlocks = new ArrayList<>();
    }

    public MarkdownDocument(List<MarkdownBlock> markdownBlocks) {
        this.markdownBlocks = new ArrayList<>(markdownBlocks);
    }

    public void AddBlock(MarkdownBlock markdownBlock) {
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

    public String toMarkdownString() {
        StringBuilder stringBuilder = new StringBuilder();
        markdownBlocks.forEach(markdownBlock -> {
            try {
                stringBuilder.append(markdownBlock.toMarkdown()).append("\n");
            } catch (Exception ignored) {
            }
        });

        return stringBuilder.toString();
    }

    public static void toHTML(String markdownFilename, String htmlFilename) throws IOException {
        String markdown = Files.readString(Path.of(markdownFilename));

        FileWriter fileWriter = new FileWriter(htmlFilename);
        fileWriter.write(DescriptionStorage.htmlPre + "\n" + markdown + "\n" + DescriptionStorage.htmlPost);
        fileWriter.close();
    }

    @Override
    public String toString() {
        return toMarkdownString();
    }
}
