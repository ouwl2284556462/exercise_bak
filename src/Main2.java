import java.util.Arrays;

public class Main2 {

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {1, 2, 3}};
        System.out.println(Arrays.deepToString(a));

        char b = 'a';
        short c = 1;
        byte d = 1;
        b = (char) d;
        System.out.println(b);

        String str = "";
        switch (str) {
            case "1":
                break;
        }
    }
}
