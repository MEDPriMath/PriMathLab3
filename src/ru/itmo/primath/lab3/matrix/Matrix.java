package ru.itmo.primath.lab3.matrix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Matrix<T extends Number> {

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

    public abstract T get(int row, int col);
    public abstract void set(T elem, int row, int col);

    public int getMatrixDimensionM() {
        return matrixDimensionM;
    }

    public int getMatrixDimensionN() {
        return matrixDimensionN;
    }

    public Matrix<Double> sum(Matrix<? extends Number> otherMatrix){
        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            throw new IllegalArgumentException();

        Matrix<Double> resultMatrix = new ArrayMatrix<>(this.matrixDimensionM, this.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i){
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j){
                resultMatrix.set(this.get(i, j).doubleValue() + otherMatrix.get(i, j).doubleValue(), i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix<Double> sum(Matrix<? extends Number> otherMatrix, Class type) throws Exception {
        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            throw new IllegalArgumentException();

        Constructor constructor = type.getConstructor(int.class, int.class);
        Matrix<Double> resultMatrix = (Matrix<Double>) constructor.newInstance(this.matrixDimensionM, otherMatrix.matrixDimensionN);

        for (int i = 0; i < matrixDimensionM; ++i){
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j){
                resultMatrix.set(this.get(i, j).doubleValue() + otherMatrix.get(i, j).doubleValue(), i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix<Double> multiply(Matrix<? extends Number> otherMatrix){
        if (this.matrixDimensionN != otherMatrix.matrixDimensionM)
            throw new IllegalArgumentException();
        Matrix<Double> resultMatrix = new ArrayMatrix<>(this.matrixDimensionM, otherMatrix.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i){
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j){
                Double elem = 0d;
                for (int k = 0; k < matrixDimensionN; ++k){
                    elem += this.get(i, k).doubleValue() * otherMatrix.get(k, j).doubleValue();
                }
                resultMatrix.set(elem, i, j);
            }
        }

        return resultMatrix;
    }

    public Matrix<Double> multiply(Matrix<? extends Number> otherMatrix, Class type) throws Exception {
        if (this.matrixDimensionN != otherMatrix.matrixDimensionM)
            throw new IllegalArgumentException();

        Constructor constructor = type.getConstructor(int.class, int.class);

        Matrix<Double> resultMatrix = (Matrix<Double>) constructor.newInstance(this.matrixDimensionM, otherMatrix.matrixDimensionN);
        for (int i = 0; i < matrixDimensionM; ++i){
            for (int j = 0; j < otherMatrix.matrixDimensionN; ++j){
                Double elem = 0d;
                for (int k = 0; k < matrixDimensionN; ++k){
                    elem += this.get(i, k).doubleValue() * otherMatrix.get(k, j).doubleValue();
                }
                resultMatrix.set(elem, i, j);
            }
        }

        return resultMatrix;
    }

    public boolean isSquare(){
        return matrixDimensionM == matrixDimensionN;
    }
    public boolean isLowerTriangular(){
        if (!this.isSquare())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i){
            for (int j = i + 1; j < this.getMatrixDimensionN(); ++j){
                if (Math.abs(this.get(i, j).doubleValue() - 0) > 10E-6)
                    return false;
            }
        }

        return true;
    }

    public boolean isUpperTriangular(){
        if (!this.isSquare())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i){
            for (int j = 0; j < i; ++j){
                if (Math.abs(this.get(i, j).doubleValue() - 0) > 10E-6)
                    return false;
            }
        }

        return true;
    }

    public void print() {
        print(1);
    }

    public void print(int precision){
        int largest = 0;
        for (int i = 0; i < this.getMatrixDimensionM(); ++i){
            for (int j = 0; j < this.getMatrixDimensionN(); ++j){
                if (String.format("%."+precision+"f", this.get(i, j).doubleValue()).length() > largest)
                    largest = String.format("%."+precision+"f", this.get(i, j).doubleValue()).length();
            }
        }
        for (int i = 0; i < this.getMatrixDimensionM(); ++i){
            for (int j = 0; j < this.getMatrixDimensionN(); ++j){
                System.out.print(String.format("%"+largest+"."+precision+"f", this.get(i, j).doubleValue()) + " ");
            }
            System.out.println();
        }
    }

    public static<T extends Number> Matrix<T> generateIdentityMatrix(int n){
        if (n <= 0)
            throw new IllegalArgumentException();

        Matrix<T> matrix = new CSRMatrix<>(n, (T)Double.valueOf(0));

        for (int i = 0; i < n; ++i){
            matrix.set((T)Double.valueOf(1), i, i);
        }

        return matrix;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Matrix)){
            return false;
        }

        Matrix otherMatrix = (Matrix) obj;

        if (this.getMatrixDimensionM() != otherMatrix.getMatrixDimensionM() || this.getMatrixDimensionN() != otherMatrix.getMatrixDimensionN())
            return false;

        for (int i = 0; i < this.getMatrixDimensionM(); ++i){
            for (int j = 0; j < this.getMatrixDimensionN(); ++j){
                if (Math.abs(this.get(i, j).doubleValue() - otherMatrix.get(i, j).doubleValue()) > 10E-6)
                    return false;
            }
        }

        return true;

    }
}
