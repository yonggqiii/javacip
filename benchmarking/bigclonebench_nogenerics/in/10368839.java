


class c10368839 {

    public static void copy(File source, File destination) throws IORuntimeException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) > 0) out.write(buffer, 0, len);
        in.close();
        out.close();
    }

}
