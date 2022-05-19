package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownText implements MarkdownBlock{
    private String text;

    public MarkdownText(String s) {
        this.text = s;
    }

    @Override
    public String toMarkdown() {
        return text.replaceAll("\n$", "")
                .concat("\n");
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
