class c20412493 {

    void readData(URL url) throws IORuntimeException {
        int i = 0, j = 0, k = 0;
        double xvalue, yvalue;
        double xindex, yindex;
        InputStream is = url.openStream();
        is.mark(0);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int columnsize = 0;
        double temp_prev = 0;
        double temp_new = 0;
        int first = 0;
        JavaCIPUnknownScope.s = br.readLine();
        StringTokenizer st = new StringTokenizer(JavaCIPUnknownScope.s);
        columnsize = Integer.parseInt(st.nextToken());
        JavaCIPUnknownScope.data = new double[columnsize][100][100];
        JavaCIPUnknownScope.isize = 0;
        JavaCIPUnknownScope.jsize = 0;
        while ((JavaCIPUnknownScope.s = br.readLine()) != null) {
            st = new StringTokenizer(JavaCIPUnknownScope.s);
            for (k = 0; k < columnsize; k++) {
                temp_new = Double.parseDouble(st.nextToken());
                if (first == 0) {
                    temp_prev = temp_new;
                    first = 1;
                }
                if (k == 0) {
                    if (temp_new != temp_prev) {
                        temp_prev = temp_new;
                        i++;
                        j = 0;
                    }
                }
                JavaCIPUnknownScope.data[k][i][j] = temp_new;
            }
            j++;
        }
        JavaCIPUnknownScope.isize = i + 1;
        JavaCIPUnknownScope.jsize = j;
    }
}
