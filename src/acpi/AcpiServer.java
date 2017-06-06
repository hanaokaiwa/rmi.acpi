/**
 * 
 */
package acpi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * ACPIの状況を取得するサーバー
 */
public class AcpiServer implements Acpi {

	/*
	 * (non-Javadoc)
	 * 
	 * @see acpi.Acpi#getBatteryCapacity()
	 */
	@Override
	public int getBatteryCapacity() throws RemoteException {
		String capacity = AcpiUtils.readLineFromFile(AcpiConstants.BATTERY_CAPACITY_PATH);
		if (null != capacity) {
			try {
				return Integer.parseInt(capacity);
			} catch (NumberFormatException e) {
				// 何もしない
				;
			}
		}

		String energyNow = AcpiUtils.readLineFromFile(AcpiConstants.BATTERY_ENERGY_NOW_PATH);
		String energyFull = AcpiUtils.readLineFromFile(AcpiConstants.BATTERY_ENERGY_FULL_PATH);
		if ((null != energyNow) && (null != energyFull)) {
			return Integer.parseInt(energyNow) / (Integer.parseInt(energyFull) / 100);
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acpi.Acpi#onAcPower()
	 */
	@Override
	public boolean onAcPower() throws RemoteException {
		String line = AcpiUtils.readLineFromFile(AcpiConstants.AC_POWER_PATH);
		if (AcpiConstants.AC_POWER_ON.equals(line)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isCharging() throws RemoteException {
		File file = new File(AcpiConstants.BATTERY_STATUS_PATH);
		if (file.exists() && file.isFile() && file.canRead()) {
			try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
				String line = br.readLine();
				if (AcpiConstants.BATTERY_STATUS_CHARGING.equals(line)) {
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AcpiServer obj = new AcpiServer();
		try {
			Acpi stub = (Acpi) UnicastRemoteObject.exportObject(obj, AcpiConstants.ACPI_SERVER_PORT_NUMBER);

			// Bind the remote object's stub in the registry
			final Registry registry = LocateRegistry.getRegistry();
			registry.bind(AcpiConstants.BIND_NAME, stub);

			System.out.println("Server ready.");

			SignalHandler signalHandler = new AcpiServerSignalHandler(registry);
			try {
				Signal.handle(new Signal("TERM"), signalHandler);
			} catch (IllegalArgumentException e) {
				// 何もしない
			}
			try {
				Signal.handle(new Signal("INT"), signalHandler);
			} catch (IllegalArgumentException e) {
				// 何もしない
			}
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private static class AcpiServerSignalHandler implements SignalHandler {

		Registry registry;

		AcpiServerSignalHandler(Registry registry) {
			this.registry = registry;
		}

		@Override
		public void handle(Signal signal) {
			try {
				registry.unbind(AcpiConstants.BIND_NAME);
			} catch (RemoteException | NotBoundException e) {
				System.err.println("Unbind exception: " + e.toString());
				e.printStackTrace();
			}
			System.out.println("Server exit.");
			System.exit(0);
		}
	}
}
