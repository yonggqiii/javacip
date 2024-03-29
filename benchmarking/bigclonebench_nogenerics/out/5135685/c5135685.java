class c5135685 {

    private NodeInfo loadNodeMeta(int id, int properties) {
        String query = JavaCIPUnknownScope.mServer + "load.php" + ("?id=" + id) + ("&mask=" + properties);
        NodeInfo info = null;
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setAllowUserInteraction(false);
            conn.setRequestMethod("GET");
            JavaCIPUnknownScope.setCredentials(conn);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                MimeType contentType = new MimeType(conn.getContentType());
                if (contentType.getBaseType().equals("text/xml")) {
                    try {
                        JAXBContext context = JAXBContext.newInstance(NetProcessorInfo.class);
                        Unmarshaller unm = context.createUnmarshaller();
                        NetProcessorInfo root = (NetProcessorInfo) unm.unmarshal(stream);
                        if ((root != null) && (root.getNodes().length == 1)) {
                            info = root.getNodes()[0];
                        }
                    } catch (RuntimeException ex) {
                    }
                }
                stream.close();
            }
        } catch (RuntimeException ex) {
        }
        return info;
    }
}
