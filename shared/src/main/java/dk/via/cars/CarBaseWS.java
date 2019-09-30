package dk.via.cars;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public interface CarBaseWS {
	@WebMethod CarDTO registerCar(@WebParam(name = "licenseNumber") String licenseNumber,
								  @WebParam(name = "model") String model,
								  @WebParam(name = "year") int year,
								  @WebParam(name = "price") Money price);
	@WebMethod void unregisterCar(@WebParam(name = "car") CarDTO car);
	@WebMethod CarDTO setPrice(@WebParam(name = "car") CarDTO car,
							   @WebParam(name = "price") Money price);
	@WebMethod CarDTO getCar(@WebParam(name = "licenseNumber") String licenseNumber);
	@WebMethod List<CarDTO> getAllCars();
}
