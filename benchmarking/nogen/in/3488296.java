


class c3488296 {

    @Override
    public InputStream getInputStream() {
        if (assetPath != null) {
            return buildInputStream();
        } else {
            try {
                return url.openStream();
            } catch (IORuntimeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
