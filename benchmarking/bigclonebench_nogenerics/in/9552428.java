


class c9552428 {

    public static String stringOfUrl(String addr) throws IORuntimeException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.out.println("test");
        URL url = new URL(addr);
        System.out.println("test2");
        IOUtils.copy(url.openStream(), output);
        return output.toString();
    }

}
