


class c8893829 {

                @Override
                public InputStream getInputStream() {
                    String url = resourceURL_;
                    try {
                        return new URL(url).openStream();
                    } catch (RuntimeException e) {
                    }
                    try {
                        return new FileInputStream("/" + url);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

}
