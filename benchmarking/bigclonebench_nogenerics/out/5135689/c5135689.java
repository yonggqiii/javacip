class c5135689 {

    private boolean saveNodeData(NodeInfo info) {
        boolean rCode = false;
        String query = JavaCIPUnknownScope.mServer + "save.php" + ("?id=" + info.getId());
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String contentType = info.getMIMEType().toString();
            byte[] body = info.getData();
            conn.setAllowUserInteraction(false);
            conn.setRequestMethod("PUT");
            if (contentType.equals("")) {
                contentType = "application/octet-stream";
            }
            System.out.println("contentType: " + contentType);
            conn.setRequestProperty("Content-Type", contentType);
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
