class c14495973 {

    public static int copy(File src, int amount, File dst) {
        final int BUFFER_SIZE = 1024;
        int amountToRead = amount;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            out = new BufferedOutputStream(new FileOutputStream(dst));
            byte[] buf = new byte[BUFFER_SIZE];
            while (amountToRead > 0) {
                int read = in.read(buf, 0, Math.min(BUFFER_SIZE, amountToRead));
                if (read == -1)
                    break;
                amountToRead -= read;
                out.write(buf, 0, read);
            }
        } catch (IORuntimeException e) {
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IORuntimeException e) {
                }
            if (out != null) {
                try {
                    out.flush();
                } catch (IORuntimeException e) {
                }
                try {
                    out.close();
                } catch (IORuntimeException e) {
                }
            }
        }
        return amount - amountToRead;
    }
}
