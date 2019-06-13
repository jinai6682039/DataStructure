package com.github.alexhanxs.datastructure;

import java.math.BigInteger;

public class DecimalToFraction {

    public static void decimalToFraction(String limitedPart, String cyclePart) {

        int limitNum = 0;
        int limitSize = 0;
        int cycleNum = 0;
        int cycleSize = 0;

        if (limitedPart != null) {
            limitNum = Integer.parseInt(limitedPart);
            limitSize = limitedPart.length();
        }

        if (cyclePart != null) {
            cycleNum = Integer.parseInt(cyclePart);
            cycleSize = cyclePart.length();
        }

        double num = limitNum * (Math.pow(10, cycleSize) - 1) + cycleNum;
        double num2 = Math.pow(10, limitSize) * (Math.pow(10, cycleSize) - 1);

        System.out.println("The Fraction is : " + (num + "") + " / " + (num2 + ""));
    }

    public static void main(String[] args) {
        decimalToFraction("285714", "285714");

        BigInteger bigInteger = new BigInteger("123456789098765432112345");
    }
}
