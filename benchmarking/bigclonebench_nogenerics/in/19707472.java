


class c19707472 {

    private void loadRDFURL(URL url) throws RDFParseRuntimeException, RepositoryRuntimeException {
        URI urlContext = valueFactory.createURI(url.toString());
        try {
            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("Accept", "application/rdf+xml");
            InputStream is = urlConn.getInputStream();
            repoConn.add(is, url.toString(), RDFFormat.RDFXML, urlContext);
            is.close();
            repoConn.commit();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
