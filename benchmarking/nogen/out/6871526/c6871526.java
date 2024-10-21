class c6871526 {

    private boolean cacheUrlFile(String filePath, String realUrl, boolean isOnline) {
        try {
            URL url = new URL(realUrl);
            String encoding = "gbk";
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            StringBuilder sb = new StringBuilder();
            sb.append(JavaCIPUnknownScope.configCenter.getWebRoot()).append(JavaCIPUnknownScope.getCacheString(isOnline)).append(filePath);
            JavaCIPUnknownScope.fileEditor.createDirectory(sb.toString());
            return JavaCIPUnknownScope.fileEditor.saveFile(sb.toString(), in);
        } catch (IORuntimeException e) {
        }
        return false;
    }
}
