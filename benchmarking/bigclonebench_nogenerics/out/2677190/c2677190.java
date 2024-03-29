class c2677190 {

    private boolean Try(URL url, Metafile mf) throws Throwable {
        InputStream is = null;
        HttpURLConnection con = null;
        boolean success = false;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            Response r = new Response(is);
            JavaCIPUnknownScope.responses.add(r);
            JavaCIPUnknownScope.peers.addAll(r.peers);
            Main.log.info("got " + r.peers.size() + " peers from " + url);
            success = true;
        } finally {
            if (is != null)
                is.close();
            if (con != null)
                con.disconnect();
        }
        return success;
    }
}
