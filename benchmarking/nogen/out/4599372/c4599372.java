class c4599372 {

    public String readFixString(final int len) {
        if (len < 1) {
            return StringUtils.EMPTY;
        }
        final StringWriter sw = new StringWriter();
        try {
            IOUtils.copy(JavaCIPUnknownScope.createLimitedInputStream(len), sw, null);
        } catch (IORuntimeException e) {
            throw JavaCIPUnknownScope.createRuntimeRuntimeException(e);
        }
        return sw.toString();
    }
}
