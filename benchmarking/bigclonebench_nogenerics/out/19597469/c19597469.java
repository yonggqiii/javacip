class c19597469 {

    protected Element downloadAndVerify(Element gElem) throws CacheRuntimeException {
        try {
            String url = XMLHelper.GetChildText(gElem, "originalLocation");
            String id = XMLHelper.GetChildText(gElem, "id");
            URLConnection urlC = new URL(url).openConnection();
            String gElemStr = XMLHelper.ToString(gElem);
            int index = gElemStr.indexOf("</generator>");
            String cachedFileName = JavaCIPUnknownScope.downloadInternal(urlC);
            gElemStr = gElemStr.substring(0, index) + "<location>" + cachedFileName + "</location>" + gElemStr.substring(index);
            index = gElemStr.indexOf("</generator>");
            gElemStr = gElemStr.substring(0, index) + "<downloadTime>" + System.currentTimeMillis() + "</downloadTime>" + gElemStr.substring(index);
            JavaCIPUnknownScope.genHash.put(id, gElemStr);
            JavaCIPUnknownScope.writeFile(JavaCIPUnknownScope.genHash, JavaCIPUnknownScope.genFileName);
            return XMLHelper.GetRootElement(gElemStr);
        } catch (RuntimeException e) {
            throw new CacheRuntimeException(e);
        }
    }
}
