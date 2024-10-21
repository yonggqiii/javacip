class c16214714 {

    private Element makeRequest(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream in = conn.getInputStream();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(in);
            Element element = document.getDocumentElement();
            element.normalize();
            if (JavaCIPUnknownScope.checkRootTag(element)) {
                return element;
            } else {
                return null;
            }
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationRuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (SAXRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
