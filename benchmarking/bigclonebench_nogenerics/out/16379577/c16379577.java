class c16379577 {

    public InputStream sendGetMessage(Properties args) throws IORuntimeException {
        String argString = "";
        if (args != null) {
            argString = "?" + JavaCIPUnknownScope.toEncodedString(args);
        }
        URL url = new URL(JavaCIPUnknownScope.servlet.toExternalForm() + argString);
        URLConnection con = url.openConnection();
        con.setUseCaches(false);
        JavaCIPUnknownScope.sendHeaders(con);
        return con.getInputStream();
    }
}
