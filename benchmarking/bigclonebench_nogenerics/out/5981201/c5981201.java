class c5981201 {

    public static void copyFile(File src, File dst) throws IORuntimeException {
        if (JavaCIPUnknownScope.T.t)
            JavaCIPUnknownScope.T.info("Copying " + src + " -> " + dst + "...");
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[40 * JavaCIPUnknownScope.KB];
        int read;
        while ((read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
        }
        out.flush();
        out.close();
        in.close();
        if (JavaCIPUnknownScope.T.t)
            JavaCIPUnknownScope.T.info("File copied.");
    }
}
