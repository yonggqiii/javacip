


class c8249985 {

    public void initGet() throws RuntimeException {
        URL url = new URL(getURL());
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Range", "bytes=" + getPosition() + "-" + getRangeEnd());
        con.setUseCaches(false);
        con.connect();
        setInputStream(con.getInputStream());
    }

}
