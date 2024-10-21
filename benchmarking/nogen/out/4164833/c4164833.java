class c4164833 {

    public static void buildDeb(File debFile, File controlFile, File dataFile) throws IORuntimeException {
        long now = new Date().getTime() / 1000;
        OutputStream deb = new FileOutputStream(debFile);
        deb.write("!<arch>\n".getBytes());
        JavaCIPUnknownScope.startFileEntry(deb, JavaCIPUnknownScope.DEBIAN_BINARY_NAME, now, JavaCIPUnknownScope.DEBIAN_BINARY_CONTENT.length());
        deb.write(JavaCIPUnknownScope.DEBIAN_BINARY_CONTENT.getBytes());
        JavaCIPUnknownScope.endFileEntry(deb, JavaCIPUnknownScope.DEBIAN_BINARY_CONTENT.length());
        JavaCIPUnknownScope.startFileEntry(deb, JavaCIPUnknownScope.CONTROL_NAME, now, controlFile.length());
        FileInputStream control = new FileInputStream(controlFile);
        byte[] buffer = new byte[1024];
        while (true) {
            int read = control.read(buffer);
            if (read == -1)
                break;
            deb.write(buffer, 0, read);
        }
        control.close();
        JavaCIPUnknownScope.endFileEntry(deb, controlFile.length());
        JavaCIPUnknownScope.startFileEntry(deb, JavaCIPUnknownScope.DATA_NAME, now, dataFile.length());
        FileInputStream data = new FileInputStream(dataFile);
        while (true) {
            int read = data.read(buffer);
            if (read == -1)
                break;
            deb.write(buffer, 0, read);
        }
        data.close();
        JavaCIPUnknownScope.endFileEntry(deb, dataFile.length());
        deb.close();
    }
}
