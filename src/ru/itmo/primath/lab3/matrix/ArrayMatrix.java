package ru.itmo.primath.lab3.matrix;

import java.util.function.ObjDoubleConsumer;

public class ArrayMatrix<T> extends Matrix<T> {

    private T[][] matrix;

    public ArrayMatrix(T[][] array) {
        super(array.length, array[0].length);
        this.matrix = array;
    }

    public ArrayMatrix(int matrixDimension){
        super(matrixDimension);
        this.matrix = (T[][]) new Object[matrixDimension][matrixDimension];
    }

    @Override
    public T get(int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        return matrix[row][col];
    }

    public void set(T elem, int row, int col){
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        matrix[row][col] = elem;
    }
}
