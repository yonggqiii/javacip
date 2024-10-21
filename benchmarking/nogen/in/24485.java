


class c24485 {

    static String fetchURLComposeExternPackageList(String urlpath, String pkglisturlpath) {
        String link = pkglisturlpath + "package-list";
        try {
            boolean relative = isRelativePath(urlpath);
            readPackageList((new URL(link)).openStream(), urlpath, relative);
        } catch (MalformedURLRuntimeException exc) {
            return getText("doclet.MalformedURL", link);
        } catch (IORuntimeException exc) {
            return getText("doclet.URL_error", link);
        }
        return null;
    }

}
