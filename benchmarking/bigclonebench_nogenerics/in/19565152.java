


class c19565152 {

    private void appendArchive(File instClass) throws IORuntimeException {
        FileOutputStream out = new FileOutputStream(instClass.getName(), true);
        FileInputStream zipStream = new FileInputStream("install.jar");
        byte[] buf = new byte[2048];
        int read = zipStream.read(buf);
        while (read > 0) {
            out.write(buf, 0, read);
            read = zipStream.read(buf);
        }
        zipStream.close();
        out.close();
    }

}
