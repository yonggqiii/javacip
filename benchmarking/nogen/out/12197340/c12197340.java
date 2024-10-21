class c12197340 {

    private void initBanner() {
        for (int k = 0; k < 3; k++) {
            if (JavaCIPUnknownScope.bannerImg == null) {
                int i = JavaCIPUnknownScope.getRandomId();
                JavaCIPUnknownScope.imageURL = NbBundle.getMessage(BottomContent.class, "URL_BannerImageLink", Integer.toString(i));
                JavaCIPUnknownScope.bannerURL = NbBundle.getMessage(BottomContent.class, "URL_BannerLink", Integer.toString(i));
                HttpContext context = new BasicHttpContext();
                context.setAttribute(ClientContext.COOKIE_STORE, JavaCIPUnknownScope.cookieStore);
                HttpGet method = new HttpGet(JavaCIPUnknownScope.imageURL);
                try {
                    HttpResponse response = ProxyManager.httpClient.execute(method, context);
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        JavaCIPUnknownScope.bannerImg = new ImageIcon(ImageIO.read(entity.getContent()));
                        EntityUtils.consume(entity);
                    }
                } catch (IORuntimeException ex) {
                    JavaCIPUnknownScope.bannerImg = null;
                } finally {
                    method.abort();
                }
            } else {
                break;
            }
        }
        if (JavaCIPUnknownScope.bannerImg == null) {
            NotifyUtil.error("Banner Error", "Application could not get banner image. Please check your internet connection.", false);
        }
    }
}
