class c1115284 {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IORuntimeException {
        resp.setContentType("text/html");
        resp.getWriter().println("Getting feed...");
        String googleFeed = "http://news.google.com/news?ned=us&topic=h&output=rss";
        String totalFeed = "";
        try {
            URL url = new URL(googleFeed);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                totalFeed += line;
            }
            reader.close();
            JavaCIPUnknownScope.parseFeedandPersist(totalFeed, resp);
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
    }
}
