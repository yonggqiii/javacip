class c12074282 {

    public void postData(Reader data, Writer output) {
        HttpURLConnection urlc = null;
        try {
            urlc = (HttpURLConnection) JavaCIPUnknownScope.solrUrl.openConnection();
            try {
                urlc.setRequestMethod("POST");
            } catch (ProtocolRuntimeException e) {
                throw new PostRuntimeException("Shouldn't happen: HttpURLConnection doesn't support POST??", e);
            }
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);
            urlc.setAllowUserInteraction(false);
            urlc.setRequestProperty("Content-type", "text/xml; charset=" + JavaCIPUnknownScope.POST_ENCODING);
            OutputStream out = urlc.getOutputStream();
            try {
                Writer writer = new OutputStreamWriter(out, JavaCIPUnknownScope.POST_ENCODING);
                JavaCIPUnknownScope.pipe(data, writer);
                writer.close();
            } catch (IORuntimeException e) {
                throw new PostRuntimeException("IORuntimeException while posting data", e);
            } finally {
                if (out != null)
                    out.close();
            }
            InputStream in = urlc.getInputStream();
            try {
                Reader reader = new InputStreamReader(in);
                JavaCIPUnknownScope.pipe(reader, output);
                reader.close();
            } catch (IORuntimeException e) {
                throw new PostRuntimeException("IORuntimeException while reading response", e);
            } finally {
                if (in != null)
                    in.close();
            }
        } catch (IORuntimeException e) {
            try {
                JavaCIPUnknownScope.fatal("Solr returned an error: " + urlc.getResponseMessage());
            } catch (IORuntimeException f) {
            }
            JavaCIPUnknownScope.fatal("Connection error (is Solr running at " + JavaCIPUnknownScope.solrUrl + " ?): " + e);
        } finally {
            if (urlc != null)
                urlc.disconnect();
        }
    }
}
