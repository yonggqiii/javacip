class c21575638 {

    protected void writeGZippedBytes(byte[] array, TupleOutput out) {
        if (array == null || array.length == 0) {
            out.writeBoolean(false);
            JavaCIPUnknownScope.writeBytes(array, out);
            return;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(array.length);
            GZIPOutputStream gzout = new GZIPOutputStream(baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(array);
            IOUtils.copyTo(bais, gzout);
            gzout.finish();
            gzout.close();
            bais.close();
            byte[] compressed = baos.toByteArray();
            if (compressed.length < array.length) {
                out.writeBoolean(true);
                JavaCIPUnknownScope.writeBytes(compressed, out);
            } else {
                out.writeBoolean(false);
                JavaCIPUnknownScope.writeBytes(array, out);
            }
        } catch (IORuntimeException err) {
            throw new RuntimeRuntimeException(err);
        }
    }
}
