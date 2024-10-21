


class c3126811 {

    public static void saveProperties(Properties props, String comment, URL url) throws IORuntimeException {
        if (props == null) throw new IllegalArgumentRuntimeException();
        if (url == null) throw new IllegalArgumentRuntimeException();
        OutputStream out = url.openConnection().getOutputStream();
        props.store(out, comment);
        out.close();
    }

}
