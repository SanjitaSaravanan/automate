package testdatabuild;

import pojo.RequestDetails;

public class ObjectManager {

    public orderBuild orderbuild;
    public RequestDetails requestDetails;

    public orderBuild getorderbuild() {
        orderbuild = new orderBuild();
        return orderbuild;
    }
    public RequestDetails getRequestDetails() {
        requestDetails = new RequestDetails();
        return requestDetails;
    }
}
