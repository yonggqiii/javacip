class c17261985 {

    public int doEndTag() throws JspRuntimeException {
        HttpSession session = JavaCIPUnknownScope.pageContext.getSession();
        try {
            IntactUserI user = (IntactUserI) session.getAttribute(Constants.USER_KEY);
            String urlStr = user.getSourceURL();
            if (urlStr == null) {
                return JavaCIPUnknownScope.EVAL_PAGE;
            }
            URL url = null;
            try {
                url = new URL(urlStr);
            } catch (MalformedURLRuntimeException me) {
                String decodedUrl = URLDecoder.decode(urlStr, "UTF-8");
                JavaCIPUnknownScope.pageContext.getOut().write("The source is malformed : <a href=\"" + decodedUrl + "\" target=\"_blank\">" + decodedUrl + "</a>");
                return JavaCIPUnknownScope.EVAL_PAGE;
            }
            StringBuffer httpContent = new StringBuffer();
            httpContent.append("<!-- URL : " + urlStr + "-->");
            String tmpLine;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((tmpLine = reader.readLine()) != null) {
                    httpContent.append(tmpLine);
                }
                reader.close();
            } catch (IORuntimeException ioe) {
                user.resetSourceURL();
                String decodedUrl = URLDecoder.decode(urlStr, "UTF-8");
                JavaCIPUnknownScope.pageContext.getOut().write("Unable to display the source at : <a href=\"" + decodedUrl + "\" target=\"_blank\">" + decodedUrl + "</a>");
                return JavaCIPUnknownScope.EVAL_PAGE;
            }
            JavaCIPUnknownScope.pageContext.getOut().write(httpContent.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new JspRuntimeException("Error when trying to get HTTP content");
        }
        return JavaCIPUnknownScope.EVAL_PAGE;
    }
}
