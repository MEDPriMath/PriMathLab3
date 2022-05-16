package ru.itmo.primath.lab3.matrix;

public abstract class Matrix2<T> implements Matrix<T> {

    protected int matrixDimensionM;
    protected int matrixDimensionN;

    public Matrix2(int matrixDimensionM, int matrixDimensionN) {
        if (matrixDimensionM <= 0 || matrixDimensionN <= 0)
            throw new IllegalArgumentException();
        this.matrixDimensionM = matrixDimensionM; // rows
        this.matrixDimensionN = matrixDimensionN; // cols
    }

    public Matrix2(int matrixDimension) {
        this(matrixDimension, matrixDimension);
    }

    @Override
    public abstract T get(int... indices);

    public int getMatrixDimensionM() {
        return matrixDimensionM;
    }

    public int getMatrixDimensionN() {
        return matrixDimensionN;
    }
}
