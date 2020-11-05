package main;

import java.awt.EventQueue;
import java.util.UUID;

import ventanas.VentanaInicio;

public class App {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					VentanaInicio vi = new VentanaInicio();
					vi.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}
