class c786782 {

    public void _getPlotTypes() {
        boolean gotPlots = false;
        while (!gotPlots) {
            try {
                JavaCIPUnknownScope._myPlotTypes = new Vector[2];
                JavaCIPUnknownScope._myPlotTypes[0] = new Vector();
                JavaCIPUnknownScope._myPlotTypes[1] = new Vector();
                URL dataurl = new URL(JavaCIPUnknownScope.getDocumentBase(), JavaCIPUnknownScope.plotTypeFile);
                BufferedReader readme = new BufferedReader(new InputStreamReader(new GZIPInputStream(dataurl.openStream())));
                while (true) {
                    String S = readme.readLine();
                    if (S == null)
                        break;
                    StringTokenizer st = new StringTokenizer(S);
                    JavaCIPUnknownScope._myPlotTypes[0].addElement(st.nextToken());
                    if (st.hasMoreTokens()) {
                        JavaCIPUnknownScope._myPlotTypes[1].addElement(st.nextToken());
                    } else {
                        JavaCIPUnknownScope._myPlotTypes[1].addElement((String) JavaCIPUnknownScope._myPlotTypes[0].lastElement());
                    }
                }
                gotPlots = true;
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope._myPlotTypes[0].removeAllElements();
                JavaCIPUnknownScope._myPlotTypes[1].removeAllElements();
                gotPlots = false;
            }
        }
    }
}
