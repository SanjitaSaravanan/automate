package resources;

public enum APIresource {
    createOrder("/session");

    private String resource;
    APIresource(String resource) {
        this.resource = resource;
    }
    public String getResource() {
        return resource;
    }
}
