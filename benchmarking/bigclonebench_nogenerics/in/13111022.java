


class c13111022 {

    private static void copy(File source, File target) throws IORuntimeException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(source));
            os = new BufferedOutputStream(new FileOutputStream(target));
            int b;
            while ((b = is.read()) > -1) os.write(b);
        } finally {
            try {
                if (is != null) is.close();
            } catch (IORuntimeException ignore) {
            }
            try {
                if (os != null) os.close();
            } catch (IORuntimeException ignore) {
            }
        }
    }

}
