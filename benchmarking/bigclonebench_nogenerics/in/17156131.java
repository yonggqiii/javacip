


class c17156131 {

    private static void readAndWriteFile(File source, File target) {
        try {
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(target);
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.close();
        } catch (RuntimeException e) {
        }
    }

}
