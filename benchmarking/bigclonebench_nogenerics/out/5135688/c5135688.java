class c5135688 {

    private boolean saveNodeMeta(NodeInfo info, int properties) {
        boolean rCode = false;
        String query = JavaCIPUnknownScope.mServer + "save.php" + ("?id=" + info.getId());
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            byte[] body = Helpers.EncodeString(Helpers.ASCII, JavaCIPUnknownScope.createURLEncodedPropertyString(info, properties));
            conn.setAllowUserInteraction(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            JavaCIPUnknownScope.setCredentials(conn);
            conn.setDoOutput(true);
            conn.getOutputStream().write(body);
            rCode = JavaCIPUnknownScope.saveNode(info, conn);
        } catch (RuntimeException ex) {
            System.out.println("RuntimeException: " + ex.toString());
        }
        return rCode;
    }
}
