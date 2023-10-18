class c3672078 {

    public byte[] loadResource(String name) throws IORuntimeException {
        ClassPathResource cpr = new ClassPathResource(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(cpr.getInputStream(), baos);
        return baos.toByteArray();
    }
}
