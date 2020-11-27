package ventanas;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;

public class VentanaPrograma extends JFrame {
	public VentanaPrograma() {
		getContentPane().setLayout(null);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(63, 269, 97, 25);
		getContentPane().add(btnBuscar);
		
		JButton btnVender = new JButton("Vender");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAddVehiculo.main(null);
			}
		});
		btnVender.setBounds(341, 269, 97, 25);
		getContentPane().add(btnVender);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBorrar.setBounds(172, 269, 97, 25);
		getContentPane().add(btnBorrar);
		
		JLabel lblAutotrader = new JLabel("AutoTrader");
		lblAutotrader.setFont(new Font("Adobe Arabic", Font.BOLD | Font.ITALIC, 17));
		lblAutotrader.setBounds(202, 0, 178, 71);
		getContentPane().add(lblAutotrader);
		
		JComboBox cbMarca = new JComboBox();
		cbMarca.setBounds(63, 100, 69, 22);
		getContentPane().add(cbMarca);
		
		JComboBox cbAnioDesde = new JComboBox();
		cbAnioDesde.setBounds(63, 135, 69, 22);
		getContentPane().add(cbAnioDesde);
		
		JComboBox cbModelo = new JComboBox();
		cbModelo.setBounds(164, 100, 69, 22);
		getContentPane().add(cbModelo);
		
		JComboBox cbCombustible = new JComboBox();
		cbCombustible.setBounds(271, 100, 69, 22);
		getContentPane().add(cbCombustible);
		
		JComboBox cbCarroceria = new JComboBox();
		cbCarroceria.setBounds(369, 100, 69, 22);
		getContentPane().add(cbCarroceria);
		
		JComboBox cbAnioHasta = new JComboBox();
		cbAnioHasta.setBounds(164, 135, 69, 22);
		getContentPane().add(cbAnioHasta);
		
		JComboBox cbPrecioDesde = new JComboBox();
		cbPrecioDesde.setBounds(271, 135, 69, 22);
		getContentPane().add(cbPrecioDesde);
		
		JComboBox cbPrecioHasta = new JComboBox();
		cbPrecioHasta.setBounds(369, 135, 69, 22);
		getContentPane().add(cbPrecioHasta);
		
		JComboBox cbKmsDesde = new JComboBox();
		cbKmsDesde.setBounds(63, 170, 69, 22);
		getContentPane().add(cbKmsDesde);
		
		JComboBox cbKmsHasta = new JComboBox();
		cbKmsHasta.setBounds(164, 170, 69, 22);
		getContentPane().add(cbKmsHasta);
		
		JComboBox cbPotencia = new JComboBox();
		cbPotencia.setBounds(271, 170, 69, 22);
		getContentPane().add(cbPotencia);
		
		JComboBox cbTransmision = new JComboBox();
		cbTransmision.setBounds(369, 170, 69, 22);
		getContentPane().add(cbTransmision);
		
		JComboBox cbNPuertas = new JComboBox();
		cbNPuertas.setBounds(63, 205, 69, 22);
		getContentPane().add(cbNPuertas);
		
		JComboBox cbNPlazas = new JComboBox();
		cbNPlazas.setBounds(164, 205, 69, 22);
		getContentPane().add(cbNPlazas);
		
		JLabel lblBienvenidoSeleccioneSus = new JLabel("Bienvenido");
		lblBienvenidoSeleccioneSus.setBounds(230, 54, 176, 16);
		getContentPane().add(lblBienvenidoSeleccioneSus);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
