


class c5380644 {

    private static void getPatronInfo(HttpClient client) throws RuntimeException {
        HttpGet httpget = new HttpGet("http://libsys.arlingtonva.us/patroninfo~S1/1079675/items");
        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            System.out.println(EntityUtils.toString(entity));
        }
        EntityUtils.consume(entity);
    }

}
