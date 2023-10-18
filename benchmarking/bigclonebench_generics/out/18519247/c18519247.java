class c18519247 {

    public List<PathObject> fetchPath(PathObject parent) throws NetworkRuntimeException {
        if (parent.isFetched()) {
            return parent.getChildren();
        } else if (!"d".equals(parent.getType())) {
            return null;
        }
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(HttpConfig.bbsURL() + HttpConfig.BBS_0AN + parent.getPath());
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            Document doc = XmlOperator.readDocument(entity.getContent());
            BBSBodyParseHelper.parsePathList(doc, parent);
            return parent.getChildren();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }
}
