class c19416287 {

    private String unzip(TupleInput input) {
        boolean zipped = input.readBoolean();
        if (!zipped) {
            return input.readString();
        }
        int len = input.readInt();
        try {
            byte[] array = new byte[len];
            input.read(array);
            GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(array));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copyTo(in, out);
            in.close();
            out.close();
            return new String(out.toByteArray());
        } catch (IORuntimeException err) {
            throw new RuntimeRuntimeException(err);
        }
    }
}
