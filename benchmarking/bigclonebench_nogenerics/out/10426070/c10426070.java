class c10426070 {

    public static long copy(File src, long amount, File dst) {
        final int BUFFER_SIZE = 1024;
        long amountToRead = amount;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            out = new BufferedOutputStream(new FileOutputStream(dst));
            byte[] buf = new byte[BUFFER_SIZE];
            while (amountToRead > 0) {
                int read = in.read(buf, 0, (int) Math.min(BUFFER_SIZE, amountToRead));
                if (read == -1)
                    break;
                amountToRead -= read;
                out.write(buf, 0, read);
            }
        } catch (IORuntimeException e) {
        } finally {
            JavaCIPUnknownScope.close(in);
            JavaCIPUnknownScope.flush(out);
            JavaCIPUnknownScope.close(out);
        }
        return amount - amountToRead;
    }
}
