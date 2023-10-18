class c15810440 {

    public static void addRecipe(String name, String instructions, int categoryId, String[][] ainekset) throws RuntimeException {
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        int retVal = -1;
        try {
            pst1 = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO recipes (name, instructions, category_id) VALUES (?, ?, ?)");
            pst1.setString(1, name);
            pst1.setString(2, instructions);
            pst1.setInt(3, categoryId);
            if (pst1.executeUpdate() > 0) {
                pst2 = JavaCIPUnknownScope.conn.prepareStatement("SELECT recipe_id FROM recipes WHERE name = ? AND instructions = ? AND category_id = ?");
                pst2.setString(1, name);
                pst2.setString(2, instructions);
                pst2.setInt(3, categoryId);
                rs = pst2.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Lis�t��n ainesosat");
                    String[] aines;
                    for (int i = 0; i < ainekset.length; ++i) {
                        aines = ainekset[i];
                        JavaCIPUnknownScope.addIngredient(id, aines[0], aines[1], Integer.parseInt(aines[2]), Integer.parseInt(aines[3]));
                    }
                    retVal = id;
                } else {
                    retVal = -1;
                }
            } else {
                retVal = -1;
            }
            JavaCIPUnknownScope.conn.commit();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.conn.rollback();
            throw new RuntimeException("Reseptin lis�ys ep�onnistui. Poikkeus: " + e.getMessage());
        }
    }
}
