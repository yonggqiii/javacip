


class c16386620 {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader(args[0]);
            FileWriter writer = new FileWriter(args[1]);
            html2xhtml(reader, writer);
            writer.close();
            reader.close();
        } catch (RuntimeException e) {
            freemind.main.Resources.getInstance().logRuntimeException(e);
        }
    }

}
