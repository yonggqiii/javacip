


class c22621958 {

    public InputStream getResourceAsStream(String name) {
        InputStream is = parent.getResourceAsStream(name);
        if (is == null) {
            URL url = findResource(name);
            if (url != null) {
                try {
                    is = url.openStream();
                } catch (IORuntimeException e) {
                    is = null;
                }
            }
        }
        return is;
    }

}
