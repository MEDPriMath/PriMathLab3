package ru.itmo.primath.lab3.matrix;

import java.lang.reflect.Constructor;

public abstract class Matrix {

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

    public abstract double get(int row, int col);

    public abstract void set(double elem, int row, int col);

    public int getMatrixDimensionM() {
        return matrixDimensionM;
    }

    public int getMatrixDimensionN() {
        return matrixDimensionN;
    }

    public Matrix sum(Matrix otherMatrix) {
        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            throw new IllegalArgumentException();

        Matrix resultMatrix = new ArrayMatrix(this.matrixDimensionM, this.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i) {
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j) {
                resultMatrix.set(this.get(i, j) + otherMatrix.get(i, j), i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix sum(Matrix otherMatrix, Class<?> type) throws Exception {
        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            throw new IllegalArgumentException();

        Constructor<?> constructor = type.getConstructor(int.class, int.class);
        Matrix resultMatrix = (Matrix) constructor.newInstance(this.matrixDimensionM, otherMatrix.matrixDimensionN);

        for (int i = 0; i < matrixDimensionM; ++i) {
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j) {
                resultMatrix.set(this.get(i, j) + otherMatrix.get(i, j), i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix multiply(Matrix otherMatrix) {
        if (this.matrixDimensionN != otherMatrix.matrixDimensionM)
            throw new IllegalArgumentException();
        Matrix resultMatrix = new ArrayMatrix(this.matrixDimensionM, otherMatrix.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i) {
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j) {
                Double elem = 0d;
                for (int k = 0; k < matrixDimensionN; ++k) {
                    elem += this.get(i, k) * otherMatrix.get(k, j);
                }
                resultMatrix.set(elem, i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix multiply(Matrix otherMatrix, Class type) throws Exception {
        if (this.matrixDimensionN != otherMatrix.matrixDimensionM)
            throw new IllegalArgumentException();

        Constructor constructor = type.getConstructor(int.class, int.class);

        Matrix resultMatrix = (Matrix) constructor.newInstance(this.matrixDimensionM, otherMatrix.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i) {
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j) {
                Double elem = 0d;
                for (int k = 0; k < matrixDimensionN; ++k) {
                    elem += this.get(i, k) * otherMatrix.get(k, j);
                }
                resultMatrix.set(elem, i, j);
            }
        }

        return resultMatrix;
    }

    public boolean isSquare() {
        return matrixDimensionM == matrixDimensionN;
    }

    public boolean isLowerTriangular() {
        if (!this.isSquare())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i) {
            for (int j = i + 1; j < this.getMatrixDimensionN(); ++j) {
                if (Math.abs(this.get(i, j) - 0) > 10E-6)
                    return false;
            }
        }

        return true;
    }

    public boolean isUpperTriangular() {
        if (!this.isSquare())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (Math.abs(this.get(i, j) - 0) > 10E-6)
                    return false;
            }
        }

        return true;
    }

    public void print() {
        print(1);
    }

    public void print(int precision) {
        int largest = 0;
        for (int i = 0; i < this.getMatrixDimensionM(); ++i) {
            for (int j = 0; j < this.getMatrixDimensionN(); ++j) {
                if (String.format("%." + precision + "f", this.get(i, j)).length() > largest)
                    largest = String.format("%." + precision + "f", this.get(i, j)).length();
            }
        }
        for (int i = 0; i < this.getMatrixDimensionM(); ++i) {
            for (int j = 0; j < this.getMatrixDimensionN(); ++j) {
                System.out.print(String.format("%" + largest + "." + precision + "f", this.get(i, j)) + " ");
            }
            System.out.println();
        }
    }

    public static Matrix generateIdentityMatrix(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        Matrix matrix = new CSRMatrix(n);

        for (int i = 0; i < n; ++i) {
            matrix.set(1, i, i);
        }

        return matrix;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Matrix)) {
            return false;
        }

        Matrix otherMatrix = (Matrix) obj;

        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i) {
            for (int j = 0; j < this.getMatrixDimensionN(); ++j) {
                if (Math.abs(this.get(i, j) - otherMatrix.get(i, j)) > 10E-6)
                    return false;
            }
        }

        return true;

    }
}
