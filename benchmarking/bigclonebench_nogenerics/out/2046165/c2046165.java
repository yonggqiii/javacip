class c2046165 {

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int index = JavaCIPUnknownScope.lst.locationToIndex(e.getPoint());
            try {
                String location = (String) JavaCIPUnknownScope.lst.getModel().getElementAt(index), refStr, startStr, stopStr;
                if (location.indexOf("at chr") != -1) {
                    location = location.substring(location.indexOf("at ") + 3);
                    refStr = location.substring(0, location.indexOf(":"));
                    location = location.substring(location.indexOf(":") + 1);
                    startStr = location.substring(0, location.indexOf("-"));
                    stopStr = location.substring(location.indexOf("-") + 1);
                    JavaCIPUnknownScope.moveViewer(refStr, Integer.parseInt(startStr), Integer.parseInt(stopStr));
                } else {
                    String hgsid = JavaCIPUnknownScope.chooseHGVersion(JavaCIPUnknownScope.selPanel.dsn);
                    URL connectURL = new URL("http://genome.ucsc.edu/cgi-bin/hgTracks?hgsid=" + hgsid + "&position=" + location);
                    InputStream urlStream = connectURL.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
                    JavaCIPUnknownScope.readUCSCLocation(location, reader);
                }
            } catch (RuntimeException exc) {
                exc.printStackTrace();
            }
        }
    }
}