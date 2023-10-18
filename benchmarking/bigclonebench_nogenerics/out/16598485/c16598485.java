class c16598485 {

    private void writeToUrl(byte[] data, String url) throws IORuntimeException, MalformedURLRuntimeException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        OutputStream out = connection.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
    }
}
