package ru.itmo.primath.lab3.markdown.blocks;

public class MarkdownBold implements MarkdownBlock{

    private String text;
    private boolean newLine;

    public MarkdownBold(String s) {
        this(s, false);
    }

    public MarkdownBold(String s, boolean newLine){
        this.text = s;
        this.newLine = newLine;
    }

    @Override
    public String toMarkdown() {

        String s = text.replaceAll("\n$", "")
                .replaceAll(" {2}$", "")
                .replace("\n", "**\n**")
                .replace("  ", "**  **");

        if (newLine)
            return "**".concat(s).concat("**  ");
        else
            return "**".concat(s).concat("**");
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
