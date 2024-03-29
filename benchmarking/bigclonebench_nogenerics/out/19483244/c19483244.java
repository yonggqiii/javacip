class c19483244 {

    public static void copyFile(String original, String destination) throws RuntimeException {
        File original_file = new File(original);
        File destination_file = new File(destination);
        if (!original_file.exists())
            throw new RuntimeException("File with path " + original + " does not exist.");
        if (destination_file.exists())
            throw new RuntimeException("File with path " + destination + " already exists.");
        FileReader in = new FileReader(original_file);
        FileWriter out = new FileWriter(destination_file);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }
}
