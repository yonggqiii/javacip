class c324681 {

    public boolean import_status(String filename) {
        int pieceId;
        int i, j, col, row;
        int rotation;
        int number;
        boolean byurl = false;
        e2piece temppiece;
        String lineread;
        StringTokenizer tok;
        BufferedReader entree;
        try {
            if (byurl == true) {
                URL url = new URL(JavaCIPUnknownScope.baseURL, filename);
                InputStream in = url.openStream();
                entree = new BufferedReader(new InputStreamReader(in));
            } else {
                entree = new BufferedReader(new FileReader(filename));
            }
            pieceId = 0;
            for (i = 0; i < JavaCIPUnknownScope.board.colnb; i++) {
                for (j = 0; j < JavaCIPUnknownScope.board.rownb; j++) {
                    JavaCIPUnknownScope.unplace_piece_at(i, j);
                }
            }
            while (true) {
                lineread = entree.readLine();
                if (lineread == null) {
                    break;
                }
                tok = new StringTokenizer(lineread, " ");
                pieceId = Integer.parseInt(tok.nextToken());
                col = Integer.parseInt(tok.nextToken()) - 1;
                row = Integer.parseInt(tok.nextToken()) - 1;
                rotation = Integer.parseInt(tok.nextToken());
                JavaCIPUnknownScope.place_piece_at(pieceId, col, row, 0);
                temppiece = JavaCIPUnknownScope.board.get_piece_at(col, row);
                temppiece.reset_rotation();
                temppiece.rotate(rotation);
            }
            return true;
        } catch (IORuntimeException err) {
            return false;
        }
    }
}
