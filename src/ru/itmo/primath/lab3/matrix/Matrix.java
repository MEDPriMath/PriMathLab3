package ru.itmo.primath.lab3.matrix;

public abstract class Matrix<T> {

    protected int matrixDimensionM;
    protected int matrixDimensionN;

    public Matrix(int matrixDimensionM, int matrixDimensionN) {
        if (matrixDimensionM <= 0 || matrixDimensionN <= 0)
            throw new IllegalArgumentException();
        this.matrixDimensionM = matrixDimensionM; // rows
        this.matrixDimensionN = matrixDimensionN; // cols
    }

    public Matrix(int matrixDimension) {
        this(matrixDimension, matrixDimension);
    }

    public abstract T get(int... indices);

    public int getMatrixDimensionM() {
        return matrixDimensionM;
    }

    public int getMatrixDimensionN() {
        return matrixDimensionN;
    }
}
