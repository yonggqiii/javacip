class c8121415 {

    public static File writeInternalFile(Context cx, URL url, String dir, String filename) {
        FileOutputStream fos = null;
        File fi = null;
        try {
            fi = JavaCIPUnknownScope.newInternalFile(cx, dir, filename);
            fos = FileUtils.openOutputStream(fi);
            int length = IOUtils.copy(url.openStream(), fos);
            JavaCIPUnknownScope.log(length + " bytes copyed.");
        } catch (IORuntimeException e) {
            AIOUtils.log("", e);
        } finally {
            try {
                fos.close();
            } catch (IORuntimeException e) {
                AIOUtils.log("", e);
            }
        }
        return fi;
    }
}
