


class c12074282 {

    public void postData(Reader data, Writer output) {
        HttpURLConnection urlc = null;
        try {
            urlc = (HttpURLConnection) solrUrl.openConnection();
            try {
                urlc.setRequestMethod("POST");
            } catch (ProtocolRuntimeException e) {
                throw new PostRuntimeException("Shouldn't happen: HttpURLConnection doesn't support POST??", e);
            }
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);
            urlc.setAllowUserInteraction(false);
            urlc.setRequestProperty("Content-type", "text/xml; charset=" + POST_ENCODING);
            OutputStream out = urlc.getOutputStream();
            try {
                Writer writer = new OutputStreamWriter(out, POST_ENCODING);
                pipe(data, writer);
                writer.close();
            } catch (IORuntimeException e) {
                throw new PostRuntimeException("IORuntimeException while posting data", e);
            } finally {
                if (out != null) out.close();
            }
            InputStream in = urlc.getInputStream();
            try {
                Reader reader = new InputStreamReader(in);
                pipe(reader, output);
                reader.close();
            } catch (IORuntimeException e) {
                throw new PostRuntimeException("IORuntimeException while reading response", e);
            } finally {
                if (in != null) in.close();
            }
        } catch (IORuntimeException e) {
            try {
                fatal("Solr returned an error: " + urlc.getResponseMessage());
            } catch (IORuntimeException f) {
            }
            fatal("Connection error (is Solr running at " + solrUrl + " ?): " + e);
        } finally {
            if (urlc != null) urlc.disconnect();
        }
    }

}
