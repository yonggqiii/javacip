


class c15129123 {

    public String doGet() throws MalformedURLRuntimeException, IORuntimeException {
        uc = (HttpURLConnection) url.openConnection();
        BufferedInputStream buffer = new BufferedInputStream(uc.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int c;
        while ((c = buffer.read()) != -1) {
            bos.write(c);
        }
        bos.close();
        headers = uc.getHeaderFields();
        status = uc.getResponseCode();
        return bos.toString();
    }

}