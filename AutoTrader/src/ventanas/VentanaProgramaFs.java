package ventanas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import bbdd.GestorBD;
import clasesBasicas.Marca;
import clasesBasicas.Usuario;
import interfaces.LeeDeFichero;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class VentanaProgramaFs extends JFrame implements LeeDeFichero {
	private HashMap<String, Marca> mapaMarcas = new HashMap<>();
	private JPanel panelContenidos;
	private JButton btnBuscar;
	private JButton btnFiltrosAvanzados;
	private JButton btnVender;
	private JMenuBar menu;
	private JMenu menuUsuarios;
	private JMenuItem menuItem;	
	private JComboBox<String> cbMarca;
	private String opcionNoSeleccionableMarca = "Marca";
	private JComboBox<String> cbModelo; /// Hasta quen no se eliga la marca va a estar en enable = false
	private String opcionNoSeleccionableModelo = "Modelo";
	private JComboBox<String> cbAnio;
	private String opcionNoSeleccionableAnio = "Anio";
	private JComboBox<String> cbPrecio;
	private String opcionNoSeleccionablePrecio = "Precio";

	private Connection conn;
	private GestorBD con;
	private PreparedStatement st;
	public VentanaProgramaFs(Usuario u) {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// GestorBD gestor = new GestorBD();
				try {
					Connection con = GestorBD.getConexion();
					String sql = "SELECT * FROM vehiculos WHERE ";
					Statement ps = con.createStatement();
					String u_marca = (String) cbMarca.getSelectedItem();
					if (u_marca == "Marca") {
						sql = sql + " MARCA LIKE '%' AND ";

					} else {
						sql = sql + " MARCA = '" + u_marca + "' AND ";
					}
					String u_modelo = (String) cbModelo.getSelectedItem();

					if (u_modelo == "Modelo") {
						sql = sql + " MODELO LIKE '%' AND ";
					} else {
						sql = sql + " MODELO = '" + u_modelo + "' AND ";
					}
					String u_anio = (String) cbAnio.getSelectedItem();
					if (u_anio == "Anio") {
						sql = sql + "ANIO LIKE '%' AND ";
					} else {
						sql = sql + "ANIO = '" + u_anio + "' AND ";
					}
					String u_precio = (String) cbPrecio.getSelectedItem();
					if (u_precio == "Precio") {
						sql = sql + "PRECIO LIKE '%' ";
					} else {
						sql = sql + "PRECIO = '" + u_precio + "'";
					}
					
					ResultSet rs = ps.executeQuery(sql);
					if (rs.next()) {
						rs.beforeFirst();
						while (rs.next()) {
							System.out.print(rs.getInt("IDVEHICULOS") + ", ");
							System.out.print(rs.getString("MARCA") + ", ");
							System.out.print(rs.getString("MODELO") + ", ");
							System.out.print(rs.getInt("ANIO") + ", ");
							System.out.println(rs.getInt("PRECIO"));
						}
					} else {
						System.out.println("LO SIENTO CEPORRO, NO HAY");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				
			
			}
		});
		
		btnNewButton.setBounds(31, 215, 97, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Filtros avanzados");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
					// TODO Auto-generated method stub
					VentanaPrograma v = new VentanaPrograma(u);
					v.setVisible(true);
					setVisible(false);

				
			
			}
		});
		btnNewButton_1.setBounds(152, 215, 131, 25);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Vender");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAddVehiculo v = new VentanaAddVehiculo(u);
				v.setVisible(true);
				setVisible(false);				
			}
		});
		btnNewButton_2.setBounds(308, 215, 97, 25);
		getContentPane().add(btnNewButton_2);
		
		JComboBox cbMarca = new JComboBox();
		cbMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String marcaSeleccionada = (String) cbMarca.getSelectedItem();
				cbModelo.removeAllItems();
				for (Entry<String, Marca> marca : mapaMarcas.entrySet()) {

					if (marcaSeleccionada.equals(marca.getValue().getNombre())) {
						for (String modelos : marca.getValue().getModelos()) {
							cbModelo.addItem(modelos);

						}

					}

				}

			}
		});
		cbMarca.setBounds(82, 49, 31, 22);
		getContentPane().add(cbMarca);
		
		JComboBox cbModelo = new JComboBox();
		cbModelo.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableModelo.equals(objeto)) {
					super.setSelectedItem(objeto);
				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}
		});
		cbModelo.addItem(opcionNoSeleccionableModelo);
		cbModelo.setBounds(82, 94, 31, 22);
		getContentPane().add(cbModelo);
		
		JComboBox cbAnio = new JComboBox();
		cbAnio.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableAnio.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbAnio.addItem(opcionNoSeleccionableAnio);
		Calendar cal = Calendar.getInstance();
		int anio = cal.get(Calendar.YEAR);
		for (int i = 1950; i <= anio; i++) {
			cbAnio.addItem(Integer.toString(i));
		}
		cbAnio.setBounds(82, 129, 31, 22);
		getContentPane().add(cbAnio);
		
		JComboBox cbPrecio = new JComboBox();
		cbPrecio.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionablePrecio.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbPrecio.addItem(opcionNoSeleccionablePrecio);
		for (int i = 1000; i <= 1000000; i += 1000) {
			cbPrecio.addItem(Integer.toString(i));

		}
		cbPrecio.setBounds(82, 164, 31, 22);
		getContentPane().add(cbPrecio);
	}

	@Override
	public void leeMarcas() {
		BufferedReader br = null;
		FileReader ficheroMarca = null;

		try {
			ficheroMarca = new FileReader("data/marcas.csv");
			br = new BufferedReader(ficheroMarca);
			String fila = null;
			while ((fila = br.readLine()) != null) {
				String[] valores = fila.split(";");
				cbMarca.addItem(valores[1]);
				mapaMarcas.put(valores[0], new Marca(valores[0], valores[1]));
			}

			ficheroMarca.close();
			br.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		catch (IOException e) {
			e.printStackTrace();

		}		
	}

	@Override
	public void leeModelos() {
		BufferedReader br = null;
		FileReader ficheroModelos = null;

		try {
			ficheroModelos = new FileReader("data/modelos.csv");
			br = new BufferedReader(ficheroModelos);
			String fila = null;
			while ((fila = br.readLine()) != null) {
				String[] valores = fila.split(";");
				mapaMarcas.get(valores[0]).addModelo(valores[2]);

			}

			ficheroModelos.close();
			br.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		catch (IOException e) {
			e.printStackTrace();

		}		
	}
	
	public static void main(String[] args) {
		VentanaProgramaFs v = new VentanaProgramaFs();
		v.setVisible(true);
	}
}
