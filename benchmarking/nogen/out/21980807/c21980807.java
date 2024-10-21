class c21980807 {

    public void checkAndDownload(String statsUrl, RDFStatsUpdatableModelExt stats, Date lastDownload, boolean onlyIfNewer) throws DataSourceMonitorRuntimeException {
        if (JavaCIPUnknownScope.log.isInfoEnabled())
            JavaCIPUnknownScope.log.info("Checking if update required for statistics of " + JavaCIPUnknownScope.ds + "...");
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(statsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(JavaCIPUnknownScope.CONNECT_TIMEOUT);
            urlConnection.setReadTimeout(JavaCIPUnknownScope.READ_TIMEOUT);
            int statusCode = urlConnection.getResponseCode();
            if (statusCode / 100 != 2) {
                String msg = urlConnection.getResponseMessage();
                throw new DataSourceMonitorRuntimeException(statsUrl + " returned HTTP " + statusCode + (msg != null ? msg : "") + ".");
            }
        } catch (RuntimeException e) {
            throw new DataSourceMonitorRuntimeException("Failed to connect to " + statsUrl + ".", e);
        }
        long lastModified = urlConnection.getLastModified();
        boolean newer = lastDownload == null || lastModified == 0 || lastModified - JavaCIPUnknownScope.TIMING_GAP > lastDownload.getTime();
        if (newer || !onlyIfNewer) {
            Model newStats = JavaCIPUnknownScope.retrieveModelData(urlConnection, JavaCIPUnknownScope.ds);
            Date retrievedTimestamp = Calendar.getInstance().getTime();
            Date modifiedTimestamp = (urlConnection.getLastModified() > 0) ? new Date(urlConnection.getLastModified()) : null;
            if (JavaCIPUnknownScope.log.isInfoEnabled())
                JavaCIPUnknownScope.log.info("Attempt to import up-to-date " + ((modifiedTimestamp != null) ? "(from " + modifiedTimestamp + ") " : "") + "statistics for " + JavaCIPUnknownScope.ds + ".");
            try {
                if (stats.updateFrom(RDFStatsModelFactory.create(newStats), onlyIfNewer))
                    stats.setLastDownload(JavaCIPUnknownScope.ds.getSPARQLEndpointURL(), retrievedTimestamp);
            } catch (RuntimeException e) {
                throw new DataSourceMonitorRuntimeException("Failed to import statistics and set last download for " + JavaCIPUnknownScope.ds + ".", e);
            }
        } else {
            if (JavaCIPUnknownScope.log.isInfoEnabled())
                JavaCIPUnknownScope.log.info("Statistics for " + JavaCIPUnknownScope.ds + " are up-to-date" + ((lastDownload != null) ? " (" + lastDownload + ")" : ""));
        }
    }
}
