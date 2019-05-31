package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Point24 {

    static double result = 24.0d;
    static double threshold = 0.000001;

    // 穷举法
    public static boolean point24Exhaustion(double[] number, String[] expnum, int n) {
        if (n == 1) {
            if (Math.abs(number[0] - result) < threshold) {
                System.out.println(expnum[0]);
                return true;
            } else {
                return false;
            }
        } else {

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double a, b;
                    String expa, expb;

                    a = number[i];
                    b = number[j];

                    expa = expnum[i];
                    expb = expnum[j];

                    // a + b
                    double[] numberMiddle = new double[n - 1];
                    String[] expnumMiddle = new String[n - 1];

                    expnumMiddle[0] = "(" + expa + " + " + expb + ")";
                    numberMiddle[0] = a + b;

                    for (int k = 0, m = 1; k < n; k++) {
                        if (k == j || k == i) {
                            continue;
                        }
                        if (n == 2) {
                            break;
                        }
                        numberMiddle[m] = number[k];
                        expnumMiddle[m] = expnum[k];
                        m++;
                    }

                    if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                        return true;
                    }

                    // a * b
                    numberMiddle = new double[n - 1];
                    expnumMiddle = new String[n - 1];

                    expnumMiddle[0] = "(" + expa + " * " + expb + ")";
                    numberMiddle[0] = a * b;

                    for (int k = 0, m = 1; k < n; k++) {
                        if (k == j || k == i) {
                            continue;
                        }
                        if (n == 2) {
                            break;
                        }
                        numberMiddle[m] = number[k];
                        expnumMiddle[m] = expnum[k];
                        m++;
                    }

                    if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                        return true;
                    }

                    // a - b
                    numberMiddle = new double[n - 1];
                    expnumMiddle = new String[n - 1];

                    expnumMiddle[0] = "(" + expa + " - " + expb + ")";
                    numberMiddle[0] = a - b;

                    for (int k = 0, m = 1; k < n; k++) {
                        if (k == j || k == i) {
                            continue;
                        }
                        if (n == 2) {
                            break;
                        }
                        numberMiddle[m] = number[k];
                        expnumMiddle[m] = expnum[k];
                        m++;
                    }

                    if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                        return true;
                    }

                    // b - a
                    numberMiddle = new double[n - 1];
                    expnumMiddle = new String[n - 1];

                    expnumMiddle[0] = "(" + expb + " - " + expa + ")";
                    numberMiddle[0] = b - a;

                    for (int k = 0, m = 1; k < n; k++) {
                        if (k == j || k == i) {
                            continue;
                        }
                        if (n == 2) {
                            break;
                        }
                        numberMiddle[m] = number[k];
                        expnumMiddle[m] = expnum[k];
                        m++;
                    }

                    if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                        return true;
                    }

                    // a / b
                    if (b != 0) {

                        numberMiddle = new double[n - 1];
                        expnumMiddle = new String[n - 1];

                        expnumMiddle[0] = "(" + expa + " / " + expb + ")";
                        numberMiddle[0] = a / b;

                        for (int k = 0, m = 1; k < n; k++) {
                            if (k == j || k == i) {
                                continue;
                            }
                            if (n == 2) {
                                break;
                            }
                            numberMiddle[m] = number[k];
                            expnumMiddle[m] = expnum[k];
                            m++;
                        }

                        if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                            return true;
                        }
                    }

                    // b / a
                    if (a != 0) {

                        numberMiddle = new double[n - 1];
                        expnumMiddle = new String[n - 1];

                        expnumMiddle[0] = "(" + expb + " / " + expa + ")";
                        numberMiddle[0] = b / a;

                        for (int k = 0, m = 1; k < n; k++) {
                            if (k == j || k == i) {
                                continue;
                            }
                            if (n == 2) {
                                break;
                            }
                            numberMiddle[m] = number[k];
                            expnumMiddle[m] = expnum[k];
                            m++;
                        }

                        if (point24Exhaustion(numberMiddle, expnumMiddle, n - 1)) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static Map<Integer, List<Double>> dynamicPlanMap = new HashMap<>();

    // 动态规划 + 去除无用计算
    // 用四位二进制数来表明除非空和非全选的真子集 0x0001 - 0x1110
    private static List<Double> dynamocPlanCalculation(List<Double> numberSet, int dynamicPlanIndex) {
        if (dynamicPlanMap.containsKey(dynamicPlanIndex)) {
            return dynamicPlanMap.get(dynamicPlanIndex);
        }

        if (numberSet.size() == 1) {

            dynamicPlanMap.put(dynamicPlanIndex, numberSet);
            return numberSet;
        } else if (numberSet.size() == 2) {

            List<Double> resultSet = calculationTwoNumber(numberSet);
            dynamicPlanMap.put(dynamicPlanIndex, resultSet);
            return resultSet;
        } else if (numberSet.size() == 3) {

            List<Double> resultSet = new ArrayList<>();

            int indexSet[] = new int[3];
            for (int i = 0, m = 0; i < 4; i++) {
                int index = dynamicPlanIndex & (int) Math.pow(2, i);
                if (index != 0) {
                    indexSet[m] = index;
                    m++;
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 3; j++) {
                    int currentDynamicIndex = i + j;
                    List<Double> currentDynamicNumberParams = new ArrayList<>();

                    currentDynamicNumberParams.add(numberSet.get(i));
                    currentDynamicNumberParams.add(numberSet.get(j));

                    List<Double> middleResult = dynamocPlanCalculation(currentDynamicNumberParams, currentDynamicIndex);

                    int singleParamsIndex = 3 - i - j;
                    double singleParam = numberSet.get(singleParamsIndex);

                    for (Double param : middleResult) {
                        List<Double> params = new ArrayList<>();
                        params.add(param);
                        params.add(singleParam);
                        List<Double> results = calculationTwoNumber(params);

                        for (Double result : results) {
                            if (!resultSet.contains(result)) {
                                resultSet.add(result);
                            }
                        }
                    }
                }
            }

            dynamicPlanMap.put(dynamicPlanIndex, resultSet);
            return resultSet;
        } else if (numberSet.size() == 4) {

            List<Double> resultSet = new ArrayList<>();

            int max = (int) Math.pow(2, 4);
            for (int i = 0; i < max; i++) {
                if (i == 0 || i == max - 1) {
                    continue;
                }

                int indexA = 0;
                int indexB = 0;
                List<Double> paramsA = new ArrayList<>();
                List<Double> paramsB = new ArrayList<>();

                for (int j = 0; j < 4; j++) {
                    int index = i & (int) Math.pow(2, j);
                    if (index != 0) {
                        indexA += (int) Math.pow(2, j);
                        paramsA.add(numberSet.get(j));
                    } else {
                        indexB += (int) Math.pow(2, j);
                        paramsB.add(numberSet.get(j));
                    }
                }

                List<Double> resultASet = dynamocPlanCalculation(paramsA, indexA);
                List<Double> resultBSet = dynamocPlanCalculation(paramsB, indexB);

                for (Double resultA : resultASet) {
                    for (Double resultB : resultBSet) {
                        List<Double> params = new ArrayList<>();
                        params.add(resultA);
                        params.add(resultB);
                        List<Double> results = calculationTwoNumber(params);

                        for (Double result : results) {
                            if (!resultSet.contains(result)) {
                                resultSet.add(result);
                            }
                        }
                    }
                }
            }

            return resultSet;
        }

        return null;
    }

    private static List<Double> calculationTwoNumber(List<Double> numberSet) {
        List<Double> resultSet = new ArrayList<>();
        double a, b;
        a = numberSet.get(0);
        b = numberSet.get(1);
        resultSet.add(a + b);

        resultSet.add(a - b);
        resultSet.add(b - a);

        resultSet.add(a * b);

        if (b != 0) {
            resultSet.add(a / b);
        }

        if (a != 0) {
            resultSet.add(b / a);
        }
        return resultSet;
    }

    public static void main(String[] main) {
        double[] number = {5, 5, 5, 1};
        String[] expnum = {number[0] + "", number[1] + "", number[2] + "", number[3] + ""};

        if (!point24Exhaustion(number, expnum, 4)) {
            System.out.println("no solution");
        }

        List<Double> data = new ArrayList<>();
        data.add(1d);
        data.add(5d);
        data.add(5d);
        data.add(5d);

        List<Double> resultSet = dynamocPlanCalculation(data, 15);
        System.out.println("result :");
        for (Double result : resultSet) {
            System.out.println("" + resultSet.indexOf(result) + ": " + result);
            if (Math.abs(result - Point24.result) < threshold) {
                System.out.println("find a solution");
            }
        }
    }
}
