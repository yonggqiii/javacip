


class c10676051 {

    public void sendRequest(String method) {
        try {
            url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();
            System.out.println(httpURLConnection.getResponseMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

}
