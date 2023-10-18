class c14773782 {

    public void testJPEGRaster() throws MalformedURLRuntimeException, IORuntimeException {
        System.out.println("JPEGCodec RasterImage:");
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < JavaCIPUnknownScope.images.length; i++) {
            String url = Constants.getDefaultURIMediaConnectorBasePath() + "albums/hund/" + JavaCIPUnknownScope.images[i];
            InputStream istream = (new URL(url)).openStream();
            JPEGImageDecoder dec = JPEGCodec.createJPEGDecoder(istream);
            Raster raster = dec.decodeAsRaster();
            int width = raster.getWidth();
            int height = raster.getHeight();
            istream.close();
            System.out.println("w: " + width + " - h: " + height);
        }
        long stop = Calendar.getInstance().getTimeInMillis();
        System.out.println("zeit: " + (stop - start));
    }
}
