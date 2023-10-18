class c22235113 {

    protected Object unmarshallXml(final Unmarshaller unmarshaller, final String accessUrl, final String nameSpace, final String replace, final String with) throws RuntimeException {
        final URL url = new URL(accessUrl);
        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()));
        String xmlContent = JavaCIPUnknownScope.readWithStringBuffer(inputStream);
        if (replace != null) {
            xmlContent = xmlContent.replace(replace, with);
        }
        JavaCIPUnknownScope.LOGGER.info("Calls " + accessUrl);
        if (JavaCIPUnknownScope.LOGGER.isDebugEnabled()) {
            JavaCIPUnknownScope.LOGGER.debug("\nXml:" + accessUrl + "\n" + xmlContent);
        }
        if (JavaCIPUnknownScope.LOGGER.isDebugEnabled()) {
            final BufferedWriter out = new BufferedWriter(new FileWriter("target/XmlAgentLog" + JavaCIPUnknownScope.xmlRequestNumber++ + ".txt"));
            out.write(xmlContent);
            out.close();
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlContent.getBytes());
        Source source;
        if (nameSpace != null) {
            source = JavaCIPUnknownScope.setNameSpaceOnXmlStream(byteArrayInputStream, nameSpace);
        } else {
            source = new StreamSource(byteArrayInputStream);
        }
        return unmarshaller.unmarshal(source);
    }
}
