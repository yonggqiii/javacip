


class c16458721 {

    public String stringOfUrl(String addr) throws IORuntimeException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        URL url = new URL(addr);
        IOUtils.copy(url.openStream(), output);
        return output.toString();
    }

}
