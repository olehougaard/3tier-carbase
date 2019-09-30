package dk.via.cars;

import javax.jws.WebService;
import java.rmi.RemoteException;
import java.util.List;

@WebService(endpointInterface ="dk.via.cars.CarBaseWS",
            serviceName = "CarBaseService")
public class CarBaseService implements CarBaseWS {

    private final CarBaseImplementation implementation;

    public CarBaseService() throws RemoteException {
        this(DAOLocator.getDAO());
    }

    public CarBaseService(CarDAO dao) {
        implementation = new CarBaseImplementation(dao);
    }

    @Override
    public CarDTO registerCar(String licenseNumber, String model, int year, Money price) {
        try {
            return implementation.registerCar(licenseNumber, model, year, price);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unregisterCar(CarDTO car) {
        try {
            implementation.unregisterCar(car);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarDTO setPrice(CarDTO car, Money price) {
        try {
            return implementation.setPrice(car, price);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarDTO getCar(String licenseNumber) {
        try {
            return implementation.getCar(licenseNumber);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarDTO> getAllCars() {
        try {
            return implementation.getAllCars();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
