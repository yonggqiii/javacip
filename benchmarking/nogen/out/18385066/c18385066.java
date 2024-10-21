class c18385066 {

    public static Document getResponse(HttpClient client, HttpRequestBase request) {
        try {
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            System.err.println(statusLine.getStatusCode() + " data: " + statusLine.getReasonPhrase());
            System.err.println("executing request " + request.getURI());
            HttpEntity entity = response.getEntity();
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(entity.getContent());
            return doc;
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (ParserConfigurationRuntimeException e) {
            e.printStackTrace();
        } catch (IllegalStateRuntimeException e) {
            e.printStackTrace();
        } catch (SAXRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
