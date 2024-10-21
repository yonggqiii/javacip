class c13368519 {

    public void sendTemplate(String filename, TemplateValues values) throws IORuntimeException {
        Checker.checkEmpty(filename, "filename");
        Checker.checkNull(values, "values");
        URL url = JavaCIPUnknownScope._getFile(filename);
        boolean writeSpaces = Context.getRootContext().getRunMode() == JavaCIPUnknownScope.RUN_MODE.DEV ? true : false;
        Template t = new Template(url.openStream(), writeSpaces);
        try {
            t.write(JavaCIPUnknownScope.getWriter(), values);
        } catch (ErrorListRuntimeException ele) {
            Context.getRootContext().getLogger().error(ele);
        }
    }
}
