package ru.itmo.primath.lab3.matrix;

public class ArrayMatrix extends Matrix {

    private double[][] matrix;

    public ArrayMatrix(double[][] array) {
        super(array.length, array[0].length);
        this.matrix = array;
    }

    public ArrayMatrix(Matrix otherMatrix) {
        this(otherMatrix.getMatrixDimensionM(), otherMatrix.getMatrixDimensionN());
        for (int i = 0; i < matrixDimensionM; ++i) {
            for (int j = 0; j < matrixDimensionN; ++j) {
                matrix[i][j] = otherMatrix.get(i, j);
            }
        }

    }

    public ArrayMatrix(int matrixDimension) {
        this(matrixDimension, matrixDimension);
    }

    public ArrayMatrix(int matrixDimensionM, int matrixDimensionN) {
        super(matrixDimensionM, matrixDimensionN);
        matrix = new double[matrixDimensionM][matrixDimensionN];
    }

    @Override
    public double get(int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        return matrix[row][col];
    }

    public void set(double elem, int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        matrix[row][col] = elem;
    }
}
