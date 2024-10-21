


class c5195056 {

    public String getData() throws ValueFormatRuntimeException, RepositoryRuntimeException, IORuntimeException {
        InputStream is = getStream();
        StringWriter sw = new StringWriter();
        IOUtils.copy(is, sw, "UTF-8");
        IOUtils.closeQuietly(is);
        return sw.toString();
    }

}
