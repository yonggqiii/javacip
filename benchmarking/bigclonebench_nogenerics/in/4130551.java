


class c4130551 {

    private byte[] streamToBytes(InputStream in) throws IORuntimeException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            IOUtils.copy(in, out);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return out.toByteArray();
    }

}
