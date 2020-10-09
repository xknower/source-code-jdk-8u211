package com.xknower.multithread.chapter.eight;

/**
 * 矩阵计算
 *
 * @author xknower
 */
public class MyMatMult {

    public static void main(String[] args) {
        Matrix a = new Matrix(1, 3);

        a.setValue(0, 0, 1);
        a.setValue(0, 1, 2);
        a.setValue(0, 2, 3);

        dump(a);

        Matrix b = new Matrix(3, 2);
        b.setValue(0, 0, 4);
        b.setValue(1, 0, 5);
        b.setValue(2, 0, 6);
        b.setValue(0, 1, 7);
        b.setValue(1, 1, 8);
        b.setValue(2, 1, 9);
        dump(b);
        dump(multiply(a, b));
    }

    public static void dump(Matrix m) {
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                System.out.printf("%d ", m.getValue(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("row/columns mismatch");
        }

        Matrix result = new Matrix(a.getRows(), b.getCols());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                for (int k = 0; k < a.getCols(); k++) {
                    result.setValue(i, j, result.getValue(i, j) + a.getValue(i, k) * b.getValue(k, j));
                }
            }
        }
        return result;
    }
}

class Matrix {

    private final int[][] matrix;

    public Matrix(int nrows, int ncols) {
        matrix = new int[nrows][ncols];
    }

    public int getCols() {
        return matrix[0].length;
    }

    public int getRows() {
        return matrix.length;
    }

    public int getValue(int row, int col) {
        return matrix[row][col];
    }

    public void setValue(int row, int col, int value) {
        matrix[row][col] = value;
    }
}
