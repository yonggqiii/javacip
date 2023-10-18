class c22222259 {

    void extractEnsemblCoords(String geneviewLink) {
        try {
            URL connectURL = new URL(geneviewLink);
            InputStream urlStream = connectURL.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("View gene in genomic location") != -1) {
                    line = line.substring(line.indexOf("contigview?"));
                    String chr, start, stop;
                    chr = line.substring(line.indexOf("chr=") + 4);
                    chr = chr.substring(0, chr.indexOf("&"));
                    start = line.substring(line.indexOf("vc_start=") + 9);
                    start = start.substring(0, start.indexOf("&"));
                    stop = line.substring(line.indexOf("vc_end=") + 7);
                    stop = stop.substring(0, stop.indexOf("\""));
                    String selString;
                    for (int s = 0; s < JavaCIPUnknownScope.selPanel.chrField.getModel().getSize(); s++) {
                        if (chr.equals(JavaCIPUnknownScope.selPanel.chrField.getModel().getElementAt(s))) {
                            JavaCIPUnknownScope.selPanel.chrField.setSelectedIndex(s);
                            break;
                        }
                    }
                    JavaCIPUnknownScope.selPanel.setStart(Integer.parseInt(start));
                    JavaCIPUnknownScope.selPanel.setStop(Integer.parseInt(stop));
                    JavaCIPUnknownScope.selPanel.refreshButton.doClick();
                    break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Problems retrieving Geneview from Ensembl");
            e.printStackTrace();
        }
    }
}
