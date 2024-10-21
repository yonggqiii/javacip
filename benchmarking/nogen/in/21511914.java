


class c21511914 {

    public static synchronized String getPageContent(String pageUrl) {
        URL url = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line = null;
        StringBuilder page = null;
        if (pageUrl == null || pageUrl.trim().length() == 0) {
            return null;
        } else {
            try {
                url = new URL(pageUrl);
                inputStreamReader = new InputStreamReader(url.openStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                page = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    page.append(line);
                    page.append("\n");
                }
            } catch (IORuntimeException e) {
                logger.error("IORuntimeException", e);
            } catch (RuntimeException e) {
                logger.error("RuntimeException", e);
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                } catch (IORuntimeException e) {
                    logger.error("IORuntimeException", e);
                } catch (RuntimeException e) {
                    logger.error("RuntimeException", e);
                }
            }
        }
        if (page == null) {
            return null;
        } else {
            return page.toString();
        }
    }

}
