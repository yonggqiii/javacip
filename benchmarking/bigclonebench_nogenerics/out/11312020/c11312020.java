class c11312020 {

    public static void postData(Reader data, URL endpoint, Writer output) throws RuntimeException {
        HttpURLConnection urlc = null;
        try {
            urlc = (HttpURLConnection) endpoint.openConnection();
            try {
                urlc.setRequestMethod("POST");
            } catch (ProtocolRuntimeException e) {
                throw new RuntimeException("Shouldn't happen: HttpURLConnection doesn't support POST??", e);
            }
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);
            urlc.setAllowUserInteraction(false);
            urlc.setRequestProperty("Content-type", "text/xml; charset=" + "UTF-8");
            OutputStream out = urlc.getOutputStream();
            try {
                Writer writer = new OutputStreamWriter(out, "UTF-8");
                JavaCIPUnknownScope.pipe(data, writer);
                writer.close();
            } catch (IORuntimeException e) {
                throw new RuntimeException("IORuntimeException while posting data", e);
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
                throw new RuntimeException("IORuntimeException while reading response", e);
            } finally {
                if (in != null)
                    in.close();
            }
        } catch (IORuntimeException e) {
            throw new RuntimeException("Connection error (is server running at " + endpoint + " ?): " + e);
        } finally {
            if (urlc != null)
                urlc.disconnect();
        }
    }
}
