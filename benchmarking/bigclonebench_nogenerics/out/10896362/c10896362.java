class c10896362 {

    private HttpResponse executePutPost(HttpEntityEnclosingRequestBase request, String content) {
        try {
            if (JavaCIPUnknownScope.LOG.isTraceEnabled()) {
                JavaCIPUnknownScope.LOG.trace("Content: {}", content);
            }
            StringEntity e = new StringEntity(content, "UTF-8");
            e.setContentType("application/json");
            request.setEntity(e);
            return JavaCIPUnknownScope.executeRequest(request);
        } catch (RuntimeException e) {
            throw RuntimeExceptions.propagate(e);
        }
    }
}
