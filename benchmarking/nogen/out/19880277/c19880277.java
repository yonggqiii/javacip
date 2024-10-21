class c19880277 {

    private void postUrl() throws RuntimeException {
        String authors = "";
        for (String auth : JavaCIPUnknownScope.plugin.getDescription().getAuthors()) {
            authors = authors + " " + auth;
        }
        authors = authors.trim();
        String url = String.format("http://bukkitstats.randomappdev.com/ping.aspx?snam=%s&sprt=%s&shsh=%s&sver=%s&spcnt=%s&pnam=%s&pmcla=%s&paut=%s&pweb=%s&pver=%s", URLEncoder.encode(JavaCIPUnknownScope.plugin.getServer().getName(), "UTF-8"), JavaCIPUnknownScope.plugin.getServer().getPort(), JavaCIPUnknownScope.hash, URLEncoder.encode(Bukkit.getVersion(), "UTF-8"), JavaCIPUnknownScope.plugin.getServer().getOnlinePlayers().length, URLEncoder.encode(JavaCIPUnknownScope.plugin.getDescription().getName(), "UTF-8"), URLEncoder.encode(JavaCIPUnknownScope.plugin.getDescription().getMain(), "UTF-8"), URLEncoder.encode(authors, "UTF-8"), URLEncoder.encode(JavaCIPUnknownScope.plugin.getDescription().getWebsite(), "UTF-8"), URLEncoder.encode(JavaCIPUnknownScope.plugin.getDescription().getVersion(), "UTF-8"));
        new URL(url).openConnection().getInputStream();
    }
}
