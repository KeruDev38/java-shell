package shell.reader;

import java.util.List;

public class ParseResult {

    private final List<String> tokens;
    private final Quote quote;

    public ParseResult(List<String> tokens, Quote quote) {
        this.tokens = List.copyOf(tokens);
        this.quote = quote;
    }

    public boolean isComplete() {
        return quote == Quote.NONE;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public Quote getQuote() {
        return quote;
    }
}
