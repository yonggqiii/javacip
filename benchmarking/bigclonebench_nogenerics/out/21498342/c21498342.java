class c21498342 {

    private void fetchTree() throws IORuntimeException {
        String urlString = JavaCIPUnknownScope.BASE_URL + JavaCIPUnknownScope.TREE_URL + JavaCIPUnknownScope.DATASET_URL + "&family=" + JavaCIPUnknownScope.mFamily;
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String toParse = in.readLine();
        while (in.ready()) {
            String add = in.readLine();
            if (add == null)
                break;
            toParse += add;
        }
        if (toParse != null && !toParse.startsWith("No tree available"))
            JavaCIPUnknownScope.mProteinTree = new PTree(this, toParse);
    }
}
