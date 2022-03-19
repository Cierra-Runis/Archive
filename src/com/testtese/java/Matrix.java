package com.testtese.java;

import java.util.Scanner;

public class Matrix {

    double[][] matrix;                      //成员变量为矩阵
    String name;                            //名称

    //使用含参数的构造方法时提供名称、行数及列数
    public Matrix(String name, int row, int col) {
        this.matrix = new double[row][col];
        this.name = name;
    }

    //显示矩阵
    public void showMatrix() {

        int[] maxlenofcol = new int[matrix[0].length];          //这里找出每列最长数的长度保存按列存入 []maxlenofcol
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                String str = String.valueOf(matrix[i][j]);
                if (str.length() > maxlenofcol[j]) {
                    maxlenofcol[j] = str.length();
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {               //按行输出

            //输出左框架
            if (matrix.length == 1) {                           //仅一行时特殊处理
                System.out.printf("%s = [  ", name);            //输出
                for (int j = 0; j < matrix[0].length; j++) {    //这里输出每一列的元素
                    System.out.printf("%s", matrix[i][j]);
                    //下句保证左对齐
                    Showing.Blank(maxlenofcol[j] + 1 - String.valueOf(matrix[i][j]).length());
                }
            } else {                                            //出现多行时
                if (i == 0) {                                   //对于第一行
                    if (matrix.length == 2) {                   //若一共两行，输出实际上和仅一行时仅有符号的差别
                        System.out.printf("%s = ┌  ", name);
                    } else {
                        Showing.Blank(name.length() + 2);  //对于三行及以上，第一行总是会先输出如左多的空格对齐
                        System.out.print("┌  ");
                    }
                }
                //而对于其他行
                else if (i == matrix.length - 1) {              //对于最后一行，也是符号的差别
                    Showing.Blank(name.length() + 2);
                    System.out.print("└  ");
                } else if (i == ((matrix.length - 1) / 2)) {    //对于居中行，输出名称、等于号和竖线
                    System.out.printf("%s = │  ", name);
                } else {                                        //对于普通行，输出左对齐竖线
                    Showing.Blank(name.length() + 2);
                    System.out.print("│  ");
                }

                //对于每一行，输出元素时
                for (int j = 0; j < matrix[0].length; j++) {    //先输出元素，再输出保证左对齐的空格们
                    System.out.printf("%s", matrix[i][j]);
                    Showing.Blank(maxlenofcol[j] + 1 - String.valueOf(matrix[i][j]).length());
                }
            }

            //输出右框架
            if (matrix.length == 1) {
                System.out.print("]\n");
            } else {
                if (i == 0) {
                    System.out.print("┐\n");
                } else if (i == matrix.length - 1) {
                    System.out.print("┘\n");
                } else {
                    System.out.print("│\n");
                }
            }
        }
        System.out.print("\n");             //每一行都来个换行
        //至此输出完毕
    }

    //设定矩阵，不赘述
    public void setMatrix() {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("Enter %s[%d][%d] = ", name, i + 1, j + 1);
                matrix[i][j] = input.nextDouble();
            }
        }
        System.out.print("Done.\n");
        showMatrix();
    }

    //矩阵的和运算
    public Matrix addMatrix(Matrix addmatrix) {

        //先判断两者行列是否相等，否则提示错误并退出
        if ((matrix.length != addmatrix.matrix.length) || (matrix[0].length != addmatrix.matrix[0].length)) {
            System.out.print("\33[31;1mError, they're not same kind of matrix.\33[0m\n");
            return null;
        }

        //设定一个新的矩阵 result 接收结果，名称为 <前矩阵>+<后矩阵>
        Matrix result = new Matrix(name + "+" + addmatrix.name, matrix.length, matrix[0].length);

        for (int i = 0; i < matrix.length; i++) {               //和运算步骤
            for (int j = 0; j < matrix[0].length; j++) {
                result.matrix[i][j] = matrix[i][j] + addmatrix.matrix[i][j];
            }
        }

        return result;                                          //最后返回 result 矩阵
    }

    //显示行列式
    public void showDet() {

        //先判断是否为方阵，否则提示错误并退出
        if (matrix.length != matrix[0].length) {
            System.out.print("\33[31;1mError, it's not a n*n matrix.\33[0m\n\n");
            return;
        }

        int[] maxlenofcol = new int[matrix[0].length];          //这里找出每列最长数的长度保存按列存入 []maxlenofcol
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                String str = String.valueOf(matrix[i][j]);
                if (str.length() > maxlenofcol[j]) {
                    maxlenofcol[j] = str.length();
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {               //按行输出

            String namae = "det(" + name + ")";                 //新字符串 namae 替代 name 输出

            if (i == ((matrix.length - 1) / 2)) {               //对于居中行，输出名称、等于号和竖线
                System.out.printf("%s = |  ", namae);
            } else {                                            //对于普通行，输出左对齐竖线
                Showing.Blank(namae.length() + 2);
                System.out.print("|  ");
            }
            //对于每一行，输出元素时
            for (int j = 0; j < matrix[0].length; j++) {        //先输出元素，再输出保证左对齐的空格们
                System.out.printf("%s", matrix[i][j]);
                Showing.Blank(maxlenofcol[j] + 1 - String.valueOf(matrix[i][j]).length());
            }

            //输出右框架
            if (i == ((matrix.length - 1) / 2)) {
                System.out.print("| = " + detOfMatrix() + "\n");
            } else {
                System.out.print("|\n");
            }
        }
        System.out.print("\n");             //每一行都来个换行
        //至此输出完毕
    }

    //矩阵的“余矩阵”
    public Matrix minorOfMatrix(int row, int col) {

        //命名以及框定大小
        Matrix minorofmatrix = new Matrix("Minor of " + name + "'s " + (row + 1) + "_" + (col + 1), matrix.length - 1, matrix.length - 1);
        //下面是算法，细节略
        int rownum = 0;
        int colnum = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == row) {
                continue;
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == col) {
                    continue;
                }
                minorofmatrix.matrix[rownum][colnum % (matrix[0].length - 1)] = matrix[i][j];
                colnum++;
            }
            rownum++;
        }
        return minorofmatrix;               //最终返回一个矩阵
    }

    //矩阵的行列式
    public double detOfMatrix() {

        //先判断是否为方阵，否则提示错误并退出
        if (matrix.length != matrix[0].length) {
            System.out.print("\33[31;1mError, it's not a n*n matrix.\33[0m\n\n");
            return 0;
        }

        if (matrix.length == 1) {                               //当行列式一阶是直接输出该值
            return matrix[0][0];
        } else if (matrix.length == 2) {                        //当行列式二阶时直接使用公式
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {                                                //否则按行展开
            double result = 0;
            for (int i = 0; i < matrix.length; i++) {
                result += Math.pow(-1, i) * matrix[i][0] * minorOfMatrix(i, 0).detOfMatrix();
            }
            return result;
        }
    }
}