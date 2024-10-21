class c15810441 {

    private static void addIngredient(int recipeId, String name, String amount, int measureId, int shopFlag) throws RuntimeException {
        PreparedStatement pst = null;
        try {
            pst = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO ingredients (recipe_id, name, amount, measure_id, shop_flag) VALUES (?,?,?,?,?)");
            pst.setInt(1, recipeId);
            pst.setString(2, name);
            pst.setString(3, amount);
            pst.setInt(4, measureId);
            pst.setInt(5, shopFlag);
            pst.executeUpdate();
            JavaCIPUnknownScope.conn.commit();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.conn.rollback();
            throw new RuntimeException("Ainesosan lis�ys ep�onnistui. Poikkeus: " + e.getMessage());
        }
    }
}
