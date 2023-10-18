class c5675398 {

    private TupleQueryResult evaluate(String location, String query, QueryLanguage queryLn) throws RuntimeException {
        location += "?query=" + URLEncoder.encode(query, "UTF-8") + "&queryLn=" + queryLn.getName();
        URL url = new URL(location);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", TupleQueryResultFormat.SPARQL.getDefaultMIMEType());
        conn.connect();
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return QueryResultIO.parse(conn.getInputStream(), TupleQueryResultFormat.SPARQL);
            } else {
                String response = "location " + location + " responded: " + conn.getResponseMessage() + " (" + responseCode + ")";
                JavaCIPUnknownScope.fail(response);
                throw new RuntimeRuntimeException(response);
            }
        } finally {
            conn.disconnect();
        }
    }
}
