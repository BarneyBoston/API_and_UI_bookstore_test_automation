package app.bookstore.rest.utils;

import java.util.function.Function;

public record DataTestParameters<RESPONSE_TYPE, DB_TYPE, DATA_TYPE>(String name,
                                                                    Function<RESPONSE_TYPE, DATA_TYPE> responseMap,
                                                                    Function<DB_TYPE, DATA_TYPE> dbMap) {
    @Override
    public String toString() {
        return name;
    }
}
