package shell.reader;

public enum Quote {
    NONE,
    SINGLE,
    DOUBLE;

    public Quote toggle(char c) {
        if (this == NONE) {
            if (c == '\'') return SINGLE;
            if (c == '"')  return DOUBLE;
        } else if ((this == SINGLE && c == '\'')
                || (this == DOUBLE && c == '"')) {
            return NONE;
        }
        return this;
    }
}