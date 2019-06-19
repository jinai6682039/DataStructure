package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// 编程之美2.21
public class FindContinuousNumSumEqualN {

    // 埃拉特斯特尼筛法
    private static List<Integer> findAllPrimeNum(int n) {

        int[] prime = new int[n + 1];
        List<Integer> primeList = new ArrayList<>();

        for (int i = 2; i < n; i++) {
            if (prime[i] == 0) {
                primeList.add(i);
                for (int j = i * i; j < n; j += i) {
                    prime[j] = 1;
                }
            }
        }

        return primeList;
    }

    // 欧拉筛法
    private static List<Integer> findAllPrimeNumPro(int n) {

        int[] prime = new int[n + 1];
        List<Integer> primeList = new ArrayList<>();

        for (int i = 2; i < n; i++) {
            if (prime[i] == 0) {
                primeList.add(i);
            }
            for (int j = 0; j < primeList.size() && i * primeList.get(j) <= n; j++) {

                prime[i * primeList.get(j)] = 1;
                if (i % primeList.get(j) == 0) {
                    break;
                }
            }
        }

        return primeList;
    }

    // 先求出小于n的所有质数，再去除
    private static HashMap<Integer, Integer> findAllPrimeFactor(int n) {
        HashMap<Integer, Integer> result = new HashMap<>();

        List<Integer> primeNumBelow = findAllPrimeNumPro(n);
        while (n > 1) {
            for (int i = 0; i < primeNumBelow.size(); ) {
                if (n % primeNumBelow.get(i) == 0) {
                    if (result.containsKey(primeNumBelow.get(i))) {
                        int count = result.get(primeNumBelow.get(i));
                        result.put(primeNumBelow.get(i), count + 1);
                    } else {
                        result.put(primeNumBelow.get(i), 1);
                    }
                    n = n / primeNumBelow.get(i);
                } else {
                    i++;
                }
            }
        }

        return result;
    }

    // 不用先求出小于n的质数
    private static HashMap<Integer, Integer> findAllPrimeFactorPro(int n) {
        HashMap<Integer, Integer> result = new HashMap<>();

        while (n > 1) {
            for (int i = 2; i <= n; ) {
                if (n % i == 0) {
                    if (result.containsKey(i)) {
                        int count = result.get(i);
                        result.put(i, count + 1);
                    } else {
                        result.put(i, 1);
                    }
                    n = n / i;
                } else {
                    i++;
                }
            }
        }

        return result;
    }

    private static List<int[]> findContinuousArray(int n) {
        HashMap<Integer, Integer> primeFactors = findAllPrimeFactorPro(2 * n);

        int[] startXY = {1, 1};

        // 2为质因数的乘积
        int evenProduct = 1;

        List<int[]> result = new ArrayList<>();
        result.add(startXY);

        List<Integer> multiplationFactors = new ArrayList<>();

        Iterator iterator = primeFactors.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterator.next();

            for (int i = 0; i < entry.getValue(); i++) {
                if (entry.getKey() == 2) {
                    evenProduct *= 2;
                } else {
                    multiplationFactors.add(entry.getKey());
                }
            }
        }

        // 计算除2以外的质因子
        for (Integer multiplationFactor : multiplationFactors) {

            List<int[]> middleResult = new ArrayList<>();
            for (int[] lastResult : result) {

                middleResult.addAll(freeCombination(lastResult, multiplationFactor));
            }

            result = middleResult;
        }

        // 计算2为质因子的情况
        List<int[]> middleResult = new ArrayList<>();
        for (int[] middleArray : result) {
            middleResult.addAll(freeCombination(middleArray, evenProduct));
        }

        result.clear();
        for (int[] middleArray : middleResult) {
            int x = middleArray[0];
            int y = middleArray[1];
            if (x < y) {
                x = middleArray[1];
                y = middleArray[0];
            }

            int[] singleResult = {(x - y + 1) / 2, (x + y - 1) / 2};
            result.add(singleResult);
        }

        return result;
    }

    private static List<int[]> freeCombination(int[] arraySet, int multiplationFactor) {
        List<int[]> result = new ArrayList<>();

        int[] result1 = {arraySet[0] * multiplationFactor, arraySet[1]};
        int[] result2 = {arraySet[0], arraySet[1] * multiplationFactor};

        result.add(result1);
        result.add(result2);

        return result;
    }

    public static void main(String[] args) {
        findAllPrimeNum(100);
        findAllPrimeNumPro(100);

        findAllPrimeFactor(99);
        findAllPrimeFactorPro(99);

        findContinuousArray(99);
    }
}
