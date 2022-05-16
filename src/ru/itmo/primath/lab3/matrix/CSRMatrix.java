package ru.itmo.primath.lab3.matrix;

import java.util.ArrayList;
import java.util.List;

public class CSRMatrix<T> extends Matrix<T> {

    private T zero;
    private List<T> data;
    private List<Integer> indices;
    private List<Integer> indPtr;

    public CSRMatrix(int dimensionM, int dimensionN, T zero, List<T> data, List<Integer> indices, List<Integer> indPtr) {
        super(dimensionM, dimensionN);
        this.zero = zero;
        this.data = new ArrayList<>(data);
        this.indices = new ArrayList<>(indices);
        this.indPtr = new ArrayList<>(indPtr);
    }

    public CSRMatrix(int dimension, T zero, List<T> data, List<Integer> indices, List<Integer> indPtr) {
        this(dimension, dimension, zero, data, indices, indPtr);
        this.zero = zero;
        this.data = new ArrayList<>(data);
        this.indices = new ArrayList<>(indices);
        this.indPtr = new ArrayList<>(indPtr);
    }

    public CSRMatrix(Matrix<T> matrix, T zero) {
        super(matrix.getMatrixDimensionM(), matrix.getMatrixDimensionN());
        this.zero = zero;
        List<T> data = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Integer> indPtr = new ArrayList<>();
        indPtr.add(1);
        for (int i = 0; i < matrix.getMatrixDimensionM(); ++i) {
            int notNull = 0;
            for (int j = 0; j < matrix.getMatrixDimensionN(); ++j) {
                T element = matrix.get(i, j);
                if (!element.equals(zero)) {
                    data.add(element);
                    indices.add(j);
                    ++notNull;
                }
            }
            indPtr.add(indPtr.get(indPtr.size() - 1) + notNull);
        }
        this.data = data;
        this.indices = indices;
        this.indPtr = indPtr;
    }

    @Override
    public T get(int... indices) {
        if (indices.length != 2)
            throw new IllegalArgumentException();
        if (indices[0] < 0 || indices[0] >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (indices[1] < 0 || indices[1] >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        int elementsBefore = this.indPtr.get(indices[0]) - this.indPtr.get(0);
        int elementsInRow = countElementsInRow(indices[0]);
        for (int i = elementsBefore; i < elementsBefore + elementsInRow; ++i) {
            if (this.indices.get(i) == indices[1])
                return this.data.get(i);
        }
        return zero;
    }

    private int countElementsInRow(int row) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();

        return indPtr.get(row + 1) - indPtr.get(row);
    }
}
