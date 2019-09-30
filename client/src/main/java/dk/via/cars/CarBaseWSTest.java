package dk.via.cars;

import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CarBaseWSTest {
	private CarBaseWS carBase;
	
	@Before
	public void setUp() throws RemoteException, NotBoundException, MalformedURLException {
		URL endpoint = new URL("http://localhost:8090/cars?wsdl");
		QName qName = new QName("http://cars.via.dk/", "CarBaseService");
		Service service = Service.create(endpoint, qName);
		carBase = service.getPort(CarBaseWS.class);
	}

	@Test
	public void test() throws RemoteException {
		Money eur = new Money(new BigDecimal("4999.99"), "EUR");
		CarDTO car = carBase.getCar("AV 41 213");
		carBase.setPrice(car, eur);
		List<CarDTO> allCars = carBase.getAllCars();
		assertEquals(1, allCars.size());
		assertEquals(eur, allCars.get(0).getPrice());
	}
}
