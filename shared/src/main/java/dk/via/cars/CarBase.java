package dk.via.cars;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CarBase extends Remote {
	CarDTO registerCar(String licenseNumber, String model, int year, Money price) throws RemoteException;
	void unregisterCar(CarDTO car) throws RemoteException;
	CarDTO setPrice(CarDTO car, Money price) throws RemoteException;
	CarDTO getCar(String licenseNumber) throws RemoteException;
	List<CarDTO> getAllCars() throws RemoteException;
}
