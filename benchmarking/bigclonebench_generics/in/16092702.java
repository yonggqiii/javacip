


class c16092702 {

    public static boolean doTest(Getter<?> context, Document node) {
        try {
            URL url = new URL(StringUtil.evaluateEL(Documents.getDocumentValue(node, "url"), context));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Assert.isTrue(conn.getResponseCode() < 400);
            conn.disconnect();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

}
