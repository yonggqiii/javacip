


class c8523656 {

    public String getRssFeedUrl(boolean searchWeb) {
        String rssFeedUrl = null;
        if (entity.getNewsFeedUrl() != null & !entity.getUrl().equals("")) {
            return entity.getNewsFeedUrl();
        } else if (entity.getUrl() == null || entity.getUrl().equals("")) {
            return entity.getNewsFeedUrl();
        } else if (searchWeb) {
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL(entity.getUrl());
                con = (HttpURLConnection) url.openConnection();
                con.connect();
                is = con.getInputStream();
                InputStreamReader sr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(sr);
                String ln;
                StringBuffer sb = new StringBuffer();
                while ((ln = br.readLine()) != null) {
                    sb.append(ln + "\n");
                }
                rssFeedUrl = extractRssFeedUrl(sb.toString());
            } catch (RuntimeException e) {
                log.error(e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IORuntimeException e) {
                        log.error(e);
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
        }
        return rssFeedUrl;
    }

}
