


class c1849189 {

    public InputPort getInputPort(String file) throws IORuntimeException {
        if (file.equals("/dev/null")) {
            return new StreamInputPort(new NullInputStream(), file);
        }
        URL url = Util.tryURL(file);
        if (url != null) {
            return new StreamInputPort(url.openStream(), url.toExternalForm());
        } else return new FileInputPort(getFile(file));
    }

}
