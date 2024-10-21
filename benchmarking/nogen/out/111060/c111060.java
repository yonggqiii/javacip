class c111060 {

    public void load(URL urlin) throws IORuntimeException {
        JavaCIPUnknownScope.index = JavaCIPUnknownScope.hs.getDoIndex();
        JavaCIPUnknownScope.loaded = false;
        JavaCIPUnknownScope.url = urlin;
        int c, i;
        JavaCIPUnknownScope.htmlDocLength = 0;
        HtmlReader in = new HtmlReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream(), JavaCIPUnknownScope.charset));
        try {
            if (JavaCIPUnknownScope.debug >= 2)
                System.out.print("Loading " + urlin.toString() + " ... ");
            while ((c = in.read()) >= 0) {
                JavaCIPUnknownScope.htmlDoc[JavaCIPUnknownScope.htmlDocLength++] = (char) (c);
                if (JavaCIPUnknownScope.htmlDocLength == JavaCIPUnknownScope.htmlDocMaxLength) {
                    char[] newHtmlDoc = new char[2 * JavaCIPUnknownScope.htmlDocMaxLength];
                    System.arraycopy(JavaCIPUnknownScope.htmlDoc, 0, newHtmlDoc, 0, JavaCIPUnknownScope.htmlDocMaxLength);
                    JavaCIPUnknownScope.htmlDocMaxLength = 2 * JavaCIPUnknownScope.htmlDocMaxLength;
                    JavaCIPUnknownScope.htmlDoc = newHtmlDoc;
                }
            }
            if (JavaCIPUnknownScope.debug >= 2)
                System.out.println("done.");
        } catch (ArrayIndexOutOfBoundsRuntimeException aioobe) {
            if (JavaCIPUnknownScope.debug >= 1)
                System.out.println("Error, reading file into memory (too big) - skipping " + urlin.toString());
            JavaCIPUnknownScope.loaded = false;
            return;
        }
        in.close();
        JavaCIPUnknownScope.fetchURLpos = 0;
        JavaCIPUnknownScope.dumpPos = 0;
        JavaCIPUnknownScope.dumpLastChar = JavaCIPUnknownScope.SPACE;
        JavaCIPUnknownScope.loaded = true;
        JavaCIPUnknownScope.frameset = false;
        JavaCIPUnknownScope.titledone = false;
        JavaCIPUnknownScope.headdone = false;
        JavaCIPUnknownScope.checkhead = false;
        JavaCIPUnknownScope.checkbody = false;
    }
}
