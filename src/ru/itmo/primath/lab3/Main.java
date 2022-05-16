package ru.itmo.primath.lab3;

import ru.itmo.primath.lab3.matrix.ArrayMatrix;
import ru.itmo.primath.lab3.matrix.CSRMatrix;

public class Main {
    public static void main(String[] args) {
        System.out.println("Here we go again");
        /*List<Integer> data = Arrays.asList(1, 1, 1, 3, 2);
        List<Integer> indices = Arrays.asList(0, 2, 1, 0, 1);
        List<Integer> indPtr = Arrays.asList(1, 3, 4, 6);
        CSRMatrix2<Integer> csrMatrix2 = new CSRMatrix2<>(3, 3, 0, data, indices, indPtr);
        for (int i = 0; i < csrMatrix2.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix2.getMatrixDimensionN(); ++j){
                System.out.print(csrMatrix2.get(i, j) + " ");
            }
            System.out.println();
        }*/

        ArrayMatrix<Integer> arrayMatrix2 = new ArrayMatrix<>(new Integer[][]{{1, 0, 1}, {0, 1, 0}, {3, 2, 0}});
        CSRMatrix<Integer> csrMatrix2 = new CSRMatrix<>(arrayMatrix2, 0);
        for (int i = 0; i < csrMatrix2.getMatrixDimensionM(); ++i){
            for (int j = 0; j < csrMatrix2.getMatrixDimensionN(); ++j){
                System.out.print(csrMatrix2.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}