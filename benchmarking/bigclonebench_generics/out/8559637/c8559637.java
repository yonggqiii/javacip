class c8559637 {

    protected String updateTwitter() {
        if (JavaCIPUnknownScope.updatingTwitter)
            return null;
        JavaCIPUnknownScope.updatingTwitter = true;
        String highestId = null;
        final Cursor cursor = JavaCIPUnknownScope.query(JavaCIPUnknownScope.TWITTER_TABLE, new String[] { JavaCIPUnknownScope.KEY_TWEET_ID }, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            highestId = cursor.getString(cursor.getColumnIndex(JavaCIPUnknownScope.KEY_TWEET_ID));
        }
        cursor.close();
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("screen_name", JavaCIPUnknownScope.TWITTER_ACCOUNT));
        nameValuePairs.add(new BasicNameValuePair("count", "" + JavaCIPUnknownScope.MAX_TWEETS));
        if (highestId != null)
            nameValuePairs.add(new BasicNameValuePair("since_id", highestId));
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        final HttpParams params = new BasicHttpParams();
        final SingleClientConnManager mgr = new SingleClientConnManager(params, schemeRegistry);
        final HttpClient httpclient = new DefaultHttpClient(mgr, params);
        final HttpGet request = new HttpGet();
        final String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
        String data = "";
        try {
            final URI uri = new URI(JavaCIPUnknownScope.TWITTER_URL + "?" + paramString);
            request.setURI(uri);
            final HttpResponse response = httpclient.execute(request);
            final BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) data += inputLine;
            in.close();
        } catch (final URISyntaxException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.updatingTwitter = false;
            return "failed";
        } catch (final ClientProtocolException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.updatingTwitter = false;
            return "failed";
        } catch (final IOException e) {
            e.printStackTrace();
            JavaCIPUnknownScope.updatingTwitter = false;
            return "failed";
        }
        try {
            final JSONArray tweets = new JSONArray(data);
            if (tweets == null) {
                JavaCIPUnknownScope.updatingTwitter = false;
                return "failed";
            }
            if (tweets.length() == 0) {
                JavaCIPUnknownScope.updatingTwitter = false;
                return "none";
            }
            final SimpleDateFormat formatter = new SimpleDateFormat(JavaCIPUnknownScope.DATE_FORMAT, Locale.ENGLISH);
            final SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
            for (int i = 0; i < tweets.length(); i++) {
                final JSONObject tweet = tweets.getJSONObject(i);
                final ContentValues values = new ContentValues();
                Log.v(JavaCIPUnknownScope.TAG, "Datum van tweet: " + tweet.getString(JavaCIPUnknownScope.KEY_TWEET_DATE));
                values.put(JavaCIPUnknownScope.KEY_TWEET_DATE, formatter.format(parser.parse(tweet.getString(JavaCIPUnknownScope.KEY_TWEET_DATE))));
                values.put(JavaCIPUnknownScope.KEY_TWEET_TEXT, tweet.getString(JavaCIPUnknownScope.KEY_TWEET_TEXT));
                values.put(JavaCIPUnknownScope.KEY_TWEET_ID, tweet.getString(JavaCIPUnknownScope.KEY_TWEET_ID));
                JavaCIPUnknownScope.insert(JavaCIPUnknownScope.TWITTER_TABLE, values);
            }
        } catch (final JSONException e) {
            Log.v(JavaCIPUnknownScope.TAG, "JSON decodering is mislukt.");
            e.printStackTrace();
            JavaCIPUnknownScope.updatingTwitter = false;
            return "failed";
        } catch (final ParseException e) {
            Log.v(JavaCIPUnknownScope.TAG, "Datum decodering is mislukt.");
            e.printStackTrace();
            JavaCIPUnknownScope.updatingTwitter = false;
            return "failed";
        }
        JavaCIPUnknownScope.purgeTweets();
        JavaCIPUnknownScope.updatingTwitter = false;
        return "success";
    }
}
