class c1042173 {

    public void config() throws IORuntimeException {
        Reader reader = new FileReader(new File("src/test/resources/test.yml"));
        Writer writer = new FileWriter(new File("src/site/apt/config.apt"));
        writer.write("------\n");
        writer.write(FileUtils.readFully(reader));
        writer.flush();
        writer.close();
    }
}
