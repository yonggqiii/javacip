class c19963744 {

    public void atualizarLivro(LivroBean livro) {
        PreparedStatement pstmt = null;
        String sql = "update livro " + "set " + "isbn = ?, " + "autor = ?, " + "editora = ?, " + "edicao = ?, " + "titulo = ? " + "where " + "isbn = ?";
        try {
            pstmt = JavaCIPUnknownScope.connection.prepareStatement(sql);
            pstmt.setString(1, livro.getISBN());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getEditora());
            pstmt.setString(4, livro.getEdicao());
            pstmt.setString(5, livro.getTitulo());
            pstmt.executeUpdate();
            JavaCIPUnknownScope.connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                JavaCIPUnknownScope.connection.rollback();
            } catch (SQLRuntimeException ex1) {
                throw new RuntimeRuntimeException("Erro ao tentar atualizar livro.", ex1);
            }
            throw new RuntimeRuntimeException("Erro ao tentar atualizar livro.", ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (JavaCIPUnknownScope.connection != null) {
                    JavaCIPUnknownScope.connection.close();
                }
            } catch (SQLRuntimeException ex) {
                throw new RuntimeRuntimeException("Erro ao tentar atualizar livro.", ex);
            }
        }
    }
}
