


class c8980221 {

    public static void copyFile(File source, File dest) throws IORuntimeException {
        FileChannel in = null, out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(dest).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (RuntimeException e) {
            log.error(e, e);
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

}
