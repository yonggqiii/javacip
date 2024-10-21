class c3011498 {

    public List<String> transform(String urlString) {
        String result = "";
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-agent", "finance news monitor");
            connection.setRequestProperty("From", "romilly.cocking@gmail.com");
            connection.setInstanceFollowRedirects(true);
            inputStream = connection.getInputStream();
            result = StringUtils.join(IOUtils.readLines(inputStream).toArray(), JavaCIPUnknownScope.lineSeparator);
        } catch (MalformedURLException e) {
            JavaCIPUnknownScope.log.warn("Malformed url " + urlString);
        } catch (IOException e) {
            JavaCIPUnknownScope.log.warn("error reading from url " + urlString, e);
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                JavaCIPUnknownScope.log.warn("could not close url " + urlString, e);
            }
        }
        return JavaCIPUnknownScope.enlist(result);
    }
}
