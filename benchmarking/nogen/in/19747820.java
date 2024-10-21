


class c19747820 {

    public String parse(String term) throws OntologyAdaptorRuntimeException {
        try {
            String sUrl = getUrl(term);
            if (sUrl.length() > 0) {
                URL url = new URL(sUrl);
                InputStream in = url.openStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = r.readLine()) != null) {
                    if (sb.length() > 0) {
                        sb.append("\r\n");
                    }
                    sb.append(line);
                }
                return sb.toString();
            } else {
                return "";
            }
        } catch (RuntimeException ex) {
            throw new OntologyAdaptorRuntimeException("Convertion to lucene failed.", ex);
        }
    }

}
