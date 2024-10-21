class c18519246 {

    public List<PathObject> fetchPath(BoardObject board) throws NetworkRuntimeException {
        if (JavaCIPUnknownScope.boardPathMap.containsKey(board.getId())) {
            return JavaCIPUnknownScope.boardPathMap.get(board.getId()).getChildren();
        }
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(HttpConfig.bbsURL() + HttpConfig.BBS_0AN_BOARD + board.getId());
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            Document doc = XmlOperator.readDocument(entity.getContent());
            PathObject parent = new PathObject();
            BBSBodyParseHelper.parsePathList(doc, parent);
            parent = JavaCIPUnknownScope.searchAndCreatePathFromRoot(parent);
            JavaCIPUnknownScope.boardPathMap.put(board.getId(), parent);
            return parent.getChildren();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }
}
