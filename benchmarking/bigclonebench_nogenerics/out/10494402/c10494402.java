class c10494402 {

    private Comic[] getAllComics() {
        try {
            URL comicURL = new URL(JavaCIPUnknownScope.comicSite + "list");
            InputStream is = comicURL.openStream();
            BufferedReader buffread = new BufferedReader(new InputStreamReader(is));
            Vector tmplist = new Vector();
            while (buffread.ready()) {
                String comic = buffread.readLine();
                tmplist.add(comic);
            }
            Comic[] list = new Comic[tmplist.size()];
            JavaCIPUnknownScope.activated = new boolean[tmplist.size()];
            JavaCIPUnknownScope.titles = new String[tmplist.size()];
            for (int i = 0; i < tmplist.size(); i++) {
                try {
                    URL curl = new URL(JavaCIPUnknownScope.comicSite + (String) tmplist.get(i));
                    BufferedInputStream bis = new BufferedInputStream(curl.openStream());
                    Properties cprop = new Properties();
                    cprop.load(bis);
                    Comic c = new Comic(cprop, false);
                    list[i] = c;
                    JavaCIPUnknownScope.titles[i] = c.getName();
                    JavaCIPUnknownScope.activated[i] = JavaCIPUnknownScope.comicsmanager.isLoaded(c.getName());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < list.length; i++) {
                System.out.println(list[i]);
            }
            return list;
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
