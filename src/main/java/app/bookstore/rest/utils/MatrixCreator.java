package app.bookstore.rest.utils;

import java.util.List;

@SuppressWarnings("unused")
public class MatrixCreator {

    public static Object[][] listToMatrix(List<?> list) {
        return list.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    public static Object[][] arrayToMatrix(Object... array) {
        var output = new Object[array.length][1];

        for (int i = 0; i < array.length; i++) {
            output[i][0] = array[i];
        }

        return output;
    }

}