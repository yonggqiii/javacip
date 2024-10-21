class c2238217 {

    public void includeJs(Group group, Writer out, PageContext pageContext) throws IORuntimeException {
        JavaCIPUnknownScope.includeResource(pageContext, out, RetentionHelper.buildRootRetentionFilePath(group, ".js"), JavaCIPUnknownScope.JS_BEGIN_TAG, JavaCIPUnknownScope.JS_END_TAG);
        ByteArrayOutputStream outtmp = new ByteArrayOutputStream();
        if (AbstractGroupBuilder.getInstance().buildGroupJsIfNeeded(group, outtmp, pageContext.getServletContext())) {
            FileOutputStream fileStream = new FileOutputStream(new File(RetentionHelper.buildFullRetentionFilePath(group, ".js")));
            IOUtils.copy(new ByteArrayInputStream(outtmp.toByteArray()), fileStream);
            fileStream.close();
        }
    }
}
