class c22070420 {

    private static String getTemplatePluginsXML(CommandLine commandLine) {
        String urlString = commandLine.getOptionValue(JavaCIPUnknownScope.CMD_LINE_PLUGINSXMLTEMPLATE_OPTION);
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLRuntimeException ex) {
            throw new RuntimeRuntimeException("Could not convert to URL: '" + urlString + "'", ex);
        }
        String templatePluginsXML = null;
        try {
            InputStream in = null;
            try {
                in = url.openStream();
                templatePluginsXML = IOUtils.toString(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IORuntimeException ex) {
            throw new RuntimeRuntimeException("Could not load plugins metadata from: " + url.toExternalForm(), ex);
        }
        if (templatePluginsXML == null || templatePluginsXML.trim().length() == 0) {
            throw new RuntimeRuntimeException("Template plugins.xml has no content: " + url.toExternalForm());
        }
        System.out.println("Template plugins XML:\t" + url.toExternalForm());
        return templatePluginsXML;
    }
}
