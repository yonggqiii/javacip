


class c11941957 {

    String openUrlAsString(String address, int maxLines) {
        StringBuffer sb;
        try {
            URL url = new URL(address);
            InputStream in = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            int count = 0;
            String line;
            while ((line = br.readLine()) != null && count++ < maxLines) sb.append(line + "\n");
            in.close();
        } catch (IORuntimeException e) {
            sb = null;
        }
        return sb != null ? new String(sb) : null;
    }

}
