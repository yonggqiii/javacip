class c7458833 {

    private void copyParseFileToCodeFile() throws IORuntimeException {
        InputStream in = new FileInputStream(new File(JavaCIPUnknownScope.filenameParse));
        OutputStream out = new FileOutputStream(new File(JavaCIPUnknownScope.filenameMisc));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) out.write(buffer, 0, length);
        in.close();
        out.close();
    }
}
