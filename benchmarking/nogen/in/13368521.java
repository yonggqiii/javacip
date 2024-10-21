


class c13368521 {

    public void sendBinaryFile(String filename) throws IORuntimeException {
        Checker.checkEmpty(filename, "filename");
        URL url = _getFile(filename);
        OutputStream out = getOutputStream();
        Streams.copy(url.openStream(), out);
        out.close();
    }

}
