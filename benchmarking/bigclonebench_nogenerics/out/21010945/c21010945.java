class c21010945 {

    private static List runITQLQuery(String itqlQuery) throws RuntimeException {
        String escapedItqlQuery = URLEncoder.encode(itqlQuery, "UTF-8");
        String url = "http://" + Config.getProperty("FEDORA_SOAP_HOST") + ":" + Config.getProperty("FEDORA_SOAP_ACCESS_PORT") + "/fedora/risearch?type=tuples" + "&lang=iTQL" + "&format=CSV" + "&distinct=on" + "&stream=on" + "&query=" + escapedItqlQuery;
        JavaCIPUnknownScope.logger.debug("url for risearch query: " + url);
        URL urlObject = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JavaCIPUnknownScope.logger.debug("response code: " + con.getResponseCode());
        if (con.getResponseCode() != 200 && con.getResponseCode() != 302) {
            throw new FedoraAccessRuntimeException("Could not access the risearch service at url: " + url);
        }
        ArrayList arrayList = new ArrayList();
        String inputLine;
        int counter = 0;
        while ((inputLine = br.readLine()) != null) {
            JavaCIPUnknownScope.logger.debug("reading line:" + inputLine);
            if (inputLine.indexOf("<html>") >= 0) {
                JavaCIPUnknownScope.logger.error("problem quering the relationship");
                throw new RuntimeException("Problem querying relationships; probably a bad ITQL query:" + itqlQuery);
            }
            if (counter >= 1 && inputLine.indexOf("/") >= 0 && inputLine.trim().length() > 0) {
                JavaCIPUnknownScope.logger.debug("adding line:" + inputLine);
                inputLine = inputLine.substring(inputLine.indexOf("/") + 1);
                arrayList.add(inputLine);
                JavaCIPUnknownScope.logger.debug("found relationship to item: " + inputLine);
            }
            counter++;
        }
        br.close();
        JavaCIPUnknownScope.logger.debug("num relationships found: " + arrayList.size());
        return arrayList;
    }
}
