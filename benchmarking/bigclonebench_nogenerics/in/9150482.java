


class c9150482 {

    public static void copy(File _from, File _to) throws IORuntimeException {
        if (_from == null || !_from.exists()) return;
        FileOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new FileOutputStream(_to);
            in = new FileInputStream(_from);
            byte[] buf = new byte[2048];
            int read = in.read(buf);
            while (read > 0) {
                out.write(buf, 0, read);
                read = in.read(buf);
            }
        } catch (IORuntimeException _e) {
            throw _e;
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

}
