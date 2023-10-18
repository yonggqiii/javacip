class c14966365 {

    public void invoke(String[] args) {
        System.err.println("invoked with args of size " + args.length);
        try {
            for (int i = 0; i < args.length; i++) {
                System.err.println("processing URL: " + args[i]);
                URL url = new URL(args[i]);
                AnnotatedLinearObjectParser parserObj = JavaCIPUnknownScope.findParserForURL(url);
                if (parserObj == null) {
                    continue;
                }
                InputStream data = url.openStream();
                CompMapViewerWrapper wrapper = ((CompMapViewerProvider) JavaCIPUnknownScope.sp).getWrapper();
                wrapper.parseIntoDataModel(data, new URLImpl(url.toString()), parserObj, false);
                JFrame f = wrapper.getViewer().getMainFrame();
                f.show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
