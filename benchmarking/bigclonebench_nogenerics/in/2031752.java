


class c2031752 {

    public String readFile(String filename) throws UnsupportedEncodingRuntimeException, FileNotFoundRuntimeException, IORuntimeException {
        File f = new File(baseDir);
        f = new File(f, filename);
        StringWriter w = new StringWriter();
        Reader fr = new InputStreamReader(new FileInputStream(f), "UTF-8");
        IOUtils.copy(fr, w);
        fr.close();
        w.close();
        String contents = w.toString();
        return contents;
    }

}
