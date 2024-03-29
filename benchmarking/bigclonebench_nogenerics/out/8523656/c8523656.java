class c8523656 {

    public String getRssFeedUrl(boolean searchWeb) {
        String rssFeedUrl = null;
        if (JavaCIPUnknownScope.entity.getNewsFeedUrl() != null & !JavaCIPUnknownScope.entity.getUrl().equals("")) {
            return JavaCIPUnknownScope.entity.getNewsFeedUrl();
        } else if (JavaCIPUnknownScope.entity.getUrl() == null || JavaCIPUnknownScope.entity.getUrl().equals("")) {
            return JavaCIPUnknownScope.entity.getNewsFeedUrl();
        } else if (searchWeb) {
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL(JavaCIPUnknownScope.entity.getUrl());
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
                rssFeedUrl = JavaCIPUnknownScope.extractRssFeedUrl(sb.toString());
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.log.error(e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IORuntimeException e) {
                        JavaCIPUnknownScope.log.error(e);
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
