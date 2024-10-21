class c5683576 {

    public String shorten(String url) {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("version", "2.0.1"));
        qparams.add(new BasicNameValuePair("longUrl", url));
        if (JavaCIPUnknownScope.login != null) {
            qparams.add(new BasicNameValuePair("login", JavaCIPUnknownScope.login));
            qparams.add(new BasicNameValuePair("apiKey", JavaCIPUnknownScope.apiKey));
            qparams.add(new BasicNameValuePair("history", "1"));
        }
        try {
            BasicHttpParams params = new BasicHttpParams();
            DefaultHttpClient httpclient = new DefaultHttpClient(params);
            String qparamsf = URLEncodedUtils.format(qparams, "UTF-8");
            URI uri = URIUtils.createURI("http", "api.j.mp", -1, "/shorten", qparamsf, null);
            HttpGet httpget = new HttpGet(uri);
            URI httpgeturi = httpget.getURI();
            if (JavaCIPUnknownScope.logger.isDebugEnabled())
                JavaCIPUnknownScope.logger.debug("HttpGet.uri={}", httpgeturi);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                JsonFactory f = new JsonFactory();
                JsonParser jp = f.createJsonParser(instream);
                JmpShortenResponse responseObj = new JmpShortenResponse();
                for (; ; ) {
                    JsonToken token = jp.nextToken();
                    String fieldname = jp.getCurrentName();
                    if (JavaCIPUnknownScope.logger.isDebugEnabled())
                        JavaCIPUnknownScope.logger.debug("Token={}, currentName={}", token, fieldname);
                    if (token == JsonToken.START_OBJECT) {
                        continue;
                    }
                    if (token == JsonToken.END_OBJECT) {
                        break;
                    }
                    if ("errorCode".equals(fieldname)) {
                        token = jp.nextToken();
                        responseObj.setErrorCode(jp.getIntValue());
                    } else if ("errorMessage".equals(fieldname)) {
                        token = jp.nextToken();
                        responseObj.setErrorMessage(jp.getText());
                    } else if ("statusCode".equals(fieldname)) {
                        token = jp.nextToken();
                        responseObj.setStatusCode(jp.getText());
                    } else if ("results".equals(fieldname)) {
                        Map<String, ShortenedUrl> results = JavaCIPUnknownScope.parseResults(jp);
                        responseObj.setResults(results);
                    } else {
                        throw new IllegalStateException("Unrecognized field '" + fieldname + "'!");
                    }
                }
                Map<String, ShortenedUrl> results = responseObj.getResults();
                if (results == null) {
                    return null;
                }
                ShortenedUrl shortened = results.get(url);
                if (shortened == null) {
                    return null;
                }
                if (JavaCIPUnknownScope.logger.isDebugEnabled())
                    JavaCIPUnknownScope.logger.debug("JmpShortenResponse: {}", responseObj);
                if ("OK".equals(responseObj.getStatusCode())) {
                    return shortened.getShortUrl();
                }
                if (JavaCIPUnknownScope.logger.isWarnEnabled())
                    JavaCIPUnknownScope.logger.warn("JmpShortenResponse: {}", responseObj);
            }
        } catch (IOException ex) {
            if (JavaCIPUnknownScope.logger.isWarnEnabled())
                JavaCIPUnknownScope.logger.warn("Exception!", ex);
        } catch (URISyntaxException ex) {
            if (JavaCIPUnknownScope.logger.isWarnEnabled())
                JavaCIPUnknownScope.logger.warn("Exception!", ex);
        }
        return null;
    }
}
