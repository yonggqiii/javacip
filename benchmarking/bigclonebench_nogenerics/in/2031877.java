


class c2031877 {

    public byte[] getResponseContent() throws IORuntimeException {
        if (responseContent == null) {
            InputStream is = getResponseStream();
            if (is == null) {
                responseContent = new byte[0];
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                IOUtils.copy(is, baos);
                responseContent = baos.toByteArray();
            }
        }
        return responseContent;
    }

}
