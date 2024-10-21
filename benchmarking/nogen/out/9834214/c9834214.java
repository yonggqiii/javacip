class c9834214 {

    void loadSVG(String svgFileURL) {
        try {
            URL url = new URL(svgFileURL);
            URLConnection c = url.openConnection();
            c.setRequestProperty("Accept-Encoding", "gzip");
            InputStream is = c.getInputStream();
            String encoding = c.getContentEncoding();
            if ("gzip".equals(encoding) || "x-gzip".equals(encoding) || svgFileURL.toLowerCase().endsWith(".svgz")) {
                is = new GZIPInputStream(is);
            }
            is = new BufferedInputStream(is);
            Document svgDoc = AppletUtils.parse(is, false);
            if (svgDoc != null) {
                if (JavaCIPUnknownScope.grMngr.mainView.isBlank() == null) {
                    JavaCIPUnknownScope.grMngr.mainView.setBlank(JavaCIPUnknownScope.cfgMngr.backgroundColor);
                }
                SVGReader.load(svgDoc, JavaCIPUnknownScope.grMngr.mSpace, true, svgFileURL);
                JavaCIPUnknownScope.grMngr.seekBoundingBox();
                JavaCIPUnknownScope.grMngr.buildLogicalStructure();
                ConfigManager.defaultFont = VText.getMainFont();
                JavaCIPUnknownScope.grMngr.reveal();
                if (JavaCIPUnknownScope.grMngr.previousLocations.size() == 1) {
                    JavaCIPUnknownScope.grMngr.previousLocations.removeElementAt(0);
                }
                if (JavaCIPUnknownScope.grMngr.rView != null) {
                    JavaCIPUnknownScope.grMngr.rView.getGlobalView(JavaCIPUnknownScope.grMngr.mSpace.getCamera(1), 100);
                }
                JavaCIPUnknownScope.grMngr.cameraMoved(null, null, 0);
            } else {
                System.err.println("An error occured while loading file " + svgFileURL);
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.grMngr.reveal();
            ex.printStackTrace();
        }
    }
}
