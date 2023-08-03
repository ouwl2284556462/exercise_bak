import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());


        List<Cube> cubes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] infos = sc.nextLine().split(" ");
            int h = Integer.parseInt(infos[0]);
            int w = Integer.parseInt(infos[1]);
            int d = Integer.parseInt(infos[2]);
            cubes.add(new Cube(h, w, d));
        }

        System.out.println(getMaxHeight(cubes));
    }

    private static int getMaxHeight(List<Cube> cubes) {
        if (null == cubes || cubes.size() == 0) {
            return 0;
        }


        //sort area
        cubes.sort(Comparator.comparingInt(Cube::getArea));

        return doGetMaxHeight(cubes, null, cubes.size() - 1);
    }

    private static int doGetMaxHeight(List<Cube> cubes, Cube preCube, int curIndex) {
        if (curIndex < 0) {
            return 0;
        }

        int maxHeight = 0;
        for (int i = curIndex; i >= 0; i--) {
            Cube curCube = cubes.get(i);
            if (null != preCube && !preCube.canPut(curCube)) {
                continue;
            }

            int height = curCube.getH() + doGetMaxHeight(cubes, curCube, i - 1);
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }


    public static class Cube {
        private int h;

        private int w;

        private int d;

        public Cube(int h, int w, int d) {
            this.h = h;
            this.w = w;
            this.d = d;
        }

        public int getArea() {
            return w * d;
        }

        public int getH() {
            return h;
        }

        public int getW() {
            return w;
        }

        public int getD() {
            return d;
        }

        @Override
        public String toString() {
            return "Cube{" +
                    "h=" + h +
                    ", w=" + w +
                    ", d=" + d +
                    '}';
        }

        public boolean canPut(Cube curCube) {
            if (getArea() < curCube.getArea()) {
                return false;
            }

            if(w >= curCube.getW() && d >= curCube.getD()){
                return true;
            }

            if(w >= curCube.getD() && d >= curCube.getW()){
                return true;
            }


            return false;
        }
    }
}


