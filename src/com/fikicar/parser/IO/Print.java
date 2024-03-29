package com.fikicar.parser.IO;

import com.fikicar.parser.Parser;
import com.fikicar.parser.standard.*;
import com.fikicar.util.Pair;

public class Print {
    /**
     * Print function.
     * print: 'PRINT' STRING | INT | DECIMAL | NAME (basePrint)?
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int printFunction(int index) {
        if (Parser.tokens.size() > index + 1 && (Parser.tokens.get(index + 1).key.equals("STRING") || Parser.tokens.get(index + 1).key.equals("INT")
                || Parser.tokens.get(index + 1).key.equals("DECIMAL") || Parser.tokens.get(index + 1).key.equals("BOOL"))) {
            switch (Parser.tokens.get(index + 1).key) {
                case "INT": {
                    int[] retInt = Integers.expressionInt(index + 1, 0);
                    System.out.print(retInt[1]);
                    return basePrint(--retInt[0]);
                }
                case "DECIMAL": {
                    double[] retDouble = Decimals.expressionDecimal(index * 1.0 + 1.0, 0.0);
                    System.out.print(retDouble[1]);
                    return basePrint((int) --retDouble[0]);
                }
                case "BOOL": {
                    int[] retBool = Bools.bool(index + 1);
                    System.out.print(retBool[1] == 1);
                    return basePrint(retBool[0]);
                }
                default: {
                    String toPrint = Parser.tokens.get(index + 1).value;
                    if (Parser.tokens.get(index + 1).key.equals("STRING"))
                        toPrint = toPrint.subSequence(1, toPrint.length() - 1).toString();
                    System.out.print(toPrint);
                    break;
                }
            }
            return basePrint(index + 2); //1
        } else if (Parser.tokens.size() > index + 1 && Parser.tokens.get(index + 1).key.equals("NAME")) {
            if (Parser.intStore.containsKey(Parser.tokens.get(index + 1).value) || Parser.stringStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.decimalStore.containsKey(Parser.tokens.get(index + 1).value) || Parser.boolStore.containsKey(Parser.tokens.get(index + 1).value)) {
                int store;
                if (Parser.boolStore.containsKey(Parser.tokens.get(index + 1).value)) store = 1;
                else if (Parser.intStore.containsKey(Parser.tokens.get(index + 1).value)) store = 2;
                else if (Parser.decimalStore.containsKey(Parser.tokens.get(index + 1).value)) store = 3;
                else store = 4;
                switch (store) {
                    case 1: {
                        System.out.print(Parser.boolStore.get(Parser.tokens.get(index + 1).value));
                        break;
                    }
                    case 2: {
                        System.out.print(Parser.intStore.get(Parser.tokens.get(index + 1).value));
                        break;
                    }
                    case 3: {
                        System.out.print(Parser.decimalStore.get(Parser.tokens.get(index + 1).value));
                        break;
                    }
                    case 4: {
                        String value = Parser.stringStore.get(Parser.tokens.get(index + 1).value);
                        value = value.subSequence(1, value.length() - 1).toString();
                        System.out.print(value);
                        break;
                    }
                }
                return basePrint(index + 1); //2
            } else if (Arrays.arraySize(index + 1)[0] != 0) {
                int[] ret = Arrays.arraySize(index + 1);
                System.out.print(ret[1]);
                return ret[0];
            } else if (Matrixes.matrixRowSize(index + 1)[0] != 0) {
                int[] ret = Matrixes.matrixRowSize(index + 1);
                System.out.print(ret[1]);
                return ret[0];
            } else if (Matrixes.matrixColumnSize(index + 1)[0] != 0) {
                int[] ret = Matrixes.matrixColumnSize(index + 1);
                System.out.print(ret[1]);
                return ret[0];
            } else if (index + 6 < Parser.tokens.size() && (Parser.boolArrayStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.intArrayStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.decimalArrayStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.stringMatrixStore.containsKey(Parser.tokens.get(index + 1).value))) {
                Pair retPair = Arrays.getArrayValue(index + 1, 0);
                if ((int) retPair.getKey() != 0) {
                    System.out.print(retPair.getValue());
                    return (int) retPair.getKey();
                }
                return 0;
            } else if (index + 8 < Parser.tokens.size() && (Parser.boolMatrixStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.intMatrixStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.decimalMatrixStore.containsKey(Parser.tokens.get(index + 1).value)
                    || Parser.stringMatrixStore.containsKey(Parser.tokens.get(index + 1).value))) {
                Pair retPair = Matrixes.getMatrixValue(index + 1, 0);
                if ((int) retPair.getKey() != 0) {
                    System.out.print(retPair.getValue());
                    return (int) retPair.getKey();
                }
                return 0;
            } else return 0;
        } else if (Parser.tokens.size() > index + 1 && Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) {
            index++;
            int tempIndex = 0;
            for (int i = index; i < Parser.tokens.size(); i++)
                if (!Parser.tokens.get(i).key.equals("L_PARENTHESES")) {
                    tempIndex = i;
                    break;
                }
            switch (Parser.tokens.get(tempIndex).key) {
                case "INT": {
                    int[] retInt = Integers.expressionInt(index, 0);
                    System.out.print(retInt[1]);
                    return basePrint(--retInt[0]);
                }
                case "DECIMAL": {
                    double[] retDouble = Decimals.expressionDecimal(index * 1.0, 0.0);
                    System.out.print(retDouble[1]);
                    return basePrint((int) --retDouble[0]);
                }
            }
            return 0;
        } else return 0;
    }

    /**
     * basePrint checks for extra one-line print option.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int basePrint(int index) {
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index).key.equals("SPLIT")) {
            int res = printFunction(index);
            return (res == 0) ? --index : res;
        }
        return --index;
    }
}
