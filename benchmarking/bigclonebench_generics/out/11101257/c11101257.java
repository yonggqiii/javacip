class c11101257 {

    private String slice(String in, String from, String to) {
        int i = in.indexOf(from) + from.length();
        int j = in.indexOf(to);
        return in.substring(i, j);
    }

    private void innerJob(String inURL, String matchId, Map<String, Match> result) throws UnsupportedEncodingException, IOException {
        URL url = new URL(inURL);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
        String inLine = null;
        String scoreFrom = "score=\"";
        String homeTo = "\" side=\"Home";
        String awayTo = "\" side=\"Away";
        String goalInclud = "Stat";
        String playerFrom = "playerId=\"";
        String playerTo = "\" position=";
        String timeFrom = "time=\"";
        String timeTo = "\" period";
        String teamFinish = "</Team>";
        boolean homeStart = false;
        boolean awayStart = false;
        while ((inLine = reader.readLine()) != null) {
            if (inLine.indexOf(teamFinish) != -1) {
                homeStart = false;
                awayStart = false;
            }
            if (inLine.indexOf(homeTo) != -1) {
                Match matchResult = result.get(matchId);
                matchResult.setHomeScore(slice(inLine, scoreFrom, homeTo));
                homeStart = true;
            }
            if (homeStart && inLine.indexOf(goalInclud) != -1) {
                MatchEvent me = new MatchEvent();
                String playerName = JavaCIPUnknownScope.getPlayerName(slice(inLine, playerFrom, playerTo));
                me.setPlayerName(playerName);
                String time = slice(inLine, timeFrom, timeTo);
                me.setTime(time);
                Match m = result.get(matchId);
                List<MatchEvent> mes = m.getHomeEvents();
                boolean exist = false;
                for (MatchEvent _me : mes) {
                    String _mename = _me.getPlayerName();
                    String _metime = _me.getTime();
                    String mename = me.getPlayerName();
                    String metime = me.getTime();
                    if (mename.equals(_mename) && metime.equals(_metime)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    mes.add(me);
                }
            }
            if (inLine.indexOf(awayTo) != -1) {
                Match m = result.get(matchId);
                m.setAwayScore(slice(inLine, scoreFrom, awayTo));
                awayStart = true;
            }
            if (awayStart && inLine.indexOf(goalInclud) != -1) {
                MatchEvent me = new MatchEvent();
                String playerName = JavaCIPUnknownScope.getPlayerName(slice(inLine, playerFrom, playerTo));
                me.setPlayerName(playerName);
                String time = slice(inLine, timeFrom, timeTo);
                me.setTime(time);
                Match m = result.get(matchId);
                List<MatchEvent> mes = m.getAwayEvents();
                boolean exist = false;
                for (MatchEvent _me : mes) {
                    String _mename = _me.getPlayerName();
                    String _metime = _me.getTime();
                    String mename = me.getPlayerName();
                    String metime = me.getTime();
                    if (mename.equals(_mename) && metime.equals(_metime)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    mes.add(me);
                }
            }
        }
        reader.close();
    }
}
