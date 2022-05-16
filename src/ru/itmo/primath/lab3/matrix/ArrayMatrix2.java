package ru.itmo.primath.lab3.matrix;

public class ArrayMatrix2<T> extends Matrix2<T> {

    private final T[][] matrix;

    public ArrayMatrix2(T[][] array) {
        super(array.length, array[0].length);
        this.matrix = array;
    }

    @Override
    public T get(int... indices) {
        if (indices.length != 2)
            throw new IllegalArgumentException();
        if (indices[0] < 0 || indices[0] >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (indices[1] < 0 || indices[1] >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        return matrix[indices[0]][indices[1]];
    }
}
