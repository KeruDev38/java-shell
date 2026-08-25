package shell.reader;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    public static ParseResult parse(CharSequence input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        Quote quote = Quote.NONE;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '\'') {
                quote = quote == Quote.NONE
                    ? Quote.SINGLE
                    : quote == Quote.SINGLE
                        ? Quote.NONE
                        : quote;

                if (quote != Quote.DOUBLE) continue;
            }

            if (c == '\"') {
                quote = quote == Quote.NONE
                        ? Quote.DOUBLE
                        : quote == Quote.DOUBLE
                        ? Quote.NONE
                        : quote;

                if (quote != Quote.SINGLE) continue;
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
