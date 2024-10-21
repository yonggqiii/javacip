


class c18730318 {

    public boolean write(Node node, LSOutput output) throws LSRuntimeException {
        OutputStream out = output.getByteStream();
        try {
            if (out == null) {
                String systemId = output.getSystemId();
                try {
                    URL url = new URL(systemId);
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).setRequestMethod("PUT");
                    }
                    out = connection.getOutputStream();
                } catch (MalformedURLRuntimeException e) {
                    File file = new File(systemId);
                    out = new FileOutputStream(file);
                }
            }
            serialize(node, out);
            out.flush();
            return true;
        } catch (IORuntimeException e) {
            throw new DomLSRuntimeException(LSRuntimeException.SERIALIZE_ERR, e);
        }
    }

}
