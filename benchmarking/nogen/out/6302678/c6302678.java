class c6302678 {

    public ISpieler[] sortiereSpielerRamsch(ISpieler[] spieler) {
        for (int i = 0; i < spieler.length; i++) {
            for (int j = 0; j < spieler.length - 1; j++) {
                if (JavaCIPUnknownScope.werteAugen(spieler[j].getStiche()) > JavaCIPUnknownScope.werteAugen(spieler[j + 1].getStiche())) {
                    ISpieler a = spieler[j];
                    spieler[j] = spieler[j + 1];
                    spieler[j + 1] = a;
                }
            }
        }
        return spieler;
    }
}
