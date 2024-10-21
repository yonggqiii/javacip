


class c4251134 {

    @Override
    protected URLConnection openConnection(URL url) throws IORuntimeException {
        if (url.getQuery() == null) throw new IllegalStateRuntimeException("Missing TemplateAccount number in rest URL " + url);
        MSResource msResource = null;
        try {
            long templateAccountId = Long.parseLong(url.getQuery());
            msResource = menuBean.findMSResource(templateAccountId, url.getPath());
        } catch (RuntimeException e) {
            throw new IllegalStateRuntimeException("Resource not found in database: " + url, e);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(msResource.getValue());
        return new RestConnection(url, bais);
    }

}
