class c10535319 {

    private static Collection<String> createTopLevelFiles(Configuration configuration, Collections collections, Sets sets) throws FlickrException, SAXException, IOException, JDOMException, TransformerException {
        Collection<String> createdFiles = new HashSet<String>();
        File toplevelXmlFilename = JavaCIPUnknownScope.getToplevelXmlFilename(configuration.photosBaseDirectory);
        Logger.getLogger(FlickrDownload.class).info("Creating XML file " + toplevelXmlFilename.getAbsolutePath());
        MediaIndexer indexer = new XmlMediaIndexer(configuration);
        Element toplevel = new Element("flickr").addContent(XmlUtils.createApplicationXml()).addContent(XmlUtils.createUserXml(configuration)).addContent(collections.createTopLevelXml()).addContent(sets.createTopLevelXml()).addContent(new Stats(sets).createStatsXml(indexer));
        createdFiles.addAll(indexer.writeIndex());
        XmlUtils.outputXmlFile(toplevelXmlFilename, toplevel);
        createdFiles.add(toplevelXmlFilename.getName());
        Logger.getLogger(FlickrDownload.class).info("Copying support files and performing XSLT transformations");
        IOUtils.copyToFileAndCloseStreams(XmlUtils.class.getResourceAsStream("xslt/" + JavaCIPUnknownScope.PHOTOS_CSS_FILENAME), new File(configuration.photosBaseDirectory, JavaCIPUnknownScope.PHOTOS_CSS_FILENAME));
        createdFiles.add(JavaCIPUnknownScope.PHOTOS_CSS_FILENAME);
        IOUtils.copyToFileAndCloseStreams(XmlUtils.class.getResourceAsStream("xslt/" + JavaCIPUnknownScope.PLAY_ICON_FILENAME), new File(configuration.photosBaseDirectory, JavaCIPUnknownScope.PLAY_ICON_FILENAME));
        createdFiles.add(JavaCIPUnknownScope.PLAY_ICON_FILENAME);
        XmlUtils.performXsltTransformation(configuration, "all_sets.xsl", toplevelXmlFilename, new File(configuration.photosBaseDirectory, JavaCIPUnknownScope.ALL_SETS_HTML_FILENAME));
        createdFiles.add(JavaCIPUnknownScope.ALL_SETS_HTML_FILENAME);
        XmlUtils.performXsltTransformation(configuration, "all_collections.xsl", toplevelXmlFilename, new File(configuration.photosBaseDirectory, JavaCIPUnknownScope.ALL_COLLECTIONS_HTML_FILENAME));
        createdFiles.add(JavaCIPUnknownScope.ALL_COLLECTIONS_HTML_FILENAME);
        createdFiles.add(Collections.COLLECTIONS_ICON_DIRECTORY);
        XmlUtils.performXsltTransformation(configuration, "stats.xsl", toplevelXmlFilename, new File(configuration.photosBaseDirectory, JavaCIPUnknownScope.STATS_HTML_FILENAME));
        createdFiles.add(JavaCIPUnknownScope.STATS_HTML_FILENAME);
        sets.performXsltTransformation();
        for (AbstractSet set : sets.getSets()) {
            createdFiles.add(set.getSetId());
        }
        return createdFiles;
    }
}
