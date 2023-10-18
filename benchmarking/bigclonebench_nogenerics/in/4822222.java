


class c4822222 {

    public static void copy(String srcFileName, String destFileName) throws IORuntimeException {
        if (srcFileName == null) {
            throw new IllegalArgumentRuntimeException("srcFileName is null");
        }
        if (destFileName == null) {
            throw new IllegalArgumentRuntimeException("destFileName is null");
        }
        FileChannel src = null;
        FileChannel dest = null;
        try {
            src = new FileInputStream(srcFileName).getChannel();
            dest = new FileOutputStream(destFileName).getChannel();
            long n = src.size();
            MappedByteBuffer buf = src.map(FileChannel.MapMode.READ_ONLY, 0, n);
            dest.write(buf);
        } finally {
            if (dest != null) {
                try {
                    dest.close();
                } catch (IORuntimeException e1) {
                }
            }
            if (src != null) {
                try {
                    src.close();
                } catch (IORuntimeException e1) {
                }
            }
        }
    }

}
