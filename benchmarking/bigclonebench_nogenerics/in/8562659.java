


class c8562659 {

    protected void validate(long googcmId, long reservePrice, String description, String category, int days, String status, String title, byte[] imgBytes) throws PortalRuntimeException, SystemRuntimeException {
        if (Validator.isNull(description)) throw new AuctionDescriptionRuntimeException(); else if (Validator.isNull(title)) throw new AuctionTitleRuntimeException(); else if (Validator.isNull(category)) throw new CategoryIdRuntimeException();
        if (googcmId < 1000000000l | googcmId > 999999999999999l) throw new AuctionGoogCMIdRuntimeException();
        long imgMaxSize = 1048576l;
        if ((imgBytes == null) || (imgBytes.length > ((int) imgMaxSize))) throw new AuctionImageSizeRuntimeException();
        if (days != 3 & days != 7 & days != 10) throw new AuctionEndeDateRuntimeException();
        if ((reservePrice < 0) || (reservePrice > 10000)) throw new AuctionReservePriceRuntimeException();
        try {
            URL url = new URL("https://checkout.google.com/api/checkout/v2/checkoutForm/Merchant/" + googcmId);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            boolean sellerExists = true;
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.contains("" + googcmId)) {
                    throw new AuctionGoogCMAccountRuntimeException();
                }
            }
            rd.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
