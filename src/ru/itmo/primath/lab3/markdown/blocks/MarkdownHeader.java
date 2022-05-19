package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownHeader implements MarkdownBlock{
    private String text;
    private int level;
    private boolean newLine;

    public MarkdownHeader(String text, int level) {
        this(text, level, false);
    }

    public MarkdownHeader(String text, int level, boolean newLine) {
        this.newLine = newLine;
        if (level < 1 || level > 6)
            throw new IllegalArgumentException();

        this.text = text;
        this.level = level;
    }

    @Override
    public String toMarkdown() {
        if (newLine)
            return "#".repeat(level)
                    .concat(" ")
                    .concat(text.replaceAll("^ *", ""))
                    .concat("\n");
        else
            return "#".repeat(level)
                    .concat(" ")
                    .concat(text.replaceAll("^ *", ""));
    }

    @Override
    public String toString(){
        try {
            return toMarkdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
