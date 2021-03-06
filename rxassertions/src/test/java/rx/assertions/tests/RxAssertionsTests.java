package rx.assertions.tests;

import rx.assertions.RxAssertions;
import org.junit.Test;
import rx.Completable;
import rx.Observable;
import rx.Single;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class RxAssertionsTests {

    @Test public void factoryMethodBlockingObservableReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just(1)))
                .isNotNull();
    }

    @Test public void factoryMethodRegularObservableReturnsNotNull() {
        assertThat(RxAssertions.assertThat(Observable.just("Test")))
                .isNotNull();
    }

    @Test public void factoryMethodSingleReturnsNotNull() {
        Single<Boolean> single = Single.fromCallable(new java.util.concurrent.Callable<Boolean>() {
            @Override public Boolean call() {
                return Boolean.TRUE;
            }
        });
        assertThat(RxAssertions.assertThat(single)).isNotNull();
    }

    @Test public void factoryMethodCompletableReturnsNotNull() {
        Completable completable = Completable.fromCallable(new java.util.concurrent.Callable<String>() {
            @Override public String call() {
                return "Done";
            }
        });
        assertThat(RxAssertions.assertThat(completable)).isNotNull();
    }

    @Test public void emissionsCount() {
        RxAssertions.assertThat(Observable.range(1, 10))
                .emissionsCount(10);
    }

    @Test public void canChainTwoAssertions() {
        RxAssertions.assertThat(Observable.range(1, 5))
                .emissionsCount(5)
                .completes();
    }

    @Test public void simpleFail() {
        Observable<?> error = Observable.error(new IllegalArgumentException());
        RxAssertions.assertThat(error).fails().notCompletes();
    }

    @Test public void failWithException() {
        Observable<?> error = Observable.error(new IllegalStateException());
        RxAssertions.assertThat(error).failsWithThrowable(IllegalStateException.class);
    }

    @Test public void noEmissions() {
        RxAssertions.assertThat(Observable.empty())
                .emitsNothing()
                .completes()
                .withoutErrors();
    }

    @Test public void regularObservableCompletesWithEmissions() {
        RxAssertions.assertThat(Observable.just("RxJava", "Assertions"))
                .completes()
                .withoutErrors()
                .expectedValues("RxJava", "Assertions");
    }

    @Test public void completablecompletesWithoutErrors() {
        Completable trivial = Completable.fromObservable(Observable.range(1, 5));
        RxAssertions.assertThat(trivial)
                .completes()
                .withoutErrors();
    }

    @Test public void singleCompletesWithExpectedValue() {
        Single<String> single = Single.fromCallable(new java.util.concurrent.Callable<String>() {
            @Override public String call() {
                return "RxJava";
            }
        });
        RxAssertions.assertThat(single).completes().expectedSingleValue("RxJava");
    }

}
