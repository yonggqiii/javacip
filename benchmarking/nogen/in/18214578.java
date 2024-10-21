


class c18214578 {

    public void openJadFile(URL url) {
        try {
            setStatusBar("Loading...");
            jad.clear();
            jad.load(url.openStream());
            loadFromJad(url);
        } catch (FileNotFoundRuntimeException ex) {
            System.err.println("Cannot found " + url.getPath());
        } catch (NullPointerRuntimeException ex) {
            ex.printStackTrace();
            System.err.println("Cannot open jad " + url.getPath());
        } catch (IllegalArgumentRuntimeException ex) {
            ex.printStackTrace();
            System.err.println("Cannot open jad " + url.getPath());
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
            System.err.println("Cannot open jad " + url.getPath());
        }
    }

}
