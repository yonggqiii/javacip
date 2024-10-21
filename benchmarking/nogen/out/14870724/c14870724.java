class c14870724 {

    private void readURL(URL url) throws IORuntimeException {
        JavaCIPUnknownScope.statusLine.setText("Opening " + url.toExternalForm());
        URLConnection connection = url.openConnection();
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line).append('\n');
                JavaCIPUnknownScope.statusLine.setText("Read " + buffer.length() + " bytes...");
            }
        } finally {
            if (in != null)
                in.close();
        }
        String type = connection.getContentType();
        if (type == null)
            type = "text/plain";
        JavaCIPUnknownScope.statusLine.setText("Content type " + type);
        JavaCIPUnknownScope.content.setContentType(type);
        JavaCIPUnknownScope.content.setText(buffer.toString());
        JavaCIPUnknownScope.statusLine.setText("Done");
    }
}
