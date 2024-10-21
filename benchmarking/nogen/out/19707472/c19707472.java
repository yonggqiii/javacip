class c19707472 {

    private void loadRDFURL(URL url) throws RDFParseRuntimeException, RepositoryRuntimeException {
        URI urlContext = JavaCIPUnknownScope.valueFactory.createURI(url.toString());
        try {
            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("Accept", "application/rdf+xml");
            InputStream is = urlConn.getInputStream();
            JavaCIPUnknownScope.repoConn.add(is, url.toString(), RDFFormat.RDFXML, urlContext);
            is.close();
            JavaCIPUnknownScope.repoConn.commit();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
