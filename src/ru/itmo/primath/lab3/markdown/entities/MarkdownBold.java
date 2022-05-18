package ru.itmo.primath.lab3.markdown.entities;

public class MarkdownBold implements MarkdownBlock{

    private String text;

    public MarkdownBold(String s){
        this.text = s;
    }

    @Override
    public String toMarkdown() {
        return "**".concat(text).concat("**\n");
    }
}
