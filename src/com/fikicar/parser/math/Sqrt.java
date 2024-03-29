package com.fikicar.parser.math;

import com.fikicar.parser.Parser;

public class Sqrt extends OneExpression {

    /**
     * mathSqrtDecimal checks if series of tokens fits type int sqrt function.
     *
     * @param index position of SQRT token
     * @return index to continue parsing from and value
     */
    public static int[] mathSqrtInt(int index) {
        int[] ret = new int[2];
        if (Parser.tokens.size() > index && !Parser.tokens.get(index).key.equals("SQRT")) {
            return ret;
        }
        index++;
        int[] retInt = getOneIntExpression(index);
        ret[0] = retInt[0];
        ret[1] = (int) Math.sqrt(retInt[1]);
        return ret;
    }

    /**
     * mathSqrtDecimal checks if series of tokens fits type decimal sqrt function.
     *
     * @param index position of SQRT token
     * @return index to continue parsing from and value
     */
    public static double[] mathSqrtDecimal(int index) {
        double[] ret = new double[2];
        if (Parser.tokens.size() > index && !Parser.tokens.get(index).key.equals("SQRT")) {
            return ret;
        }
        index++;
        double[] retDecimal = getOneDecimalExpression(index);
        ret[0] = retDecimal[0];
        ret[1] = Math.sqrt(retDecimal[1]);
        return ret;
    }
}
