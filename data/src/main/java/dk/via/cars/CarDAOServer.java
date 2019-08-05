package dk.via.cars;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class CarDAOServer extends UnicastRemoteObject implements CarDAO {
	private static final long serialVersionUID = 1;
	private DatabaseHelper<CarDTO> helper;

	public CarDAOServer(DatabaseHelper<CarDTO> helper) throws RemoteException {
		this.helper = helper;
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=car_base", "postgres", "password");
	}

	@Override
	public CarDTO create(String licenseNo, String model, int year, Money price) throws RemoteException {
		helper.executeUpdate("INSERT INTO car VALUES (?, ?, ?, ?)", licenseNo, model, year, price.getAmount(), price.getCurrency());
		return new CarDTO(licenseNo, model, year, price);
	}
	
	private CarDTO createCar(ResultSet rs) throws SQLException {
		String licenseNo = rs.getString("license_number");
		String model = rs.getString("model");
		int year = rs.getInt("year");
		BigDecimal priceAmount = rs.getBigDecimal("price_amount");
		String priceCurrency = rs.getString("price_currency");
		Money price = new Money(priceAmount, priceCurrency);
		return new CarDTO(licenseNo, model, year, price);
	}

	@Override
	public CarDTO read(String licenseNumber) throws RemoteException {
		return helper.mapSingle((rs) -> createCar(rs), "SELECT * FROM car where license_number = ?", licenseNumber);
	}

	@Override
	public Collection<CarDTO> readAll() throws RemoteException {
		return helper.map((rs) -> createCar(rs), "SELECT * FROM car");
	}

	@Override
	public void update(CarDTO car) throws RemoteException {
		helper.executeUpdate("UPDATE car SET model=?, year=?, price_amount=?, price_currency=? WHERE license_number = ?", 
				car.getModel(), car.getYear(), car.getPrice().getAmount(), car.getPrice().getCurrency(), car.getLicenseNumber());
	}

	@Override
	public void delete(CarDTO car) throws RemoteException {
		helper.executeUpdate("DELETE FROM car WHERE license_number = ?", car.getLicenseNumber());
	}
	
	private void createTestDB() throws SQLException {
		try (Connection connection = getConnection()) {
			Statement stat = connection.createStatement();
			stat.executeUpdate("DELETE FROM car");
			stat.executeUpdate("INSERT INTO car VALUES('AV 41 213', 'Ford', 2014, 100000, 'DKK')");
		}
	}
	
	public static void main(String[] args) throws Exception {
		DatabaseHelper<CarDTO> helper = new DatabaseHelper<>("jdbc:postgresql://localhost:5432/postgres?currentSchema=car_base", "postgres", "password");
		CarDAOServer carDAOServer = new CarDAOServer(helper);
		carDAOServer.createTestDB();
		Registry registry = LocateRegistry.getRegistry(1099);
		registry.rebind("carDao", carDAOServer);
	}
}
