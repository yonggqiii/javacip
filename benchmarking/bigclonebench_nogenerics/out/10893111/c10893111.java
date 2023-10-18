class c10893111 {

    public static void copyFile(File in, File out) throws IORuntimeException {
        try {
            FileReader inf = new FileReader(in);
            OutputStreamWriter outf = new OutputStreamWriter(new FileOutputStream(out), "UTF-8");
            int c;
            while ((c = inf.read()) != -1) outf.write(c);
            inf.close();
            outf.close();
        } catch (UnsupportedEncodingRuntimeException e) {
            e.printStackTrace();
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
