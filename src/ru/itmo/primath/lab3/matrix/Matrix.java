package ru.itmo.primath.lab3.matrix;

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

    public Matrix<Double> multiply(Matrix<? extends Number> otherMatrix){
        if (this.matrixDimensionN != otherMatrix.matrixDimensionM)
            throw new IllegalArgumentException();
        ArrayMatrix<Double> resultMatrix = new ArrayMatrix<>(this.matrixDimensionM, otherMatrix.matrixDimensionN);
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
}
