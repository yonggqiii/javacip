public class Class_8 {

    public static double eval(final String str) {
        return new Parser().parse();
    }
}

class Parser {

    int pos = -1, ch;

    void nextChar() {
        ch = (++pos < JavaCIPUnknownScope.str.length()) ? JavaCIPUnknownScope.str.charAt(pos) : -1;
    }

    boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    double parse() {
        nextChar();
        double x = parseExpression();
        if (pos < JavaCIPUnknownScope.str.length())
            throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)`
    // | number | functionName factor | factor `^` factor
    double parseExpression() {
        double x = parseTerm();
        for (; ; ) {
            if (// addition
            eat('+'))
                // addition
                x += parseTerm();
            else if (// subtraction
            eat('-'))
                // subtraction
                x -= parseTerm();
            else
                return x;
        }
    }

    double parseTerm() {
        double x = parseFactor();
        for (; ; ) {
            if (// multiplication
            eat('*'))
                // multiplication
                x *= parseFactor();
            else if (// division
            eat('/'))
                // division
                x /= parseFactor();
            else
                return x;
        }
    }

    double parseFactor() {
        // unary plus
        if (eat('+'))
            return parseFactor();
        // unary minus
        if (eat('-'))
            return -parseFactor();
        double x;
        int startPos = this.pos;
        if (eat('(')) {
            // parentheses
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') {
            // numbers
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(JavaCIPUnknownScope.str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') {
            // functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = JavaCIPUnknownScope.str.substring(startPos, this.pos);
            x = parseFactor();
            if (func.equals("sqrt"))
                x = Math.sqrt(x);
            else if (func.equals("sin"))
                x = Math.sin(Math.toRadians(x));
            else if (func.equals("cos"))
                x = Math.cos(Math.toRadians(x));
            else if (func.equals("tan"))
                x = Math.tan(Math.toRadians(x));
            else
                throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) ch);
        }
        // exponentiation
        if (eat('^'))
            x = Math.pow(x, parseFactor());
        return x;
    }
}
