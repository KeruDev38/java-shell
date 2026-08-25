package shell.reader;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    public static ParseResult parse(CharSequence input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        boolean escaped = false;
        Quote quote = Quote.NONE;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (escaped) {
                currentToken.append(c);
                escaped = false;
                continue;
            }

            if (c == '\\' && quote != Quote.SINGLE) {
                escaped = true;
                continue;
            }

            Quote newQuote = quote.toggle(c);
            if (newQuote != quote) {
                quote = newQuote;
                continue;
            }

            if (Character.isWhitespace(c) && quote == Quote.NONE) {
                if (!currentToken.isEmpty()) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }

                continue;
            }

            currentToken.append(c);
        }

        if (!currentToken.isEmpty()) {
            tokens.add(currentToken.toString());
        }

        return new ParseResult(tokens, quote);
    }
}
