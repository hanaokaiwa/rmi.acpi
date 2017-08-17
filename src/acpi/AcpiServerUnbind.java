/**
 * 
 */
package acpi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * ACPIのunbindを実施する
 */
public class AcpiServerUnbind {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Bind the remote object's stub in the registry
			final Registry registry = LocateRegistry.getRegistry();
			registry.unbind(AcpiConstants.BIND_NAME);

			System.out.println("Server unbinded.");
		} catch (RemoteException | NotBoundException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
