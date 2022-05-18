package ru.itmo.primath.lab3.markdown.entities;

public class MarkdownText implements MarkdownBlock{
    private String text;

    public MarkdownText(String s) {
        this.text = s;
    }

    @Override
    public String toMarkdown() {
        return text.concat("\n");
    }
}
