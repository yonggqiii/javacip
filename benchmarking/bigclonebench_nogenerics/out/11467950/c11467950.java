class c11467950 {

    public String getMediaURL(String strLink) {
        try {
            String res = JavaCIPUnknownScope.de.nomule.mediaproviders.KeepVid.getAnswer(strLink, "aa");
            if (NoMuleRuntime.DEBUG)
                System.out.println(res);
            String regexp = "http:\\/\\/[^\"]+\\/get_video[^\"]+";
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(res);
            m.find();
            String strRetUrl = res.substring(m.start(), m.end());
            strRetUrl = URLDecoder.decode(strRetUrl, "UTF-8");
            if (JavaCIPUnknownScope.TRY_HIGH_QUALITY) {
                NoMuleRuntime.showDebug("HIGH_QUALITY");
                strRetUrl += "&fmt=18";
                try {
                    URL url = new URL(strRetUrl);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    in.close();
                } catch (MalformedURLRuntimeException e) {
                    e.printStackTrace();
                } catch (IORuntimeException e) {
                    strRetUrl = strRetUrl.substring(0, strRetUrl.length() - 7);
                }
            }
            if (NoMuleRuntime.DEBUG)
                System.out.println(strRetUrl);
            return strRetUrl;
        } catch (UnsupportedEncodingRuntimeException e) {
            System.out.println("Error in Youtube Media Provider. Encoding is not supported. (How would that happen?!)");
            e.printStackTrace();
        }
        return "";
    }
}
