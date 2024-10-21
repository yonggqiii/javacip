


class c9880330 {

    protected static String stringOfUrl(String addr) throws IORuntimeException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        URL url = new URL(addr);
        URLConnection c = url.openConnection();
        c.setConnectTimeout(2000);
        IOUtils.copy(c.getInputStream(), output);
        return output.toString();
    }

}
