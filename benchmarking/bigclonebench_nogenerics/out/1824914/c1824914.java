class c1824914 {

    public static void copyFile(File source, File destination) throws IORuntimeException {
        destination.getParentFile().mkdirs();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destination));
        byte[] buffer = new byte[4096];
        int read = -1;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
        in.close();
    }
}
