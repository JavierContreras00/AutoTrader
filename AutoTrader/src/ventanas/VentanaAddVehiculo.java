package ventanas;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaAddVehiculo extends JFrame {
	public VentanaAddVehiculo() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Anadir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(295, 286, 97, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Volver");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrograma.main(null);
			}
		});
		btnNewButton_1.setBounds(403, 286, 97, 25);
		getContentPane().add(btnNewButton_1);
		
		JList list = new JList();
		list.setBounds(295, 92, 213, 136);
		getContentPane().add(list);
		
		JComboBox cbMarca = new JComboBox();
		cbMarca.setBounds(26, 90, 97, 22);
		getContentPane().add(cbMarca);
		
		JComboBox cbCombustible = new JComboBox();
		cbCombustible.setBounds(26, 136, 97, 22);
		getContentPane().add(cbCombustible);
		
		JComboBox cbAnio = new JComboBox();
		cbAnio.setBounds(26, 171, 97, 22);
		getContentPane().add(cbAnio);
		
		JComboBox cbModelo = new JComboBox();
		cbModelo.setBounds(148, 90, 97, 22);
		getContentPane().add(cbModelo);
		
		JComboBox cbCarroceria = new JComboBox();
		cbCarroceria.setBounds(148, 136, 97, 22);
		getContentPane().add(cbCarroceria);
		
		JComboBox cbNPlazas = new JComboBox();
		cbNPlazas.setBounds(148, 206, 97, 22);
		getContentPane().add(cbNPlazas);
		
		JComboBox cbTransmision = new JComboBox();
		cbTransmision.setBounds(26, 206, 97, 22);
		getContentPane().add(cbTransmision);
		
		JComboBox cbNPuertas = new JComboBox();
		cbNPuertas.setBounds(148, 242, 97, 22);
		getContentPane().add(cbNPuertas);
		
		JButton btnNewButton_2 = new JButton("Imagenes");
		btnNewButton_2.setBounds(26, 241, 97, 25);
		getContentPane().add(btnNewButton_2);
		
		JLabel lblDescripcionDel = new JLabel("Descripcion del vehiculo");
		lblDescripcionDel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblDescripcionDel.setBounds(148, 24, 249, 33);
		getContentPane().add(lblDescripcionDel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
