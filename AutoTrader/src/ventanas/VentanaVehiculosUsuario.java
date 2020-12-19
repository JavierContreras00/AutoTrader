package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bbdd.GestorBD;
import clasesBasicas.Coche;
import clasesBasicas.Usuario;

public class VentanaVehiculosUsuario extends JFrame {
	private JPanel panelContenidos;
	private JButton btnELiminar;
	private JButton btnVolver;
	public VentanaVehiculosUsuario(Usuario u) {
		this.setTitle("VVS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 300);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("img/vvs.png"));

		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		setLayout(new BorderLayout(10, 10));

		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new GridLayout(1, 1));
		panelContenidos.add(panelCentral, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.WHITE);
		panelBotones.setLayout(new GridLayout(1,2));
		panelContenidos.add(panelBotones, BorderLayout.SOUTH);

		JList<String> listaVehiculos = new JList<String>();
		DefaultListModel<String> modeloLista = new DefaultListModel<String>();
		btnELiminar = new JButton("Eliminar");

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
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProgramaFs v = new VentanaProgramaFs(u);
				v.setVisible(true);
				dispose();
			}
		});
		listaVehiculos.setModel(modeloLista);
		panelCentral.add(new JScrollPane(listaVehiculos));

		panelBotones.add(btnELiminar);
		panelBotones.add(btnVolver);

	}

}