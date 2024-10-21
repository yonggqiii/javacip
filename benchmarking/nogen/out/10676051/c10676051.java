class c10676051 {

    public void sendRequest(String method) {
        try {
            JavaCIPUnknownScope.url = new URL(JavaCIPUnknownScope.urlStr);
            JavaCIPUnknownScope.httpURLConnection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            JavaCIPUnknownScope.httpURLConnection.setRequestMethod(method);
            JavaCIPUnknownScope.httpURLConnection.setDoOutput(true);
            JavaCIPUnknownScope.httpURLConnection.getOutputStream().flush();
            JavaCIPUnknownScope.httpURLConnection.getOutputStream().close();
            System.out.println(JavaCIPUnknownScope.httpURLConnection.getResponseMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (JavaCIPUnknownScope.httpURLConnection != null) {
                JavaCIPUnknownScope.httpURLConnection.disconnect();
            }
        }
    }
}
