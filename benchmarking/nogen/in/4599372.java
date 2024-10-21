


class c4599372 {

    @Override
    public String readFixString(final int len) {
        if (len < 1) {
            return StringUtils.EMPTY;
        }
        final StringWriter sw = new StringWriter();
        try {
            IOUtils.copy(createLimitedInputStream(len), sw, null);
        } catch (IORuntimeException e) {
            throw createRuntimeRuntimeException(e);
        }
        return sw.toString();
    }

}
