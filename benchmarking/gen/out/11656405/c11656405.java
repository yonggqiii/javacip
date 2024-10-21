class c11656405 {

    public static String addWeibo(String weibo, File pic, String uid) throws Throwable {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("_surl", ""));
        qparams.add(new BasicNameValuePair("_t", "0"));
        qparams.add(new BasicNameValuePair("location", "home"));
        qparams.add(new BasicNameValuePair("module", "stissue"));
        if (pic != null) {
            String picId = JavaCIPUnknownScope.upLoadImg(pic, uid);
            qparams.add(new BasicNameValuePair("pic_id", picId));
        }
        qparams.add(new BasicNameValuePair("rank", "weibo"));
        qparams.add(new BasicNameValuePair("text", weibo));
        HttpPost post = JavaCIPUnknownScope.getHttpPost("http://weibo.com/aj/mblog/add?__rnd=1333611402611", uid);
        UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, JavaCIPUnknownScope.HTTP.UTF_8);
        post.setEntity(params);
        HttpResponse response = JavaCIPUnknownScope.client.execute(post);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, JavaCIPUnknownScope.HTTP.UTF_8);
        post.abort();
        return content;
    }
}
