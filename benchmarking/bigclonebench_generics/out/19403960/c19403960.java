class c19403960 {

    public static Set<String> getProteins(final String goCode, final Set<String> evCodes, final int taxon, final int limit) throws IOException {
        final Set<String> proteins = new HashSet<String>();
        HttpURLConnection connection = null;
        try {
            final String evCodeList = JavaCIPUnknownScope.join(evCodes);
            final URL url = new URL(String.format(JavaCIPUnknownScope.__urlTempl4, goCode, evCodeList, taxon, limit + 1));
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(JavaCIPUnknownScope.__connTimeout);
            connection.setReadTimeout(JavaCIPUnknownScope.__readTimeout);
            connection.setRequestProperty("Connection", "close");
            connection.connect();
            final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                proteins.add(line.trim());
            }
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return JavaCIPUnknownScope.filter(proteins);
    }
}
