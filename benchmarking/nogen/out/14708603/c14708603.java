class c14708603 {

    private void gerarComissao() {
        int opt = Funcoes.mensagemConfirma(null, "Confirma gerar comiss�es para o vendedor " + JavaCIPUnknownScope.txtNomeVend.getVlrString().trim() + "?");
        if (opt == JOptionPane.OK_OPTION) {
            StringBuilder insert = new StringBuilder();
            insert.append("INSERT INTO RPCOMISSAO ");
            insert.append("(CODEMP, CODFILIAL, CODPED, CODITPED, ");
            insert.append("CODEMPVD, CODFILIALVD, CODVEND, VLRCOMISS ) ");
            insert.append("VALUES ");
            insert.append("(?,?,?,?,?,?,?,?)");
            PreparedStatement ps;
            int parameterIndex;
            boolean gerou = false;
            try {
                for (int i = 0; i < JavaCIPUnknownScope.tab.getNumLinhas(); i++) {
                    if (((BigDecimal) JavaCIPUnknownScope.tab.getValor(i, 8)).floatValue() > 0) {
                        parameterIndex = 1;
                        ps = JavaCIPUnknownScope.con.prepareStatement(insert.toString());
                        ps.setInt(parameterIndex++, AplicativoRep.iCodEmp);
                        ps.setInt(parameterIndex++, ListaCampos.getMasterFilial("RPCOMISSAO"));
                        ps.setInt(parameterIndex++, JavaCIPUnknownScope.txtCodPed.getVlrInteger());
                        ps.setInt(parameterIndex++, (Integer) JavaCIPUnknownScope.tab.getValor(i, ETabNota.ITEM.ordinal()));
                        ps.setInt(parameterIndex++, AplicativoRep.iCodEmp);
                        ps.setInt(parameterIndex++, ListaCampos.getMasterFilial("RPVENDEDOR"));
                        ps.setInt(parameterIndex++, JavaCIPUnknownScope.txtCodVend.getVlrInteger());
                        ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.VLRCOMIS.ordinal()));
                        ps.executeUpdate();
                        gerou = true;
                    }
                }
                if (gerou) {
                    Funcoes.mensagemInforma(null, "Comiss�o gerada para " + JavaCIPUnknownScope.txtNomeVend.getVlrString().trim());
                    JavaCIPUnknownScope.txtCodPed.setText("0");
                    JavaCIPUnknownScope.lcPedido.carregaDados();
                    JavaCIPUnknownScope.carregaTabela();
                    JavaCIPUnknownScope.con.commit();
                } else {
                    Funcoes.mensagemInforma(null, "N�o foi possiv�l gerar comiss�o!\nVerifique os valores das comiss�es dos itens.");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                Funcoes.mensagemErro(this, "Erro ao gerar comiss�o!\n" + e.getMessage());
                try {
                    JavaCIPUnknownScope.con.rollback();
                } catch (SQLRuntimeException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
