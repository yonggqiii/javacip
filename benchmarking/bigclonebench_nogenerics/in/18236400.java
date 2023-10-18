


class c18236400 {

    protected URL[][] getImageLinks(final URL url) {
        Lexer lexer;
        URL[][] ret;
        if (null != url) {
            try {
                lexer = new Lexer(url.openConnection());
                ret = extractImageLinks(lexer, url);
            } catch (RuntimeException t) {
                System.out.println(t.getMessage());
                ret = NONE;
            }
        } else ret = NONE;
        return (ret);
    }

}
