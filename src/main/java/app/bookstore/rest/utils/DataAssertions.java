package app.bookstore.rest.utils;

import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForInterfaceTypes;

import java.util.List;

public class DataAssertions {

    @Step("Verify that API response data matches database records")
    public static <T> void verifyThatAPIvsDBListsMatch(List<T> responseList, List<T> dbList){
        AssertionsForInterfaceTypes
                .assertThat(responseList)
                .describedAs("Mismatch between API response data and database records.")
                .containsExactlyInAnyOrderElementsOf(dbList)
                .hasSameSizeAs(dbList);
    }

    @Step("Verify that database records contains API response")
    public static <T> void verifyThatAPIvsDBListContains(List<T> responseList, List<T> dbList){
        AssertionsForInterfaceTypes
                .assertThat(dbList)
                .describedAs("Mismatch between API response data and database records.")
                .containsAll(responseList);
    }
}
