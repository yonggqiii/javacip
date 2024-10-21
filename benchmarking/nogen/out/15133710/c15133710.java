class c15133710 {

    private Image retrievePdsImage(double lat, double lon) {
        JavaCIPUnknownScope.imageDone = false;
        try {
            StringBuffer urlBuff = new StringBuffer(JavaCIPUnknownScope.psdUrl + JavaCIPUnknownScope.psdCgi + "?");
            urlBuff.append("DATA_SET_NAME=" + JavaCIPUnknownScope.dataSet);
            urlBuff.append("&VERSION=" + JavaCIPUnknownScope.version);
            urlBuff.append("&PIXEL_TYPE=" + JavaCIPUnknownScope.pixelType);
            urlBuff.append("&PROJECTION=" + JavaCIPUnknownScope.projection);
            urlBuff.append("&STRETCH=" + JavaCIPUnknownScope.stretch);
            urlBuff.append("&GRIDLINE_FREQUENCY=" + JavaCIPUnknownScope.gridlineFrequency);
            urlBuff.append("&SCALE=" + URLEncoder.encode(JavaCIPUnknownScope.scale));
            urlBuff.append("&RESOLUTION=" + JavaCIPUnknownScope.resolution);
            urlBuff.append("&LATBOX=" + JavaCIPUnknownScope.latbox);
            urlBuff.append("&LONBOX=" + JavaCIPUnknownScope.lonbox);
            urlBuff.append("&BANDS_SELECTED=" + JavaCIPUnknownScope.bandsSelected);
            urlBuff.append("&LAT=" + lat);
            urlBuff.append("&LON=" + lon);
            URL url = new URL(urlBuff.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = null;
            String line;
            String imageSrc;
            int count = 0;
            while ((line = in.readLine()) != null) {
                if (count == 6)
                    result = line;
                count++;
            }
            int startIndex = result.indexOf("<TH COLSPAN=2 ROWSPAN=2><IMG SRC = \"") + 36;
            int endIndex = result.indexOf("\"", startIndex);
            imageSrc = result.substring(startIndex, endIndex);
            URL imageUrl = new URL(imageSrc);
            return (Toolkit.getDefaultToolkit().getImage(imageUrl));
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return null;
    }
}
