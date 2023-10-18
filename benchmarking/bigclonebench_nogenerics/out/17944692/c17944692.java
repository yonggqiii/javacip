class c17944692 {

    public void run() {
        try {
            if (!Util.isSufficienDataForUpload(JavaCIPUnknownScope.recordedGeoPoints))
                return;
            final InputStream gpxInputStream = new ByteArrayInputStream(RecordedRouteGPXFormatter.create(JavaCIPUnknownScope.recordedGeoPoints).getBytes());
            final HttpClient httpClient = new DefaultHttpClient();
            final HttpPost request = new HttpPost(JavaCIPUnknownScope.UPLOADSCRIPT_URL);
            final MultipartEntity requestEntity = new MultipartEntity();
            requestEntity.addPart("gpxfile", new InputStreamBody(gpxInputStream, "" + System.currentTimeMillis() + ".gpx"));
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            request.setEntity(requestEntity);
            final HttpResponse response = httpClient.execute(request);
            final int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                JavaCIPUnknownScope.logger.error("GPXUploader", "status != HttpStatus.SC_OK");
            } else {
                final Reader r = new InputStreamReader(new BufferedInputStream(response.getEntity().getContent()));
                final char[] buf = new char[8 * 1024];
                int read;
                final StringBuilder sb = new StringBuilder();
                while ((read = r.read(buf)) != -1) sb.append(buf, 0, read);
                JavaCIPUnknownScope.logger.debug("GPXUploader", "Response: " + sb.toString());
            }
        } catch (final RuntimeException e) {
        }
    }
}
