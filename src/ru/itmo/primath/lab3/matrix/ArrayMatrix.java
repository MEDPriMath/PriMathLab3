package ru.itmo.primath.lab3.matrix;

import java.util.function.ObjDoubleConsumer;

public class ArrayMatrix<T extends Number> extends Matrix<T> {

    private Object[][] matrix;

    public ArrayMatrix(T[][] array) {
        super(array.length, array[0].length);
        this.matrix = array;
    }

    public ArrayMatrix(Matrix<T> otherMatrix){
        this(otherMatrix.getMatrixDimensionM(), otherMatrix.getMatrixDimensionN());
        for (int i = 0; i < matrixDimensionM; ++i){
            for (int j = 0; j < matrixDimensionN; ++j){
                this.matrix[i][j] = otherMatrix.get(i, j);
            }
        }

    }

    public ArrayMatrix(int matrixDimension){
        this(matrixDimension, matrixDimension);
    }

    public ArrayMatrix(int matrixDimensionM, int matrixDimensionN){
        super(matrixDimensionM, matrixDimensionN);
        this.matrix = new Object[matrixDimensionM][matrixDimensionN];
    }

    @Override
    public T get(int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        return (T) matrix[row][col];
    }

    public void set(T elem, int row, int col){
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        matrix[row][col] = elem;
    }
}
