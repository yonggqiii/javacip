class c9510815 {

    public static void copy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(target).getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(JavaCIPUnknownScope.BUFFER);
            while (in.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    out.write(buffer);
                }
                buffer.clear();
            }
        } catch (IORuntimeException ex) {
            throw new RuntimeRuntimeException(ex);
        } finally {
            JavaCIPUnknownScope.close(in);
            JavaCIPUnknownScope.close(out);
        }
    }
}