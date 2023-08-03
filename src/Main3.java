import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String firstRow = sc.nextLine();
        String[] firstRowInfo = firstRow.split(" ");
        int h = Integer.parseInt(firstRowInfo[0]);
        int w = Integer.parseInt(firstRowInfo[1]);


        char[][] map = new char[h][w];
        for (int i = 0; i < h; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < w; j++) {
                map[i][j] = row.charAt(j);
            }
        }

        System.out.println(getCount(map));
    }

    private static long getCount(char[][] map) {
        if (null == map) {
            return 0;
        }

        int row = map.length;
        if (row < 3) {
            return 0;
        }

        int col = map[0].length;
        if (col < 5) {
            return 0;
        }

        long result = 0;
        int endRow = row - 1;
        int endCol = col - 3;
        for (int i = 1; i < endRow; i++) {
            for (int j = 1; j < endCol; j++) {
                result += getTarPosCount(map, i, j, row, col);
            }
        }

        return result;
    }

    private static long getTarPosCount(char[][] map, int i, int j, int row, int col) {
        if (!checkHas3Start(map, i, j)) {
            return 0;
        }

        long leftUpCount = getLeftUpCount(map, i, j);
        if (leftUpCount < 1) {
            return 0;
        }

        long leftDownCount = getLeftDownCount(map, i, j, row);
        if (leftDownCount < 1) {
            return 0;
        }

        long rightUpCount = getRightUpCount(map, i, j, col);
        if (rightUpCount < 1) {
            return 0;
        }

        long rightDownCount = getRightDownCount(map, i, j, row, col);
        if (rightDownCount < 1) {
            return 0;
        }

        return leftUpCount * leftDownCount * rightUpCount * rightDownCount;
    }

    private static long getRightDownCount(char[][] map, int i, int j, int row, int col) {
        int counter = 0;
        for (int k = i + 1; k < row; k++) {
            for (int m = j + 3; m < col; m++) {
                if (map[k][m] == '*') {
                    ++counter;
                }
            }
        }

        return counter;
    }

    private static long getRightUpCount(char[][] map, int i, int j, int col) {
        int counter = 0;
        for (int k = 0; k < i; k++) {
            for (int m = j + 3; m < col; m++) {
                if (map[k][m] == '*') {
                    ++counter;
                }
            }
        }

        return counter;
    }

    private static long getLeftDownCount(char[][] map, int i, int j, int row) {
        int counter = 0;
        for (int k = i + 1; k < row; k++) {
            for (int m = 0; m < j; m++) {
                if (map[k][m] == '*') {
                    ++counter;
                }
            }
        }

        return counter;
    }

    private static long getLeftUpCount(char[][] map, int i, int j) {
        int counter = 0;
        for (int k = 0; k < i; k++) {
            for (int m = 0; m < j; m++) {
                if (map[k][m] == '*') {
                    ++counter;
                }
            }
        }

        return counter;
    }

    private static boolean checkHas3Start(char[][] map, int i, int j) {
        int endCol = j + 3;
        for (int k = j; k < endCol; k++) {
            if (map[i][k] != '*') {
                return false;
            }
        }

        return true;
    }

}


