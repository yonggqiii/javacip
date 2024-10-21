class c12883277 {

    private void createWikiPages(WikiContext context) throws PluginRuntimeException {
        OntologyWikiPageName owpn = new OntologyWikiPageName(JavaCIPUnknownScope.omemo.getFormDataAlias().toUpperCase(), JavaCIPUnknownScope.omemo.getFormDataVersionDate());
        String wikiPageFullFileName = JavaCIPUnknownScope.WikiPageName2FullFileName(context, owpn.toString());
        String rdfFileNameWithPath = JavaCIPUnknownScope.getWorkDir(context) + File.separator + owpn.toFileName();
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(wikiPageFullFileName);
            fis = new FileInputStream(rdfFileNameWithPath);
            InfoExtractor infoe = new InfoExtractor(fis, JavaCIPUnknownScope.omemo.getFormDataNS(), JavaCIPUnknownScope.omemo.getFormDataOntLang());
            infoe.writePage(JavaCIPUnknownScope.getWorkDir(context), owpn, Omemo.checksWikiPageName);
            fis.close();
            fos.close();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("Can not read local rdf file or can not write wiki page");
            throw new PluginRuntimeException("Error creating wiki pages. See logs");
        }
    }
}
