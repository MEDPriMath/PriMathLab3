package ru.itmo.primath.lab3.matrix;

import java.util.ArrayList;
import java.util.List;

public class CSRMatrix extends Matrix {

    private final List<Double> data;
    private final List<Integer> indices;
    private final List<Integer> indPtr;

    public CSRMatrix(int dimensionM, int dimensionN, List<Double> data, List<Integer> indices, List<Integer> indPtr) {
        super(dimensionM, dimensionN);
        this.data = new ArrayList<>(data);
        this.indices = new ArrayList<>(indices);
        this.indPtr = new ArrayList<>(indPtr);
    }

    public CSRMatrix(int dimensionM, int dimensionN) {
        super(dimensionM, dimensionN);
        this.data = new ArrayList<>();
        this.indices = new ArrayList<>();
        this.indPtr = new ArrayList<>();
        for (int i = 0; i < dimensionM + 1; ++i) {
            indPtr.add(1);
        }
    }

    public CSRMatrix(int dimension) {
        this(dimension, dimension);
    }

    public CSRMatrix(int dimension, List<Double> data, List<Integer> indices, List<Integer> indPtr) {
        this(dimension, dimension, data, indices, indPtr);
    }

    public CSRMatrix(Matrix matrix) {
        super(matrix.getMatrixDimensionM(), matrix.getMatrixDimensionN());
        List<Double> data = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Integer> indPtr = new ArrayList<>();
        indPtr.add(1);
        for (int i = 0; i < matrix.getMatrixDimensionM(); ++i) {
            int notNull = 0;
            for (int j = 0; j < matrix.getMatrixDimensionN(); ++j) {
                double element = matrix.get(i, j);
                if (element != 0) {
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
    public double get(int row, int col) {
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
        return 0;
    }

    @Override
    public void set(double elem, int row, int col) {
        if (row < 0 || row >= matrixDimensionM)
            throw new ArrayIndexOutOfBoundsException();
        if (col < 0 || col >= matrixDimensionN)
            throw new ArrayIndexOutOfBoundsException();

        int elementsBefore = this.indPtr.get(row) - this.indPtr.get(0);
        int elementsInRow = countElementsInRow(row);

        for (int i = elementsBefore; i < elementsBefore + elementsInRow; ++i) {
            if (this.indices.get(i) == col) {
                if (Math.abs(elem) < 10E-6){
                    this.data.remove(i);
                    this.indices.remove(i);
                    for (int k = col + 1; k <= getMatrixDimensionN(); ++k){
                        this.indPtr.set(k, this.indPtr.get(k) - 1);
                        return;
                    }
                }
                this.data.set(i, elem);
                return;
            }
            if (this.indices.get(i) > col) {
                if (Math.abs(elem) < 10E-6)
                    return;
                for (int k = row + 1; k < indPtr.size(); ++k)
                    indPtr.set(k, indPtr.get(k) + 1);
                this.data.add(0d);
                this.indices.add(0);
                for (int k = this.data.size() - 2; k >= i; --k) {
                    this.data.set(k + 1, this.data.get(k));
                    this.indices.set(k + 1, this.indices.get(k));
                }
                this.data.set(i, elem);
                this.indices.set(i, col);
                return;
            }
        }
        if (Math.abs(elem) < 10E-6)
            return;
        for (int k = row + 1; k < indPtr.size(); ++k)
            indPtr.set(k, indPtr.get(k) + 1);
        this.data.add(0d);
        this.indices.add(0);
        for (int k = this.data.size() - 2; k >= elementsBefore + elementsInRow; --k) {
            this.data.set(k + 1, this.data.get(k));
            this.indices.set(k + 1, this.indices.get(k));
        }
        this.data.set(elementsBefore + elementsInRow, elem);
        this.indices.set(elementsBefore + elementsInRow, col);



        /*ArrayMatrix matrix = new ArrayMatrix<>(this);
        matrix.set(elem, row, col);
        List<Double> data = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Integer> indPtr = new ArrayList<>();
        indPtr.add(1);
        for (int i = 0; i < matrix.getMatrixDimensionM(); ++i) {
            int notNull = 0;
            for (int j = 0; j < matrix.getMatrixDimensionN(); ++j) {
                double element = matrix.get(i, j);
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
