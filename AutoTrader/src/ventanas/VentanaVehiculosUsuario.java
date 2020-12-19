package ventanas;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;

import bbdd.GestorBD;

public class VentanaVehiculosUsuario extends JFrame {
	public VentanaVehiculosUsuario(Usuario u) {
		getContentPane().setLayout(null);
		DefaultListModel<String> modeloLista = new DefaultListModel<String>();
		JList<String> listaVehiculos = new JList<String>();


		JButton btnELiminar = new JButton("Eliminar");
		try {
			Connection con = GestorBD.getConexion();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM  VEHICULOS  WHERE IDUSUARIOS = " + u.getIdusuarios();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("IDVEHICULOS");
				String marca = rs.getString("MARCA") + " ";
				String modelo = rs.getString("MODELO") + " ";
				String precio = rs.getString("PRECIO") + " ";

				modeloLista.addElement(id + " " + marca +  modelo + precio);

				btnELiminar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int seleccionado = listaVehiculos.getSelectedIndex();
						if (seleccionado != -1) {
							modeloLista.removeElementAt(seleccionado);
							GestorBD bd = new GestorBD();
							try {
								Connection con = GestorBD.getConexion();
								String sql = "DELETE FROM VEHICULOS WHERE IDVEHICULOS =?";
								PreparedStatement ps = con.prepareStatement(sql);

								ps.setInt(1, id);
								ps.execute();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
						}

					}

				});
				
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		btnELiminar.setBounds(85, 215, 97, 25);
		getContentPane().add(btnELiminar);
		
		JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProgramaFs v = new VentanaProgramaFs(u);
				v.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(213, 215, 97, 25);
		getContentPane().add(btnVolver);
		
		listaVehiculos.setModel(modeloLista);

		
		listaVehiculos.setBounds(85, 37, 225, 134);
		getContentPane().add(listaVehiculos);
	}
}
