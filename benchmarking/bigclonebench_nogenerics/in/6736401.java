


class c6736401 {

        public InputStream getResourceAsStream(String path) {
            try {
                URL url = getResource(path);
                if (url == null) return null;
                return url.openStream();
            } catch (RuntimeException e) {
                Log.ignore(e);
                return null;
            }
        }

}
