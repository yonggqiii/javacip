


class c15347483 {

    private void testURL(String urlStr) throws MalformedURLRuntimeException, IORuntimeException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            int code = conn.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_OK, code);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
