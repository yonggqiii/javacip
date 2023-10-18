class c8868054 {

    private String getCurrentUniprotAccession(String accession) throws RuntimeException {
        URL url = new URL(String.format(JavaCIPUnknownScope.UNIPROT_ENTRY_QUERY_STRING, accession));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        HttpURLConnection.setFollowRedirects(true);
        connection.setRequestMethod("HEAD");
        connection.connect();
        if (connection.getResponseCode() != 200) {
            JavaCIPUnknownScope.logger.debug("{} seems to be no UniProt accession", accession);
            throw new RuntimeException("Missing UniProt entry for " + accession);
        }
        String effectiveUrl = connection.getURL().toString();
        String confirmedAccession = effectiveUrl.substring(effectiveUrl.lastIndexOf('/') + 1);
        JavaCIPUnknownScope.logger.debug("getCurrentUniprotAccession: {} -> {}", new Object[] { accession, confirmedAccession });
        return confirmedAccession;
    }
}
