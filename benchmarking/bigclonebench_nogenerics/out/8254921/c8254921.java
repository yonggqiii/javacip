class c8254921 {

    public static void copy(File src, File dest, boolean overwrite) throws IORuntimeException {
        if (!src.exists())
            throw new IORuntimeException("File source does not exists");
        if (dest.exists()) {
            if (!overwrite)
                throw new IORuntimeException("File destination already exists");
            dest.delete();
        } else {
            dest.createNewFile();
        }
        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);
        byte[] buffer = new byte[1024 * 4];
        int len = 0;
        while ((len = is.read(buffer)) > 0) {
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }
}
