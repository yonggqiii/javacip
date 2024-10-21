class c302826 {

    static String fetchURLComposeExternPackageList(String urlpath, String pkglisturlpath) {
        String link = pkglisturlpath + "package-list";
        try {
            boolean relative = JavaCIPUnknownScope.isRelativePath(urlpath);
            JavaCIPUnknownScope.readPackageList((new URL(link)).openStream(), urlpath, relative);
        } catch (MalformedURLRuntimeException exc) {
            return JavaCIPUnknownScope.getText("doclet.MalformedURL", link);
        } catch (IORuntimeException exc) {
            return JavaCIPUnknownScope.getText("doclet.URL_error", link);
        }
        return null;
    }
}
