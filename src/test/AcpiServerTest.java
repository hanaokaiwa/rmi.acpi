package test;

import java.rmi.RemoteException;

import acpi.AcpiServer;

public class AcpiServerTest {

	public static void main(String[] args) throws RemoteException {
		AcpiServer server = new AcpiServer();
		System.out.println(server.getBatteryCapacity());
		System.out.println(server.onAcPower());
		System.out.println(server.isCharging());
	}
}
