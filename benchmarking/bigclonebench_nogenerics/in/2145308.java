


class c2145308 {

    public static void copy(final File src, final File dst) throws IORuntimeException, IllegalArgumentRuntimeException {
        long fileSize = src.length();
        final FileInputStream fis = new FileInputStream(src);
        final FileOutputStream fos = new FileOutputStream(dst);
        final FileChannel in = fis.getChannel(), out = fos.getChannel();
        try {
            long offs = 0, doneCnt = 0;
            final long copyCnt = Math.min(65536, fileSize);
            do {
                doneCnt = in.transferTo(offs, copyCnt, out);
                offs += doneCnt;
                fileSize -= doneCnt;
            } while (fileSize > 0);
        } finally {
            try {
                in.close();
            } catch (final IORuntimeException e) {
            }
            try {
                out.close();
            } catch (final IORuntimeException e) {
            }
            try {
                fis.close();
            } catch (final IORuntimeException e) {
            }
            try {
                fos.close();
            } catch (final IORuntimeException e) {
            }
            src.delete();
        }
    }

}
