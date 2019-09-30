package dk.via.cars;

import javax.xml.ws.Endpoint;

public class WebServer {
    public static void main(String[] args) throws Exception {
        CarBaseService service = new CarBaseService();
        Endpoint.publish("http://localhost:8090/cars", service);
    }
}
