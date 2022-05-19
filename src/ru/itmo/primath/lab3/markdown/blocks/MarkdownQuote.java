package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownQuote implements MarkdownBlock{

    private String text;

    public MarkdownQuote(String text) {
        this.text = text;
    }

    @Override
    public String toMarkdown() {
        return "> ".concat(text)
                .replaceAll("\n$", "")
                .replace("\n", "  \n> ")
                .concat("\n");
    }
}
