package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SudokuMatrix {

    // 当前构建的点坐标
    int x = 0;
    int y = 0;
    public static int size = 9;
    Cell[][] matrix = new Cell[size][size];

    public Cell getCurrentBuildCell() {
        if (matrix[y][x] == null) {
            matrix[y][x] = new Cell(x, y);
        }
        return matrix[y][x];
    }

    public List<Integer> getCurrentCellAvailableNum() {
        List<Integer> currentCellAvailable = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            currentCellAvailable.add(i);
        }

        for (int i = 0; i < x; i++) {
            Cell sameY = matrix[y][i];
            int index = currentCellAvailable.indexOf(sameY.exceptedNum);
            if (index != -1) {
                currentCellAvailable.remove(index);
            }
        }

        for (int i = 0; i < y; i++) {
            Cell sameX = matrix[i][x];
            int index = currentCellAvailable.indexOf(sameX.exceptedNum);
            if (index != -1) {
                currentCellAvailable.remove(index);
            }
        }

        return currentCellAvailable;
    }

    public void dump() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                Cell cell = matrix[i][j];
                if (cell != null) {
                    String index = (i * size + j + 1) >= 10 ? (i * size + j + 1) + "" : "0" + (i * size + j + 1);
                    System.out.print("Cell " + index + ": " + cell.exceptedNum + "   ");
                    if (j == size - 1) {
                        System.out.println();
                    }
                } else {
                    break;
                }
            }
    }
}

class Cell {

    int exceptedNum = -1;
    int x;
    int y;

    boolean isEmpty = true;
    boolean availNumNullNeedGoPreCell = false;

    List<Integer> availNum = null;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dump() {
        StringBuilder stringBuilder = new StringBuilder();
        if (availNum != null) {
            stringBuilder.append("avail num : ");
            for (int availNum : availNum) {
                stringBuilder.append(availNum + ", ");
            }
        } else {
            stringBuilder.append("null available num");
        }
        System.out.println("Build Cell " + (y * SudokuMatrix.size + x + 1) + " " + stringBuilder.toString());
    }
}

public class SudokuGame {


    // 深度优先搜索构建
    public static SudokuMatrix generateTraversingMatrix() {

        SudokuMatrix sudokuMatrix = new SudokuMatrix();

        while (true) {
            Cell c = sudokuMatrix.getCurrentBuildCell();
//            c.dump();
//            sudokuMatrix.dump();

            if (c.x + SudokuMatrix.size * c.y + 1 == 27) {
                int a = 1;
                a = a + 1;
            }

            List<Integer> availNumber = null;
            if (c.availNum == null) {
                availNumber = sudokuMatrix.getCurrentCellAvailableNum();
                c.availNum = new ArrayList<>();
                c.availNum.addAll(availNumber);
            } else {
                if (c.availNum.size() == 0) {
                    if (!c.availNumNullNeedGoPreCell) {
                        availNumber = sudokuMatrix.getCurrentCellAvailableNum();
                        c.availNum.addAll(availNumber);
                    } else {
                        c.availNumNullNeedGoPreCell = false;
                        availNumber = null;
                    }
                } else {
                    availNumber = c.availNum;
                }
            }

            try {
                if (availNumber != null && availNumber.size() > 0) {
                    c.exceptedNum = getOneAvailNum(c);
                    c.isEmpty = false;
                    if (sudokuMatrix.x == SudokuMatrix.size - 1 && sudokuMatrix.y == SudokuMatrix.size - 1) {
                        break;
                    } else {
                        moveToNextBuildCell(sudokuMatrix);
                    }
                } else {
                    if (sudokuMatrix.x == 0 && sudokuMatrix.y == 0) {
                        break;
                    } else {
                        c.exceptedNum = -1;
                        c.isEmpty = true;
                        moveToPreBuildCell(sudokuMatrix);
                    }
                }
            } catch (Exception e) {
                sudokuMatrix.dump();
                break;
            }

        }

        return sudokuMatrix;
    }

    public static int getOneAvailNum(Cell c) {
        List<Integer> availNum = c.availNum;

        if (availNum != null && availNum.size() > 0) {
            int nextAvail = 0;
            if (availNum.size() == 1) {
                nextAvail = availNum.get(0);
                c.availNumNullNeedGoPreCell = true;
                availNum.remove(0);
                return nextAvail;
            } else {

                Random random = new Random();
                int index = random.nextInt(availNum.size() - 1);
                nextAvail = availNum.get(index);
                availNum.remove(index);

                if (availNum.size() == 0) {
                    c.availNumNullNeedGoPreCell = true;
                }
                return nextAvail;
            }
        }
        return -1;
    }

    public static void moveToNextBuildCell(SudokuMatrix matrix) {
        int size = SudokuMatrix.size;
        if (matrix.x < size - 1) {
            matrix.x += 1;
        } else if (matrix.x == size - 1) {
            if (matrix.y < size - 1) {
                matrix.x = 0;
                matrix.y += 1;
            }
        }
    }

    public static void moveToPreBuildCell(SudokuMatrix matrix) {
        int size = SudokuMatrix.size;
        if (matrix.x > 0) {
            matrix.x -= 1;
        } else if (matrix.x == 0) {
            if (matrix.y > 0) {
                matrix.x = size - 1;
                matrix.y -= 1;
            }
        }
    }

    public static void main(String[] args) {
        SudokuMatrix sudokuMatrix = generateTraversingMatrix();

        sudokuMatrix.dump();
    }
}
