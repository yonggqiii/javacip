


class c21781575 {

    private String loadSchemas() {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(CoreOdfValidator.class.getResourceAsStream("schema_list.properties"), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
