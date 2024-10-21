class c15373370 {

    public VocabularyLocation next() {
        try {
            if (!JavaCIPUnknownScope.urls.isEmpty()) {
                final URL url = JavaCIPUnknownScope.urls.poll();
                return new VocabularyLocation(url.toExternalForm(), VocabularyFormat.RDFXML, 0, url.openStream());
            }
            if (!JavaCIPUnknownScope.files.isEmpty()) {
                File file = JavaCIPUnknownScope.files.poll();
                return new VocabularyLocation(file.getCanonicalPath(), file.getName().endsWith(".ntriples") ? VocabularyFormat.NTRIPLES : VocabularyFormat.RDFXML, file.lastModified(), new FileInputStream(file));
            }
            if (JavaCIPUnknownScope.nextZipEntry != null) {
                String zipEntryAsString = IOUtils.toString(new CloseShieldInputStream(JavaCIPUnknownScope.in), "UTF-8");
                VocabularyLocation location = new VocabularyLocation(JavaCIPUnknownScope.nextZipEntry.getName(), JavaCIPUnknownScope.nextZipEntry.getName().endsWith(".rdf") ? VocabularyFormat.RDFXML : null, JavaCIPUnknownScope.nextZipEntry.getTime(), IOUtils.toInputStream(zipEntryAsString, "UTF-8"));
                JavaCIPUnknownScope.findNextZipEntry();
                return location;
            }
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        throw new NoSuchElementRuntimeException();
    }
}
