class c13680335 {

    private String fetchCompareContent() throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.compareTo);
        StringWriter sw = new StringWriter();
        IOUtils.copy(url.openStream(), sw);
        return sw.getBuffer().toString();
    }
}
