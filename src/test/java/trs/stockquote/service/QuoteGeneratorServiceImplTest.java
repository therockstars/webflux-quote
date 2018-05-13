package trs.stockquote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import trs.stockquote.WebfluxStockQuoteServiceApplicationTests;
import trs.stockquote.model.Quote;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class QuoteGeneratorServiceImplTest extends WebfluxStockQuoteServiceApplicationTests {

    @Autowired
    QuoteGeneratorServiceImpl quoteGeneratorService;

    @Before
    public void setup() throws Exception {

    }


    // The stream runs so fast - and the output will not be visible at all
    @Test
    public void fetchQuoteStream() throws Exception {
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        quoteFlux.take(200)
                .subscribe(System.out::println);

        System.out.println("========================================================");

        Thread.sleep(10000);
    }

    @Test
    public void fetchQuoteStreamCountDown() throws Exception {

        //get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        // subscriber lambda
        Consumer<Quote> println = System.out::println;

        // error handler
        Consumer<Throwable> errorHandler = e -> System.out.println(e.getMessage());

        // set down latch to 1
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // Runnable called upon complete
        Runnable allDone = countDownLatch::countDown;

        quoteFlux.take(10)
                .subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }
}