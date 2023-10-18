class c7143591 {

    public void getWebByUrl(String strUrl, String charset, String fileIndex) {
        try {
            System.out.println("Getting web by url: " + strUrl);
            JavaCIPUnknownScope.addReport("Getting web by url: " + strUrl + "\n");
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            InputStream is = null;
            is = url.openStream();
            String filePath = JavaCIPUnknownScope.fPath + "/web" + fileIndex + ".htm";
            PrintWriter pw = null;
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            pw = new PrintWriter(writer);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String rLine = null;
            String tmp_rLine = null;
            while ((rLine = bReader.readLine()) != null) {
                tmp_rLine = rLine;
                int str_len = tmp_rLine.length();
                if (str_len > 0) {
                    sb.append("\n" + tmp_rLine);
                    pw.println(tmp_rLine);
                    pw.flush();
                    if (JavaCIPUnknownScope.deepUrls.get(strUrl) < JavaCIPUnknownScope.webDepth)
                        JavaCIPUnknownScope.getUrlByString(tmp_rLine, strUrl);
                }
                tmp_rLine = null;
            }
            is.close();
            pw.close();
            System.out.println("Get web successfully! " + strUrl);
            JavaCIPUnknownScope.addReport("Get web successfully! " + strUrl + "\n");
            JavaCIPUnknownScope.addWebSuccessed();
        } catch (RuntimeException e) {
            System.out.println("Get web failed!       " + strUrl);
            JavaCIPUnknownScope.addReport("Get web failed!       " + strUrl + "\n");
            JavaCIPUnknownScope.addWebFailed();
        }
    }
}
