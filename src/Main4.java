import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Main4 {


    public static final int MOD_COUNT = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());


        int[] numInfo = new int[10];
        String secLine = sc.nextLine();
        String[] lineInfo = secLine.split(" ");
        for (int i = 0; i < numInfo.length; i++) {
            numInfo[i] = Integer.parseInt(lineInfo[i]);
        }

        System.out.println(getResult(n, numInfo));
    }

    private static long getResult(int n, int[] numInfo) {
        Set<Integer> needNum = new HashSet<Integer>();
        Set<Integer>[] posInfo = new Set[n];

        //init pre calculate info
        for (int i = 0; i < numInfo.length; i++) {
            int endPos = numInfo[i];
            if (endPos < 1) {
                continue;
            }

            //need num
            needNum.add(i);
            addPosInfo(posInfo, endPos, i);
        }

        int needNumSize = needNum.size();
        if (needNumSize < 1 || needNumSize > n) {
            return 0;
        }

        if (!checkPosInfoInvalid(posInfo)) {
            // some position has not valid number
            return 0;
        }

        long[] cache = new long[n];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = -1;
        }


        return doCalculate(needNum, posInfo, n, 0, cache) % MOD_COUNT;
    }

    private static boolean checkPosInfoInvalid(Set<Integer>[] posInfo) {
        for (Set<Integer> numbers : posInfo) {
            if (null == numbers || numbers.size() < 1) {
                return false;
            }
        }

        return true;
    }

    private static long doCalculate(Set<Integer> needNum, Set<Integer>[] posInfo, int n, int curIndex, long[] cache) {
        int needNumSize = needNum.size();
        if (curIndex >= n) {
            return needNumSize > 0 ? 0 : 1;
        }


        int remain = n - curIndex;
        if (needNumSize > remain) {
            return 0;
        }

        if (needNumSize < 1) {
            long cacheCount = cache[curIndex];
            if (cacheCount > -1) {
                return cacheCount;
            }

            long result = 1;
            for (int i = curIndex; i < n; i++) {
                int tarCount = posInfo[i].size();
                if (tarCount > 1) {
                    result *= tarCount;
                    result %= MOD_COUNT;
                }
            }

            cache[curIndex] = result;
            return result;
        }

        Set<Integer> newNeedNum = new HashSet<>(needNum);
        Set<Integer> numInPosition = posInfo[curIndex];

        if (!numInPosition.containsAll(newNeedNum)) {
            return 0;
        }

        int nextIndex = curIndex + 1;
        long result = 0;
        for (Integer num : needNum) {
            if (!numInPosition.contains(num)) {
                continue;
            }

            newNeedNum.remove(num);
            long tarCount = doCalculate(newNeedNum, posInfo, n, nextIndex, cache);
            if (tarCount > 0) {
                result += tarCount;
                result %= MOD_COUNT;
            }

            newNeedNum.add(num);
        }

        if (curIndex != 0 && !needNum.containsAll(numInPosition)) {
            long tarCount = doCalculate(newNeedNum, posInfo, n, nextIndex, cache);
            if (tarCount > 0) {
                result += tarCount;
                result %= MOD_COUNT;
            }
        }

        return result;
    }

    private static void addPosInfo(Set<Integer>[] posInfo, int endPos, int num) {
        for (int i = 0; i < endPos; i++) {
            Set<Integer> numsInPosition = posInfo[i];
            if (null == numsInPosition) {
                numsInPosition = new HashSet<>();
                posInfo[i] = numsInPosition;
            }

            numsInPosition.add(num);
        }
    }


}


