package com.github.alexhanxs.datastructure;

import java.util.HashMap;

// 编程之美 3.3 计算字符串相似度
public class CalculateTheSimilarBetweenTwoString {

    private static HashMap<String, Integer> results = new HashMap<>();

    private static String getDynamicPlanKey(String stringA, int aStart, int aEnd, String stringB,
                                            int bStart, int bEnd) {
        StringBuilder sb = new StringBuilder();
        sb.append(stringA).append("_").append(aStart).append("_").append(aEnd);
        sb.append(stringB).append("_").append(bStart).append("_").append(bEnd);
        return sb.toString();
    }

    // 加入了动态规划
    private static int calculateStringSimilar(String stringA, int aStart, int aEnd, String stringB,
                                              int bStart, int bEnd) {

        String key = getDynamicPlanKey(stringA, aStart, aEnd, stringB, bStart, bEnd);

        // 加入动态规划
        if (results.containsKey(key)) {
            return results.get(key);
        }

        if (aStart > aEnd) {
            if (bStart > bEnd) {

                results.put(key, 0);

                return 0;
            } else {
                System.out.println("current A = " + stringA + " current B = " + stringB + " current similar = " + (bEnd - bStart + 1));

                results.put(key, bEnd - bStart + 1);

                return bEnd - bStart + 1;
            }
        }

        if (bStart > bEnd) {
            if (aStart > aEnd) {

                results.put(key, 0);

                return 0;
            } else {
                System.out.println("current A = " + stringA + " current B = " + stringB + " current similar = " + (aEnd - aStart + 1));

                results.put(key, aEnd - aStart + 1);

                return aEnd - aStart + 1;
            }
        }

        if (stringA.charAt(aStart) == stringB.charAt(bStart)) {
            int result = calculateStringSimilar(stringA, aStart + 1, aEnd, stringB, bStart + 1, bEnd);
            System.out.println("current A = " + stringA + " current B = " + stringB + " current similar = " + result);

            results.put(key, result);
            return result;
        } else {

            // 删除A当前比较的第一位,也就是aStart位
            // 如果当前A只有一位，不执行删除
            int similar1 = Integer.MAX_VALUE;
            StringBuilder sb;
            if (aEnd - aStart != 1 && stringA.length() != 1) {
                sb = new StringBuilder(stringA);
                sb.replace(aStart, aStart + 1, "");
                similar1 = calculateStringSimilar(sb.toString(), aStart, aEnd - 1, stringB, bStart, bEnd);

                key = getDynamicPlanKey(sb.toString(), aStart, aEnd - 1, stringB, bStart, bEnd);
                results.put(key, similar1);

                System.out.println("TT current A = " + sb.toString() + " current B = " + stringB + " current similar = " + similar1);
            }


            // 删除B当前比较的第一位
            // 如果当前B只有一位，不执行删除
            int similar2 = Integer.MAX_VALUE;
            if (bEnd - bStart != 1 && stringB.length() != 1) {
                sb = new StringBuilder(stringB);
                sb.replace(bStart, bStart + 1, "");
                similar2 = calculateStringSimilar(stringA, aStart, aEnd, sb.toString(), bStart, bEnd - 1);

                key = getDynamicPlanKey(stringA, aStart, aEnd, sb.toString(), bStart, bEnd - 1);
                results.put(key, similar2);

                System.out.println("TT current A = " + stringA + " current B = " + sb.toString() + " current similar = " + similar2);
            }

            // 修改A的当前比较的第一位
            sb = new StringBuilder(stringA);
            sb.replace(aStart, aStart + 1, stringB.charAt(bStart) + "");

            int similar3 = calculateStringSimilar(sb.toString(), aStart + 1, aEnd, stringB, bStart + 1, bEnd);
            System.out.println("TT current A = " + sb.toString() + " current B = " + stringB + " current similar = " + similar3);

            key = getDynamicPlanKey(sb.toString(), aStart + 1, aEnd, stringB, bStart + 1, bEnd);
            results.put(key, similar3);

            // 修改B的当前比较的第一位
            sb = new StringBuilder(stringB);
            sb.replace(bStart, bStart + 1, stringA.charAt(bStart) + "");
            int similar4 = calculateStringSimilar(stringA, aStart + 1, aEnd, sb.toString(), bStart + 1, bEnd);
            System.out.println("TT current A = " + stringA + " current B = " + sb.toString() + " current similar = " + similar4);

            key = getDynamicPlanKey(stringA, aStart + 1, aEnd, sb.toString(), bStart + 1, bEnd);
            results.put(key, similar4);

            // A的start位增加一位与当前B比较位相同的字符串
            sb = new StringBuilder(stringA);
            sb.replace(aStart, aStart + 1, (stringB.charAt(bStart) + "") + (stringA.charAt(aStart) + ""));
            int similar5 = calculateStringSimilar(sb.toString(), aStart + 1, aEnd + 1, stringB, bStart + 1, bEnd);
            System.out.println("TT current A = " + sb.toString() + " current B = " + stringB + " current similar = " + similar5);

            key = getDynamicPlanKey(sb.toString(), aStart + 1, aEnd + 1, stringB, bStart + 1, bEnd);
            results.put(key, similar5);

            // B的start位增加一位与当前A比较位相同的字符串
            sb = new StringBuilder(stringB);
            sb.replace(bStart, bStart + 1, (stringA.charAt(aStart) + "") + (stringB.charAt(bStart) + ""));
            int similar6 = calculateStringSimilar(stringA, aStart + 1, aEnd, sb.toString(), bStart + 1, bEnd + 1);
            System.out.println("TT current A = " + stringA + " current B = " + sb.toString() + " current similar = " + similar5);

            key = getDynamicPlanKey(stringA, aStart + 1, aEnd, sb.toString(), bStart + 1, bEnd + 1);
            results.put(key, similar6);

            int minSimilar = Math.min(similar1, similar2);
            minSimilar = Math.min(minSimilar, similar3);
            minSimilar = Math.min(minSimilar, similar4);
            minSimilar = Math.min(minSimilar, similar5);
            minSimilar = Math.min(minSimilar, similar6);

            key = getDynamicPlanKey(stringA, aStart, aEnd, stringB, bStart, bEnd);
            System.out.println("current A = " + stringA + " current B = " + stringB + " current similar = " + (minSimilar + 1));
            results.put(key, minSimilar + 1);

            return minSimilar + 1;
        }
    }

    public static void main(String[] args) {
        String stringA = "35";
        String stringB = "2";
        System.out.println(calculateStringSimilar(stringA, 0, stringA.length() - 1, stringB, 0, stringB.length() - 1));
    }
}
