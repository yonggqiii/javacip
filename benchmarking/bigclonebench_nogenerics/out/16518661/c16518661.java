class c16518661 {

    public String quebraLink(String link) throws StringIndexOutOfBoundsRuntimeException {
        link = link.replace(".url", "");
        int cod = 0;
        final String linkInit = link.replace("#", "");
        boolean estado = false;
        char letra;
        String linkOrig;
        String newlink = "";
        linkOrig = link.replace("#", "");
        linkOrig = linkOrig.replace(".url", "");
        linkOrig = linkOrig.replace(".html", "");
        linkOrig = linkOrig.replace("http://", "");
        if (linkOrig.contains("clubedodownload")) {
            for (int i = 7; i < linkInit.length(); i++) {
                if (linkOrig.charAt(i) == '/') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    if (newlink.contains("//:ptth")) {
                        newlink = JavaCIPUnknownScope.inverteFrase(newlink);
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    } else if (newlink.contains("http://")) {
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    }
                }
            }
        }
        if (linkOrig.contains("protetordelink.tv")) {
            for (int i = linkOrig.length() - 1; i >= 0; i--) {
                letra = linkOrig.charAt(i);
                if (letra == '/') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    newlink = JavaCIPUnknownScope.HexToChar(newlink);
                    if (newlink.contains("ptth")) {
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            newlink = JavaCIPUnknownScope.inverteFrase(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            newlink = JavaCIPUnknownScope.inverteFrase(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    } else {
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
        }
        if (linkOrig.contains("baixeaquifilmes")) {
            for (int i = 0; i < linkOrig.length(); i++) {
                letra = linkOrig.charAt(i);
                if (letra == '?') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    if (newlink.contains(":ptth")) {
                        newlink = JavaCIPUnknownScope.inverteFrase(newlink);
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    } else {
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
        }
        if (linkOrig.contains("downloadsgratis")) {
            for (int i = 0; i < linkOrig.length(); i++) {
                letra = linkOrig.charAt(i);
                if (letra == '!') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    if (JavaCIPUnknownScope.precisaRepassar(QuebraLink.decode64(newlink))) {
                        newlink = quebraLink(QuebraLink.decode64(newlink));
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    } else {
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    }
                }
            }
        }
        newlink = "";
        if (linkOrig.contains("vinxp")) {
            System.out.println("é");
            for (int i = 1; i < linkOrig.length(); i++) {
                if (linkOrig.charAt(i) == '=') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    break;
                }
            }
            if (newlink.contains(".vinxp")) {
                newlink = newlink.replace(".vinxp", "");
            }
            newlink = JavaCIPUnknownScope.decodeCifraDeCesar(newlink);
            System.out.println(newlink);
            return newlink;
        }
        if (linkOrig.contains("?")) {
            String linkTemporary = "";
            newlink = "";
            if (linkOrig.contains("go!")) {
                linkOrig = linkOrig.replace("?go!", "?");
            }
            if (linkOrig.contains("=")) {
                for (int i = 0; i < linkOrig.length(); i++) {
                    letra = linkOrig.charAt(i);
                    if (letra == '=') {
                        for (int j = i + 1; j < linkOrig.length(); j++) {
                            newlink += linkOrig.charAt(j);
                        }
                        linkTemporary = QuebraLink.decode64(newlink);
                        break;
                    }
                }
                if (linkTemporary.contains("http")) {
                    newlink = "";
                    for (int i = 0; i < linkTemporary.length(); i++) {
                        letra = linkTemporary.charAt(i);
                        if (letra == 'h') {
                            for (int j = i; j < linkTemporary.length(); j++) {
                                newlink += linkTemporary.charAt(j);
                            }
                            newlink = newlink.replace("!og", "");
                            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                                newlink = quebraLink(newlink);
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            } else {
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            }
                        }
                    }
                }
                if (linkTemporary.contains("ptth")) {
                    newlink = "";
                    linkTemporary = JavaCIPUnknownScope.inverteFrase(linkTemporary);
                    for (int i = 0; i < linkTemporary.length(); i++) {
                        letra = linkTemporary.charAt(i);
                        if (letra == 'h') {
                            for (int j = i; j < linkTemporary.length(); j++) {
                                newlink += linkTemporary.charAt(j);
                            }
                            newlink = newlink.replace("!og", "");
                            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                                newlink = quebraLink(newlink);
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            } else {
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            }
                        }
                    }
                }
            }
            linkTemporary = "";
            for (int i = 0; i < linkOrig.length(); i++) {
                letra = linkOrig.charAt(i);
                if (letra == '?') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        linkTemporary += linkOrig.charAt(j);
                    }
                    link = QuebraLink.decode64(linkTemporary);
                    break;
                }
            }
            if (link.contains("http")) {
                newlink = "";
                for (int i = 0; i < link.length(); i++) {
                    letra = link.charAt(i);
                    if (letra == 'h') {
                        for (int j = i; j < link.length(); j++) {
                            newlink += link.charAt(j);
                        }
                        newlink = newlink.replace("!og", "");
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
            if (link.contains("ptth")) {
                newlink = "";
                linkTemporary = JavaCIPUnknownScope.inverteFrase(link);
                for (int i = 0; i < linkTemporary.length(); i++) {
                    letra = linkTemporary.charAt(i);
                    if (letra == 'h') {
                        for (int j = i; j < linkTemporary.length(); j++) {
                            newlink += linkTemporary.charAt(j);
                        }
                        newlink = newlink.replace("!og", "");
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
            linkOrig = linkInit;
            link = linkOrig;
            newlink = "";
        }
        if (linkOrig.contains("?")) {
            String linkTemporary = "";
            newlink = "";
            if (linkOrig.contains("go!")) {
                linkOrig = linkOrig.replace("?go!", "?");
            }
            if (linkOrig.contains("=")) {
                for (int i = 0; i < linkOrig.length(); i++) {
                    letra = linkOrig.charAt(i);
                    if (letra == '=') {
                        for (int j = i + 1; j < linkOrig.length(); j++) {
                            newlink += linkOrig.charAt(j);
                        }
                        linkTemporary = linkTemporary.replace(".", "");
                        try {
                            linkTemporary = JavaCIPUnknownScope.HexToChar(newlink);
                        } catch (RuntimeException e) {
                            System.err.println("erro hex 1º");
                            estado = true;
                        }
                        break;
                    }
                }
                if (linkTemporary.contains("http") && !estado) {
                    newlink = "";
                    for (int i = 0; i < linkTemporary.length(); i++) {
                        letra = linkTemporary.charAt(i);
                        if (letra == 'h') {
                            for (int j = i; j < linkTemporary.length(); j++) {
                                newlink += linkTemporary.charAt(j);
                            }
                            newlink = newlink.replace("!og", "");
                            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                                newlink = quebraLink(newlink);
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            } else {
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            }
                        }
                    }
                }
                if (linkTemporary.contains("ptth") && !estado) {
                    newlink = "";
                    linkTemporary = JavaCIPUnknownScope.inverteFrase(linkTemporary);
                    for (int i = 0; i < linkTemporary.length(); i++) {
                        letra = linkTemporary.charAt(i);
                        if (letra == 'h') {
                            for (int j = i; j < linkTemporary.length(); j++) {
                                newlink += linkTemporary.charAt(j);
                            }
                            newlink = newlink.replace("!og", "");
                            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                                newlink = quebraLink(newlink);
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            } else {
                                if (JavaCIPUnknownScope.isValid(newlink)) {
                                    return newlink;
                                }
                            }
                        }
                    }
                }
            }
            estado = false;
            linkTemporary = "";
            for (int i = 0; i < linkOrig.length(); i++) {
                letra = linkOrig.charAt(i);
                if (letra == '?') {
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        linkTemporary += linkOrig.charAt(j);
                    }
                    linkTemporary = linkTemporary.replace(".", "");
                    try {
                        link = JavaCIPUnknownScope.HexToChar(linkTemporary);
                    } catch (RuntimeException e) {
                        System.err.println("erro hex 2º");
                        estado = true;
                    }
                    break;
                }
            }
            if (link.contains("http") && !estado) {
                newlink = "";
                for (int i = 0; i < link.length(); i++) {
                    letra = link.charAt(i);
                    if (letra == 'h') {
                        for (int j = i; j < link.length(); j++) {
                            newlink += link.charAt(j);
                        }
                        newlink = newlink.replace("!og", "");
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
            if (link.contains("ptth") && !estado) {
                newlink = "";
                linkTemporary = JavaCIPUnknownScope.inverteFrase(link);
                for (int i = 0; i < linkTemporary.length(); i++) {
                    letra = linkTemporary.charAt(i);
                    if (letra == 'h') {
                        for (int j = i; j < linkTemporary.length(); j++) {
                            newlink += linkTemporary.charAt(j);
                        }
                        newlink = newlink.replace("!og", "");
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
            linkOrig = linkInit;
            link = linkOrig;
            newlink = "";
        }
        if (linkOrig.contains("?") && !linkOrig.contains("id=") && !linkOrig.contains("url=") && !linkOrig.contains("link=") && !linkOrig.contains("r=http") && !linkOrig.contains("r=ftp")) {
            for (int i = 0; i < linkOrig.length(); i++) {
                letra = linkOrig.charAt(i);
                if (letra == '?') {
                    newlink = "";
                    for (int j = i + 1; j < linkOrig.length(); j++) {
                        newlink += linkOrig.charAt(j);
                    }
                    if (newlink.contains("ptth")) {
                        newlink = JavaCIPUnknownScope.inverteFrase(newlink);
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    } else {
                        if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                            newlink = quebraLink(newlink);
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        } else {
                            if (JavaCIPUnknownScope.isValid(newlink)) {
                                return newlink;
                            }
                        }
                    }
                }
            }
        }
        if ((link.contains("url=")) || (link.contains("link=")) || (link.contains("?r=http")) || (link.contains("?r=ftp"))) {
            if (!link.contains("//:ptth")) {
                for (int i = 0; i < link.length(); i++) {
                    letra = link.charAt(i);
                    if (letra == '=') {
                        for (int j = i + 1; j < link.length(); j++) {
                            letra = link.charAt(j);
                            newlink += letra;
                        }
                        break;
                    }
                }
                if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                    newlink = quebraLink(newlink);
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                } else {
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                }
            }
        }
        if (linkOrig.contains("//:ptth") || linkOrig.contains("//:sptth")) {
            if (linkOrig.contains("=")) {
                for (int i = 0; i < linkOrig.length(); i++) {
                    letra = linkOrig.charAt(i);
                    if (letra == '=') {
                        for (int j = linkOrig.length() - 1; j > i; j--) {
                            letra = linkOrig.charAt(j);
                            newlink += letra;
                        }
                        break;
                    }
                }
                if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                    newlink = quebraLink(newlink);
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                } else {
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                }
            }
            newlink = JavaCIPUnknownScope.inverteFrase(linkOrig);
            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                newlink = quebraLink(newlink);
                if (JavaCIPUnknownScope.isValid(newlink)) {
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(newlink)) {
                    return newlink;
                }
            }
        }
        if (linkOrig.contains("?go!")) {
            linkOrig = linkOrig.replace("?go!", "?down!");
            newlink = linkOrig;
            if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                newlink = quebraLink(newlink);
                if (JavaCIPUnknownScope.isValid(newlink)) {
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(newlink)) {
                    return newlink;
                }
            }
        }
        if (linkOrig.contains("down!")) {
            linkOrig = linkOrig.replace("down!", "");
            return quebraLink(linkOrig);
        }
        newlink = "";
        for (int i = linkOrig.length() - 4; i >= 0; i--) {
            letra = linkOrig.charAt(i);
            if (letra == '=') {
                for (int j = i + 1; j < linkOrig.length(); j++) {
                    newlink += linkOrig.charAt(j);
                }
                break;
            }
        }
        String ltmp = "";
        try {
            ltmp = JavaCIPUnknownScope.HexToChar(newlink);
        } catch (RuntimeException e) {
            System.err.println("erro hex 3º");
        }
        if (ltmp.contains("http://")) {
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                ltmp = quebraLink(ltmp);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else if (ltmp.contains("//:ptth")) {
            ltmp = JavaCIPUnknownScope.inverteFrase(ltmp);
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                ltmp = quebraLink(ltmp);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else {
            ltmp = newlink;
        }
        ltmp = JavaCIPUnknownScope.decode64(newlink);
        if (ltmp.contains("http://")) {
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                ltmp = quebraLink(newlink);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else if (ltmp.contains("//:ptth")) {
            ltmp = JavaCIPUnknownScope.inverteFrase(ltmp);
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                newlink = quebraLink(newlink);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else {
            ltmp = newlink;
        }
        try {
            ltmp = JavaCIPUnknownScope.decodeAscii(newlink);
        } catch (NumberFormatRuntimeException e) {
            System.err.println("erro ascii");
        }
        if (ltmp.contains("http://")) {
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                ltmp = quebraLink(newlink);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else if (ltmp.contains("//:ptth")) {
            ltmp = JavaCIPUnknownScope.inverteFrase(ltmp);
            if (JavaCIPUnknownScope.precisaRepassar(ltmp)) {
                ltmp = quebraLink(ltmp);
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            } else {
                if (JavaCIPUnknownScope.isValid(ltmp)) {
                    newlink = ltmp;
                    return newlink;
                }
            }
        } else {
            ltmp = null;
        }
        newlink = "";
        int cont = 0;
        letra = '\0';
        ltmp = "";
        newlink = "";
        for (int i = linkOrig.length() - 4; i >= 0; i--) {
            letra = linkOrig.charAt(i);
            if (letra == '=' || letra == '?') {
                for (int j = i + 1; j < linkOrig.length(); j++) {
                    if (linkOrig.charAt(j) == '.') {
                        break;
                    }
                    newlink += linkOrig.charAt(j);
                }
                break;
            }
        }
        ltmp = newlink;
        String tmp = "";
        String tmp2 = "";
        do {
            try {
                tmp = JavaCIPUnknownScope.HexToChar(ltmp);
                tmp2 = JavaCIPUnknownScope.HexToChar(JavaCIPUnknownScope.inverteFrase(ltmp));
                if (!tmp.isEmpty() && tmp.length() > 5 && !tmp.contains("") && !tmp.contains("§") && !tmp.contains("�") && !tmp.contains("")) {
                    ltmp = JavaCIPUnknownScope.HexToChar(ltmp);
                } else if (!JavaCIPUnknownScope.inverteFrase(tmp2).isEmpty() && JavaCIPUnknownScope.inverteFrase(tmp2).length() > 5 && !JavaCIPUnknownScope.inverteFrase(tmp2).contains("") && !JavaCIPUnknownScope.inverteFrase(tmp2).contains("§") && !JavaCIPUnknownScope.inverteFrase(tmp2).contains("�")) {
                    ltmp = JavaCIPUnknownScope.HexToChar(JavaCIPUnknownScope.inverteFrase(ltmp));
                }
            } catch (NumberFormatRuntimeException e) {
            }
            tmp = JavaCIPUnknownScope.decode64(ltmp);
            tmp2 = JavaCIPUnknownScope.decode64(JavaCIPUnknownScope.inverteFrase(ltmp));
            if (!tmp.contains("�") && !tmp.contains("ޚ")) {
                ltmp = JavaCIPUnknownScope.decode64(ltmp);
            } else if (!tmp2.contains("�") && !tmp2.contains("ޚ")) {
                ltmp = JavaCIPUnknownScope.decode64(JavaCIPUnknownScope.inverteFrase(ltmp));
            }
            try {
                tmp = JavaCIPUnknownScope.decodeAscii(ltmp);
                tmp2 = JavaCIPUnknownScope.decodeAscii(JavaCIPUnknownScope.inverteFrase(ltmp));
                if (!tmp.contains("") && !tmp.contains("�") && !tmp.contains("§") && !tmp.contains("½") && !tmp.contains("*") && !tmp.contains("\"") && !tmp.contains("^")) {
                    ltmp = JavaCIPUnknownScope.decodeAscii(ltmp);
                } else if (!tmp2.contains("") && !tmp2.contains("�") && !tmp2.contains("§") && !tmp2.contains("½") && !tmp2.contains("*") && !tmp2.contains("\"") && !tmp2.contains("^")) {
                    ltmp = JavaCIPUnknownScope.decodeAscii(JavaCIPUnknownScope.inverteFrase(ltmp));
                }
            } catch (NumberFormatRuntimeException e) {
            }
            cont++;
            if (ltmp.contains("http")) {
                newlink = ltmp;
                if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                    newlink = quebraLink(newlink);
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                } else {
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                }
            } else if (ltmp.contains("ptth")) {
                newlink = JavaCIPUnknownScope.inverteFrase(ltmp);
                if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                    newlink = quebraLink(newlink);
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                } else {
                    if (JavaCIPUnknownScope.isValid(newlink)) {
                        return newlink;
                    }
                }
            }
        } while (!JavaCIPUnknownScope.isValid(newlink) && cont <= 20);
        tmp = null;
        tmp2 = null;
        ltmp = null;
        String leitura = "";
        try {
            leitura = JavaCIPUnknownScope.readHTML(linkInit);
        } catch (IORuntimeException e) {
        }
        leitura = leitura.toLowerCase();
        if (leitura.contains("trocabotao")) {
            newlink = "";
            for (int i = leitura.indexOf("trocabotao"); i < leitura.length(); i++) {
                if (Character.isDigit(leitura.charAt(i))) {
                    int tmpInt = i;
                    while (Character.isDigit(leitura.charAt(tmpInt))) {
                        newlink += leitura.charAt(tmpInt);
                        tmpInt++;
                    }
                    cod = Integer.parseInt(newlink);
                    break;
                }
            }
            if (cod != 0) {
                for (int i = 7; i < linkInit.length(); i++) {
                    letra = linkInit.charAt(i);
                    if (letra == '/') {
                        newlink = linkInit.substring(0, i + 1) + "linkdiscover.php?cod=" + cod;
                        break;
                    }
                }
                DataInputStream dat = null;
                try {
                    URL url = new URL(newlink);
                    InputStream in = url.openStream();
                    dat = new DataInputStream(new BufferedInputStream(in));
                    leitura = "";
                    int dado;
                    while ((dado = dat.read()) != -1) {
                        letra = (char) dado;
                        leitura += letra;
                    }
                    newlink = leitura.replaceAll(" ", "");
                    if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                        newlink = quebraLink(newlink);
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    } else {
                        if (JavaCIPUnknownScope.isValid(newlink)) {
                            return newlink;
                        }
                    }
                } catch (MalformedURLRuntimeException ex) {
                    System.out.println("URL mal formada.");
                } catch (IORuntimeException e) {
                } finally {
                    try {
                        if (dat != null) {
                            dat.close();
                        }
                    } catch (IORuntimeException e) {
                        System.err.println("Falha ao fechar fluxo.");
                    }
                }
            }
        }
        if (JavaCIPUnknownScope.precisaRepassar(linkInit)) {
            if (linkInit.substring(8).contains("http")) {
                newlink = linkInit.substring(linkInit.indexOf("http", 8), linkInit.length());
                if (JavaCIPUnknownScope.isValid(newlink)) {
                    return newlink;
                }
            }
        }
        newlink = "";
        StringBuffer strBuf = null;
        try {
            strBuf = new StringBuffer(JavaCIPUnknownScope.readHTML(linkInit));
            for (String tmp3 : JavaCIPUnknownScope.getLibrary()) {
                if (strBuf.toString().toLowerCase().contains(tmp3)) {
                    for (int i = strBuf.toString().indexOf(tmp3); i >= 0; i--) {
                        if (strBuf.toString().charAt(i) == '"') {
                            for (int j = i + 1; j < strBuf.length(); j++) {
                                if (strBuf.toString().charAt(j) == '"') {
                                    if (JavaCIPUnknownScope.precisaRepassar(newlink)) {
                                        newlink = quebraLink(newlink);
                                        if (JavaCIPUnknownScope.isValid(newlink)) {
                                            return newlink;
                                        }
                                    } else {
                                        if (JavaCIPUnknownScope.isValid(newlink)) {
                                            return newlink;
                                        }
                                    }
                                } else {
                                    newlink += strBuf.toString().charAt(j);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IORuntimeException ex) {
        }
        GUIQuebraLink.isBroken = false;
        return "Desculpe o link não pode ser quebrado.";
    }
}
