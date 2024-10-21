


class c12197169 {

    public void read() throws IORuntimeException {
        if (log.isInfoEnabled()) {
            log.info("Reading the camera log, " + url);
        }
        final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        int i = 0;
        try {
            while ((line = in.readLine()) != null) {
                i++;
                try {
                    final CameraLogRecord logDatum = new CameraLogRecord(line);
                    records.add(logDatum);
                } catch (LogParseRuntimeException e) {
                    if (log.isInfoEnabled()) {
                        log.info("Bad record in " + url + " at line:" + i);
                    }
                }
            }
        } finally {
            in.close();
        }
        Collections.sort(records);
        if (log.isInfoEnabled()) {
            log.info("Finished reading the camera log, " + url);
        }
    }

}
