class c317857 {

    public DataTable generateDataTable(Query query, HttpServletRequest request) throws DataSourceRuntimeException {
        String url = request.getParameter(JavaCIPUnknownScope.URL_PARAM_NAME);
        if (StringUtils.isEmpty(url)) {
            JavaCIPUnknownScope.log.error("url parameter not provided.");
            throw new DataSourceRuntimeException(ReasonType.INVALID_REQUEST, "url parameter not provided");
        }
        Reader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.log.error("url is malformed: " + url);
            throw new DataSourceRuntimeException(ReasonType.INVALID_REQUEST, "url is malformed: " + url);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error("Couldn't read from url: " + url, e);
            throw new DataSourceRuntimeException(ReasonType.INVALID_REQUEST, "Couldn't read from url: " + url);
        }
        DataTable dataTable = null;
        ULocale requestLocale = DataSourceHelper.getLocaleFromRequest(request);
        try {
            dataTable = CsvDataSourceHelper.read(reader, null, true, requestLocale);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error("Couldn't read from url: " + url, e);
            throw new DataSourceRuntimeException(ReasonType.INVALID_REQUEST, "Couldn't read from url: " + url);
        }
        return dataTable;
    }
}
