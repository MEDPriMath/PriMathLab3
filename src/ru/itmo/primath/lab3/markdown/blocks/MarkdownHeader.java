package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownHeader implements MarkdownBlock{
    private String text;
    private int level;

    public MarkdownHeader(String text, int level) {
        if (level < 1 || level > 6)
            throw new IllegalArgumentException();

        this.text = text;
        this.level = level;
    }

    @Override
    public String toMarkdown() {
        return "#".repeat(level).concat(text);
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
