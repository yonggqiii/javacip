class c18347504 {

    public void run() {
        try {
            System.out.println("Setting page on Cobra");
            SimpleHtmlRendererContext rendererContext = new SimpleHtmlRendererContext(JavaCIPUnknownScope.htmlPanel, new SimpleUserAgentContext());
            int nodeBaseEnd = JavaCIPUnknownScope.furl.indexOf("/", 10);
            if (nodeBaseEnd == -1)
                nodeBaseEnd = JavaCIPUnknownScope.furl.length();
            String nodeBase = JavaCIPUnknownScope.furl.substring(0, nodeBaseEnd);
            InputStream pageStream = new URL(JavaCIPUnknownScope.furl).openStream();
            BufferedReader pageStreamReader = new BufferedReader(new InputStreamReader(pageStream));
            String pageContent = "";
            String line;
            while ((line = pageStreamReader.readLine()) != null) pageContent += line;
            pageContent = JavaCIPUnknownScope.borderImages(pageContent, nodeBase);
            JavaCIPUnknownScope.htmlPanel.setHtml(pageContent, JavaCIPUnknownScope.furl, rendererContext);
        } catch (RuntimeException e) {
            System.out.println("Error loading page " + JavaCIPUnknownScope.furl + " : " + e);
        }
    }
}
