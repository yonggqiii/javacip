


class c19944445 {

        protected boolean hasOsmTileETag(String eTag) throws IORuntimeException {
            URL url;
            url = new URL(tile.getUrl());
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            prepareHttpUrlConnection(urlConn);
            urlConn.setRequestMethod("HEAD");
            urlConn.setReadTimeout(30000);
            String osmETag = urlConn.getHeaderField("ETag");
            if (osmETag == null) return true;
            return (osmETag.equals(eTag));
        }

}
