class c14708602 {

    private void gerarFaturamento() {
        int opt = Funcoes.mensagemConfirma(null, "Confirma o faturamento?");
        if (opt == JOptionPane.OK_OPTION) {
            StringBuilder insert = new StringBuilder();
            insert.append("INSERT INTO RPFATURAMENTO ");
            insert.append("(CODEMP, CODFILIAL, CODPED, CODITPED, ");
            insert.append("QTDFATURADO, VLRFATURADO, QTDPENDENTE, ");
            insert.append("PERCCOMISFAT, VLRCOMISFAT, DTFATURADO ) ");
            insert.append("VALUES");
            insert.append("(?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement ps;
            int parameterIndex;
            try {
                for (int i = 0; i < JavaCIPUnknownScope.tab.getNumLinhas(); i++) {
                    parameterIndex = 1;
                    ps = JavaCIPUnknownScope.con.prepareStatement(insert.toString());
                    ps.setInt(parameterIndex++, AplicativoRep.iCodEmp);
                    ps.setInt(parameterIndex++, ListaCampos.getMasterFilial("RPFATURAMENTO"));
                    ps.setInt(parameterIndex++, JavaCIPUnknownScope.txtCodPed.getVlrInteger());
                    ps.setInt(parameterIndex++, (Integer) JavaCIPUnknownScope.tab.getValor(i, ETabNota.ITEM.ordinal()));
                    ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.QTDFATURADA.ordinal()));
                    ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.VLRFATURADO.ordinal()));
                    ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.QDTPENDENTE.ordinal()));
                    ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.PERCCOMIS.ordinal()));
                    ps.setBigDecimal(parameterIndex++, (BigDecimal) JavaCIPUnknownScope.tab.getValor(i, ETabNota.VLRCOMIS.ordinal()));
                    ps.setDate(parameterIndex++, Funcoes.dateToSQLDate(Calendar.getInstance().getTime()));
                    ps.executeUpdate();
                }
                JavaCIPUnknownScope.gerarFaturamento.setEnabled(false);
                JavaCIPUnknownScope.gerarComissao.setEnabled(true);
                Funcoes.mensagemInforma(null, "Faturamento criado para pedido " + JavaCIPUnknownScope.txtCodPed.getVlrInteger());
                JavaCIPUnknownScope.con.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                Funcoes.mensagemErro(this, "Erro ao gerar faturamento!\n" + e.getMessage());
                try {
                    JavaCIPUnknownScope.con.rollback();
                } catch (SQLRuntimeException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
