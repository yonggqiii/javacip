


class c22670948 {

    private String writeInputStreamToString(InputStream stream) {
        StringWriter stringWriter = new StringWriter();
        try {
            IOUtils.copy(stream, stringWriter);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        String namespaces = stringWriter.toString().trim();
        return namespaces;
    }

}
