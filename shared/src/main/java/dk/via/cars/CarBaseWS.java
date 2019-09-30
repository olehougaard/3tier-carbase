package dk.via.cars;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public interface CarBaseWS {
	@WebMethod CarDTO registerCar(String licenseNumber, String model, int year, Money price);
	@WebMethod void unregisterCar(CarDTO car);
	@WebMethod CarDTO setPrice(CarDTO car, Money price);
	@WebMethod CarDTO getCar(String licenseNumber);
	@WebMethod List<CarDTO> getAllCars();
}
