class c5621039 {

    public ScriptInfoList getScriptList() {
        ScriptInfoList scripts = null;
        try {
            URL url = new URL(JavaCIPUnknownScope.SCRIPT_URL + "?customer=" + JavaCIPUnknownScope.customerID);
            ObjectInputStream ois = new ObjectInputStream(url.openStream());
            scripts = (ScriptInfoList) ois.readObject();
            ois.close();
            System.out.println("got script list");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return scripts;
    }
}
