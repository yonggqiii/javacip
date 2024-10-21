class c19016407 {

    public int print(String type, String url, String attrs) throws PrinterRuntimeException {
        try {
            return print(type, (new URL(url)).openStream(), attrs);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PrinterRuntimeException(e);
        }
    }
}
