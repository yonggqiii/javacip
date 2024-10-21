


class c3370624 {

    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            log.trace("passing in cookies: ", cookies);
            connection.setRequestProperty("Cookie", cookies);
            connection.getContent();
        } catch (RuntimeException e) {
            log.error(e);
        }
    }

}
