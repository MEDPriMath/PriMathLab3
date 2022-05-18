package ru.itmo.primath.lab3.markdown.entities;

import ru.itmo.primath.lab3.matrix.Matrix;

public class MarkdownTable implements MarkdownBlock {
    private Object[][] table;

    public MarkdownTable(Object[][] table) {
        this.table = table;
    }

    public MarkdownTable(Matrix<?> matrix){
        this.table = new Object[matrix.getMatrixDimensionM()][matrix.getMatrixDimensionN()];
        for (int i = 0; i < matrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < matrix.getMatrixDimensionN(); ++j){
                this.table[i][j] = matrix.get(i, j);
            }
        }
    }

    @Override
    public String toMarkdown() throws Exception {
        int width = table[0].length;
        int height = table.length;
        if (height < 2)
            throw new Exception();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('|');
        for (int i = 0; i < width; i++){
            stringBuilder.append(table[0][i].toString()).append(" |");
        }
        stringBuilder.append("\n| ");
        for (int i = 0; i < width; i++){
            stringBuilder.append("---").append('|');
        }
        stringBuilder.append("\n");

        for (int i = 1; i < height; i++){
            stringBuilder.append("| ");
            for (int j = 0; j < width; j++){
                stringBuilder.append(table[i][j].toString()).append(" |");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
