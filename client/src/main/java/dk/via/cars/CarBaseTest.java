package dk.via.cars;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.via.cars.Car;
import dk.via.cars.CarBase;
import dk.via.cars.Money;

public class CarBaseTest {
	private CarBase carBase;
	
	@Before
	public void setUp() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1099);
		carBase = (CarBase) registry.lookup("CarBase");
	}

	@Test
	public void test() throws RemoteException {
		Money eur = new Money(new BigDecimal("4999.99"), "EUR");
		Car car = carBase.getCar("AV 41 213");
		car.setPrice(eur);
		List<Car> allCars = carBase.getAllCars();
		assertEquals(1, allCars.size());
		assertEquals(eur, allCars.get(0).getPrice());
	}
}
