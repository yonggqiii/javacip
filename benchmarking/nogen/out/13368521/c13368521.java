class c13368521 {

    public void sendBinaryFile(String filename) throws IORuntimeException {
        Checker.checkEmpty(filename, "filename");
        URL url = JavaCIPUnknownScope._getFile(filename);
        OutputStream out = JavaCIPUnknownScope.getOutputStream();
        Streams.copy(url.openStream(), out);
        out.close();
    }
}
