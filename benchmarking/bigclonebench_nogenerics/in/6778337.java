


class c6778337 {

    private void copy(File from, File to) throws FileNotFoundRuntimeException, IORuntimeException {
        FileReader in;
        in = new FileReader(from);
        FileWriter out = new FileWriter(to);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
    }

}
