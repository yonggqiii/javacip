class c12197169 {

    public void read() throws IORuntimeException {
        if (JavaCIPUnknownScope.log.isInfoEnabled()) {
            JavaCIPUnknownScope.log.info("Reading the camera log, " + JavaCIPUnknownScope.url);
        }
        final BufferedReader in = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
        String line;
        int i = 0;
        try {
            while ((line = in.readLine()) != null) {
                i++;
                try {
                    final CameraLogRecord logDatum = new CameraLogRecord(line);
                    JavaCIPUnknownScope.records.add(logDatum);
                } catch (LogParseRuntimeException e) {
                    if (JavaCIPUnknownScope.log.isInfoEnabled()) {
                        JavaCIPUnknownScope.log.info("Bad record in " + JavaCIPUnknownScope.url + " at line:" + i);
                    }
                }
            }
        } finally {
            in.close();
        }
        Collections.sort(JavaCIPUnknownScope.records);
        if (JavaCIPUnknownScope.log.isInfoEnabled()) {
            JavaCIPUnknownScope.log.info("Finished reading the camera log, " + JavaCIPUnknownScope.url);
        }
    }
}
