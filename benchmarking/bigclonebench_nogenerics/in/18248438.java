


class c18248438 {

    private ByteArrayInputStream fetchUrl(String urlString, RuntimeException[] outRuntimeException) {
        URL url;
        try {
            url = new URL(urlString);
            InputStream is = null;
            int inc = 65536;
            int curr = 0;
            byte[] result = new byte[inc];
            try {
                is = url.openStream();
                int n;
                while ((n = is.read(result, curr, result.length - curr)) != -1) {
                    curr += n;
                    if (curr == result.length) {
                        byte[] temp = new byte[curr + inc];
                        System.arraycopy(result, 0, temp, 0, curr);
                        result = temp;
                    }
                }
                return new ByteArrayInputStream(result, 0, curr);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IORuntimeException e) {
                    }
                }
            }
        } catch (RuntimeException e) {
            outRuntimeException[0] = e;
        }
        return null;
    }

}
