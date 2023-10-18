class c3296963 {

    public String fetchDataDailyByStockId(String StockId, String market) throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.urlDailyStockPrice.replace("{0}", StockId + "." + market));
        URLConnection con = url.openConnection();
        con.setConnectTimeout(20000);
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        OutputStream os = new FileOutputStream(JavaCIPUnknownScope.dailyStockPriceList, true);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        os.flush();
        os.close();
        is.close();
        con = null;
        url = null;
        return null;
    }
}
