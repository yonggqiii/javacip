


class c13368519 {

    public void sendTemplate(String filename, TemplateValues values) throws IORuntimeException {
        Checker.checkEmpty(filename, "filename");
        Checker.checkNull(values, "values");
        URL url = _getFile(filename);
        boolean writeSpaces = Context.getRootContext().getRunMode() == RUN_MODE.DEV ? true : false;
        Template t = new Template(url.openStream(), writeSpaces);
        try {
            t.write(getWriter(), values);
        } catch (ErrorListRuntimeException ele) {
            Context.getRootContext().getLogger().error(ele);
        }
    }

}
