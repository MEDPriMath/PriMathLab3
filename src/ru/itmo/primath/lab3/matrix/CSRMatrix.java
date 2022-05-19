package ru.itmo.primath.lab3.matrix;

import java.util.ArrayList;
import java.util.List;

public class CSRMatrix<T extends Number> extends Matrix<T> {

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

    public CSRMatrix(int dimensionM, int dimensionN) {
        this(dimensionM, dimensionN, (T) Double.valueOf(0));
    }

    public CSRMatrix(int dimensionM, int dimensionN, T zero) {
        super(dimensionM, dimensionN);
        this.zero = zero;
        this.data = new ArrayList<>();
        this.indices = new ArrayList<>();
        this.indPtr = new ArrayList<>();
        for (int i = 0; i < dimensionM + 1; ++i){
            indPtr.add(1);
        }
    }

    public CSRMatrix(int dimension, T zero) {
        this(dimension, dimension, zero);
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
    public T get(int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        int elementsBefore = this.indPtr.get(row) - this.indPtr.get(0);
        int elementsInRow = countElementsInRow(row);
        for (int i = elementsBefore; i < elementsBefore + elementsInRow; ++i) {
            if (this.indices.get(i) == col)
                return this.data.get(i);
        }
        return zero;
    }

    @Override
    public void set(T elem, int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        int elementsBefore = this.indPtr.get(row) - this.indPtr.get(0);
        int elementsInRow = countElementsInRow(row);

        for (int i = elementsBefore; i < elementsBefore + elementsInRow; ++i) {
            if (this.indices.get(i) == col) {
                this.data.set(i, elem);
                return;
            }
            if (this.indices.get(i) > col){
                for (int k = row + 1; k < indPtr.size(); ++k)
                    indPtr.set(k, indPtr.get(k) + 1);
                this.data.add(zero);
                this.indices.add(0);
                for (int k = this.data.size() - 2; k >= i; --k){
                    this.data.set(k + 1, this.data.get(k));
                    this.indices.set(k + 1, this.indices.get(k));
                }
                this.data.set(i, elem);
                this.indices.set(i, col);
                return;
            }
        }
        for (int k = row + 1; k < indPtr.size(); ++k)
            indPtr.set(k, indPtr.get(k) + 1);
        this.data.add(zero);
        this.indices.add(0);
        for (int k = this.data.size() - 2; k >= elementsBefore + elementsInRow; --k){
            this.data.set(k + 1, this.data.get(k));
            this.indices.set(k + 1, this.indices.get(k));
        }
        this.data.set(elementsBefore + elementsInRow, elem);
        this.indices.set(elementsBefore + elementsInRow, col);



        /*ArrayMatrix<T> matrix = new ArrayMatrix<>(this);
        matrix.set(elem, row, col);
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
        this.indPtr = indPtr;*/
    }

    private int countElementsInRow(int row) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();

        return indPtr.get(row + 1) - indPtr.get(row);
    }
}
