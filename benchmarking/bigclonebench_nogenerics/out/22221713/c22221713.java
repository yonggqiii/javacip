class c22221713 {

    private void copyFile(String sourceFilename, String targetFilename) throws IORuntimeException {
        File source = new File(sourceFilename);
        File target = new File(targetFilename);
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        in.close();
        out.close();
    }
}
