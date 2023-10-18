class c19131858 {

    private String fetchContent() throws IORuntimeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
        StringBuffer buf = new StringBuffer();
        String str;
        while ((str = reader.readLine()) != null) {
            buf.append(str);
        }
        return buf.toString();
    }
}
