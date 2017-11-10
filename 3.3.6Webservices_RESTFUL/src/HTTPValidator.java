public class HTTPValidator {

    private String httpRequest;
    public boolean validateHTTPRequest(String httpRequest) {
        this.httpRequest = httpRequest;
        return isRequestValid();
    }

    private boolean isRequestValid() {
        decodeRequest();
        return true;
    }

    private void decodeRequest() {

    }
}
