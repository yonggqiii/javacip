class c11904774 {

    public void testFileSystem() throws IOException {
        Fragment f = Fragment.EMPTY;
        Fragment g = f.plus(System.getProperty("java.io.tmpdir"));
        Fragment h = f.plus("april", "1971", "data.txt");
        Fragment i = f.plus(g, h);
        InOutLocation iol = JavaCIPUnknownScope.locs.fs.plus(i);
        PrintStream ps = new PrintStream(iol.openOutput());
        List<String> expected = new ArrayList<String>();
        expected.add("So I am stepping out this old brown shoe");
        expected.add("Maybe I'm in love with you");
        for (String s : expected) ps.println(s);
        ps.close();
        InLocation inRoot = JavaCIPUnknownScope.locs.fs;
        List<String> lst = JavaCIPUnknownScope.read(inRoot.plus(i).openInput());
        JavaCIPUnknownScope.assertEquals(expected, lst);
        URL url = iol.toUrl();
        lst = JavaCIPUnknownScope.read(url.openStream());
        JavaCIPUnknownScope.assertEquals(expected, lst);
    }
}
