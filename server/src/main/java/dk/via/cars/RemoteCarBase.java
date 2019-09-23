package dk.via.cars;

import java.rmi.RemoteException;
import java.util.List;

public class RemoteCarBase implements CarBase {
	private final CarBaseImplementation implementation;

	public RemoteCarBase(CarDAO dao) {
		implementation = new CarBaseImplementation(dao);
	}
	
	@Override
	public CarDTO registerCar(String licenseNumber, String model, int year, Money price) throws RemoteException {
		return implementation.registerCar(licenseNumber, model, year, price);
	}
	

	@Override
	public CarDTO getCar(String licenseNumber) throws RemoteException {
		return implementation.getCar(licenseNumber);
	}

	@Override
	public List<CarDTO> getAllCars() throws RemoteException {
		return implementation.getAllCars();
	}

	@Override
	public void unregisterCar(CarDTO car) throws RemoteException {
		implementation.unregisterCar(car);
	}

	@Override
	public CarDTO setPrice(CarDTO car, Money price) throws RemoteException {
		return implementation.setPrice(car, price);
	}
}
