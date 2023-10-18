class c22160879 {

    public boolean parseResults(URL url, String analysis_type, CurationI curation, Date analysis_date, String regexp) throws OutputMalFormatRuntimeException {
        boolean parsed = false;
        try {
            InputStream data_stream = url.openStream();
            parsed = parseResults(data_stream, analysis_type, curation, analysis_date, regexp);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return parsed;
    }
}
