


class c7089854 {

    @Override
    public Directory directory() {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        try {
            URL url = new URL(DIRECTORY_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            String encoding = urlConnection.getContentEncoding();
            if ("gzip".equalsIgnoreCase(encoding)) {
                in = new GZIPInputStream(urlConnection.getInputStream());
            } else if ("deflate".equalsIgnoreCase(encoding)) {
                in = new InflaterInputStream(urlConnection.getInputStream(), new Inflater(true));
            } else {
                in = urlConnection.getInputStream();
            }
            return persister.read(IcecastDirectory.class, in);
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException("Failed to get directory", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
