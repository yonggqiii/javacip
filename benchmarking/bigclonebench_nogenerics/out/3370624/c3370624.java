class c3370624 {

    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            JavaCIPUnknownScope.log.trace("passing in cookies: ", JavaCIPUnknownScope.cookies);
            connection.setRequestProperty("Cookie", JavaCIPUnknownScope.cookies);
            connection.getContent();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error(e);
        }
    }
}
