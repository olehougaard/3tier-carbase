package dk.via.cars;

import java.rmi.RemoteException;
import java.util.*;

public class CarBaseImplementation {
	private CarDAO dao;

	public CarBaseImplementation(CarDAO dao) {
		this.dao = dao;
	}
	
	public CarDTO registerCar(String licenseNumber, String model, int year, Money price) throws RemoteException {
		CarDTO carDTO = dao.create(licenseNumber, model, year, price);
		return carDTO;
	}
	

	public CarDTO getCar(String licenseNumber) throws RemoteException {
		return dao.read(licenseNumber);
	}



	public List<CarDTO> getAllCars() throws RemoteException {
		Collection<CarDTO> allCars = dao.readAll();
		return new ArrayList<>(allCars);
	}

	public void unregisterCar(CarDTO car) throws RemoteException {
		dao.delete(car);
	}

	public CarDTO setPrice(CarDTO car, Money price) throws RemoteException{
		CarDTO newCar = new CarDTO(car.getLicenseNumber(), car.getModel(), car.getYear(), price);
		dao.update(newCar);
		return newCar;
	}
}
