class c1637154 {

    public JsonValue get(Url url) {
        try {
            URLConnection connection = new URL(url + "").openConnection();
            return JavaCIPUnknownScope.createItemFromResponse(url, connection);
        } catch (IORuntimeException e) {
            throw ItemscriptError.internalError(this, "get.IORuntimeException", e);
        }
    }
}
