


class c2737487 {

    public static void copy(File src, File dest) throws IORuntimeException {
        OutputStream stream = new FileOutputStream(dest);
        FileInputStream fis = new FileInputStream(src);
        byte[] buffer = new byte[16384];
        while (fis.available() != 0) {
            int read = fis.read(buffer);
            stream.write(buffer, 0, read);
        }
        stream.flush();
    }

}
