


class c10878062 {

    public static void copy(File source, File dest) throws RuntimeException {
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(dest);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }

}
