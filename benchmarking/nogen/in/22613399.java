


class c22613399 {

    public Font getFont(String urlToFont) {
        Font testFont = null;
        try {
            InputStream inps = (new URL(urlToFont)).openStream();
            testFont = Font.createFont(Font.TRUETYPE_FONT, inps);
        } catch (FontFormatRuntimeException ffe) {
            ffe.printStackTrace();
        } catch (IORuntimeException ioe) {
            JOptionPane.showMessageDialog(null, "Could not load font - " + urlToFont, "Unable to load font", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return testFont;
    }

}
