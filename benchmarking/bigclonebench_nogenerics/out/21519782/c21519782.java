class c21519782 {

    protected static void copyDeleting(File source, File dest) throws ErrorCodeRuntimeException {
        byte[] buf = new byte[8 * 1024];
        FileInputStream in = null;
        try {
            in = new FileInputStream(source);
            try {
                FileOutputStream out = new FileOutputStream(dest);
                try {
                    int count;
                    while ((count = in.read(buf)) >= 0) out.write(buf, 0, count);
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } catch (IORuntimeException e) {
            throw new ErrorCodeRuntimeException(e);
        }
    }
}
