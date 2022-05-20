package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownText implements MarkdownBlock{
    private String text;
    private boolean newLine;

    public MarkdownText(String s) {
        this(s, false);
    }

    public MarkdownText(String s, boolean newLine) {
        this.text = s;
        this.newLine = newLine;
    }

    @Override
    public String toMarkdown() {
        if (newLine)
            return text.concat("\n");
        else
            return text;
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
