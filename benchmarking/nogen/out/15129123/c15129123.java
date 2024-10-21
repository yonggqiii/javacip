class c15129123 {

    public String doGet() throws MalformedURLRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.uc = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        BufferedInputStream buffer = new BufferedInputStream(JavaCIPUnknownScope.uc.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int c;
        while ((c = buffer.read()) != -1) {
            bos.write(c);
        }
        bos.close();
        JavaCIPUnknownScope.headers = JavaCIPUnknownScope.uc.getHeaderFields();
        JavaCIPUnknownScope.status = JavaCIPUnknownScope.uc.getResponseCode();
        return bos.toString();
    }
}
