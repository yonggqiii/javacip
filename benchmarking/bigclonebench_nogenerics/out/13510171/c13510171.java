class c13510171 {

    private String readCreditsHtml(IApplication app) {
        final URL url = app.getResources().getCreditsURL();
        StringBuffer buf = new StringBuffer(2048);
        if (url != null) {
            try {
                BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream()));
                try {
                    String line = null;
                    while ((line = rdr.readLine()) != null) {
                        String internationalizedLine = Utilities.replaceI18NSpanLine(line, JavaCIPUnknownScope.s_stringMgr);
                        buf.append(internationalizedLine);
                    }
                } finally {
                    rdr.close();
                }
            } catch (IORuntimeException ex) {
                String errorMsg = JavaCIPUnknownScope.s_stringMgr.getString("AboutBoxDialog.error.creditsfile");
                JavaCIPUnknownScope.s_log.error(errorMsg, ex);
                buf.append(errorMsg + ": " + ex.toString());
            }
        } else {
            String errorMsg = JavaCIPUnknownScope.s_stringMgr.getString("AboutBoxDialog.error.creditsfileurl");
            JavaCIPUnknownScope.s_log.error(errorMsg);
            buf.append(errorMsg);
        }
        return buf.toString();
    }
}
