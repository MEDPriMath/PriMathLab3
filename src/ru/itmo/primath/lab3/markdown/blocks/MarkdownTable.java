package ru.itmo.primath.lab3.markdown.blocks;

import ru.itmo.primath.lab3.matrix.Matrix;

public class MarkdownTable implements MarkdownBlock {
    private Object[][] table;
    private boolean allBold;
    private boolean isLarge = false;
    private int width;
    private int height;

    public MarkdownTable(Object[][] table) {
        this(table, false);
    }

    public MarkdownTable(Object[][] table, boolean allBold) {
        this.table = table;
        this.allBold = allBold;
    }

    public MarkdownTable(Matrix matrix) {
        this(matrix, 1, false);
    }

    public MarkdownTable(Matrix matrix, boolean allBold) {
        this(matrix, 1, allBold);
    }

    public MarkdownTable(Matrix matrix, int precision, boolean allBold){
        this.allBold = allBold;
        if (matrix.getMatrixDimensionM() > 50 || matrix.getMatrixDimensionN() > 50) {
            width = matrix.getMatrixDimensionN();
            height = matrix.getMatrixDimensionM();
            isLarge = true;
            return;
        }

        this.table = new Object[matrix.getMatrixDimensionM()][matrix.getMatrixDimensionN()];
        for (int i = 0; i < matrix.getMatrixDimensionM(); ++i){
            for (int j = 0; j < matrix.getMatrixDimensionN(); ++j){
                this.table[i][j] = String.format("%."+precision+"f", matrix.get(i, j));
            }
        }
    }

    @Override
    public String toMarkdown() throws Exception {
        if (isLarge)
            return "*Matrix is too large: ".concat(this.width + "x" + this.height + "*\n");
        int width = table[0].length;
        int height = table.length;

        if (height < 2)
            throw new Exception();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("| ");
        for (int i = 0; i < width; i++){
            stringBuilder.append(table[0][i].toString()).append(" | ");
        }
        stringBuilder.append("\n| ");
        for (int i = 0; i < width; i++){
            stringBuilder.append("---").append('|');
        }
        stringBuilder.append("\n");

        for (int i = 1; i < height; i++){
            stringBuilder.append("| ");
            for (int j = 0; j < width; j++){
                if (allBold)
                    stringBuilder.append(new MarkdownBold(table[i][j].toString())).append(" | ");
                else
                    stringBuilder.append(table[i][j].toString()).append(" | ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
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
