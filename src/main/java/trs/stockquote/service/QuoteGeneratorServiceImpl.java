package trs.stockquote.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import trs.stockquote.model.Quote;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;


@Service
public class QuoteGeneratorServiceImpl implements QuoteGeneratorService {

    private final MathContext mathContext = new MathContext(2);
    private final Random random = new Random();
    private final List<Quote> prices = new ArrayList<>();

    public QuoteGeneratorServiceImpl() {
        this.prices.add(new Quote("AAPL", 160.16));
        this.prices.add(new Quote("MSFT", 77.74));
        this.prices.add(new Quote("GOOG", 847.24));
        this.prices.add(new Quote("ORCL", 49.51));
        this.prices.add(new Quote("IBM", 159.34));
        this.prices.add(new Quote("INTC", 39.29));
        this.prices.add(new Quote("RHT", 84.29));
        this.prices.add(new Quote("VMW", 92.21));
    }

    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        // We Use Flux.generate to create quotes,
        // iterating over each stock starting at index 0

        return Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>) (index, sink) -> {
                    Quote updatedQuote = updateQuote(this.prices.get(index));
                    sink.next(updatedQuote);
                    return ++index % this.prices.size();
                })
                .zipWith(Flux.interval(period)) // returns a Tuple2 with <Quote> and <Sequence> 1,2,3..N
//                .map((t) -> {
//                    System.out.println(">>>>>>>>>>>>>>>>> " + t.getT2());
//                    return t.getT1();
//                })
                .map(t -> t.getT1()) // extract the Quote alone
                .map(quote -> {
                    quote.setInstant(Instant.now());
                    return quote;
                })
                .log("trs.service.QuoteGenerator");
    }

    private Quote updateQuote(Quote quote) {
        BigDecimal priceChange = quote.getPrice()
                .multiply(new BigDecimal(0.5 * this.random.nextDouble()), this.mathContext);
        return new Quote(quote.getTicker(), quote.getPrice().add(priceChange));
    }
}
