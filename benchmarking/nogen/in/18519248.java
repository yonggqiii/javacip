


class c18519248 {

    public String fetchContent(PathObject file) throws NetworkRuntimeException {
        if (file.isFetched()) {
            return file.getContent();
        }
        if (!"f".equals(file.getType())) {
            return null;
        }
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(HttpConfig.bbsURL() + HttpConfig.BBS_ANC + file.getPath());
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            Document doc = XmlOperator.readDocument(entity.getContent());
            return BBSBodyParseHelper.parsePathContent(doc, file);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }

}
