


class c13368520 {

    public void sendTextFile(String filename) throws IORuntimeException {
        Checker.checkEmpty(filename, "filename");
        URL url = _getFile(filename);
        PrintWriter out = getWriter();
        Streams.copy(new InputStreamReader(url.openStream()), out);
        out.close();
    }

}
