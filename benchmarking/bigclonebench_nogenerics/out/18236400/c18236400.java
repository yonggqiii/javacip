class c18236400 {

    protected URL[][] getImageLinks(final URL url) {
        Lexer lexer;
        URL[][] ret;
        if (null != url) {
            try {
                lexer = new Lexer(url.openConnection());
                ret = JavaCIPUnknownScope.extractImageLinks(lexer, url);
            } catch (RuntimeException t) {
                System.out.println(t.getMessage());
                ret = JavaCIPUnknownScope.NONE;
            }
        } else
            ret = JavaCIPUnknownScope.NONE;
        return (ret);
    }
}
