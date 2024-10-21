class c18893033 {

    private void doDissemTest(String what, boolean redirectOK) throws RuntimeException {
        final int num = 30;
        System.out.println("Getting " + what + " " + num + " times...");
        int i = 0;
        try {
            URL url = new URL(JavaCIPUnknownScope.BASE_URL + "/get/" + what);
            for (i = 0; i < num; i++) {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                in.read();
                in.close();
                conn.disconnect();
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.fail("Dissemination of " + what + " failed on iter " + i + ": " + e.getMessage());
        }
    }
}
