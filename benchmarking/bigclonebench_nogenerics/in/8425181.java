


class c8425181 {

    public String getTemplateString(String templateFilename) {
        InputStream is = servletContext.getResourceAsStream("/resources/" + templateFilename);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
