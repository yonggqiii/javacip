


class c17014629 {

    public static void fileCopy(File src, File dest) throws IORuntimeException {
        IORuntimeException xforward = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            fcin = fis.getChannel();
            fcout = fos.getChannel();
            final int MB32 = 32 * 1024 * 1024;
            long size = fcin.size();
            long position = 0;
            while (position < size) {
                position += fcin.transferTo(position, MB32, fcout);
            }
        } catch (IORuntimeException xio) {
            xforward = xio;
        } finally {
            if (fis != null) try {
                fis.close();
                fis = null;
            } catch (IORuntimeException xio) {
            }
            if (fos != null) try {
                fos.close();
                fos = null;
            } catch (IORuntimeException xio) {
            }
            if (fcin != null && fcin.isOpen()) try {
                fcin.close();
                fcin = null;
            } catch (IORuntimeException xio) {
            }
            if (fcout != null && fcout.isOpen()) try {
                fcout.close();
                fcout = null;
            } catch (IORuntimeException xio) {
            }
        }
        if (xforward != null) {
            throw xforward;
        }
    }

}
