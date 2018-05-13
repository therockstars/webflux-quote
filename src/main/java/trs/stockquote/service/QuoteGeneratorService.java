package trs.stockquote.service;

import reactor.core.publisher.Flux;
import trs.stockquote.model.Quote;

import java.time.Duration;

public interface QuoteGeneratorService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
