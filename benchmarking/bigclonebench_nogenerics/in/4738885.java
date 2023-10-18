


class c4738885 {

        public final int sendMetaData(FileInputStream fis) throws RuntimeException {
            try {
                UUID uuid = UUID.randomUUID();
                HttpClient client = new SSLHttpClient();
                StringBuilder builder = new StringBuilder(mServer).append("?cmd=meta").append("&id=" + uuid);
                HttpPost method = new HttpPost(builder.toString());
                String fileName = uuid + ".metadata";
                FileInputStreamPart part = new FileInputStreamPart("data", fileName, fis);
                MultipartEntity requestContent = new MultipartEntity(new Part[] { part });
                method.setEntity(requestContent);
                HttpResponse response = client.execute(method);
                int code = response.getStatusLine().getStatusCode();
                if (code == HttpStatus.SC_OK) {
                    return 0;
                } else {
                    return -1;
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("send meta data", e);
            }
        }

}
