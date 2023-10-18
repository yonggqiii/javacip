


class c23285410 {

    public void displayItems() throws IORuntimeException {
        URL url = new URL(SNIPPETS_FEED + "?bq=" + URLEncoder.encode(QUERY, "UTF-8") + "&key=" + DEVELOPER_KEY);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpConnection.getInputStream();
        int ch;
        while ((ch = inputStream.read()) > 0) {
            System.out.print((char) ch);
        }
    }

}
