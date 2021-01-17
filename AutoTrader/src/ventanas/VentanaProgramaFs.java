package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import bbdd.GestorBD;
import clasesBasicas.Marca;
import clasesBasicas.Usuario;
import interfaces.LeeDeFichero;

/**
 * 
 *  Ventana para la busqueda de vehiculos con filtros sencillos
 *
 */

public class VentanaProgramaFs extends JFrame implements LeeDeFichero {
	private HashMap<String, Marca> mapaMarcas = new HashMap<>();
	private JPanel panelContenidos;
	private JButton btnBuscar;
	private JButton btnFiltrosAvanzados;
	private JButton btnVender;

	private JMenuBar menu;
	private JMenu menuUsuarios;
	private JMenuItem menuItem;

	// ComboBox y sus etquetas
	
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
		this.setTitle("autotrader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 550, 300);
		this.setMinimumSize(new Dimension(250, 250));

		// Panel principal
		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		panelContenidos.setLayout(new BorderLayout(15, 15));

//Panel central
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelCentral, BorderLayout.CENTER);

		JPanel panelCentralContenidos = new JPanel();
		panelCentralContenidos.setBackground(Color.WHITE);
		panelCentralContenidos.setLayout(new GridLayout(5, 1, 10, 10));
		panelCentral.add(panelCentralContenidos, BorderLayout.WEST);

		cbMarca = new JComboBox<String>();
		cbMarca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

			}
		});
		cbMarca.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableMarca.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbMarca.addItem(opcionNoSeleccionableMarca);
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

		cbModelo = new JComboBox<String>();
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

		cbAnio = new JComboBox<String>();
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

		cbPrecio = new JComboBox<String>();
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

		// Panel superior
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(Color.WHITE);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelSuperiorS = new JPanel();
		panelSuperiorS.setBackground(Color.WHITE);
		panelSuperiorS.setLayout(new GridLayout(1, 3));
		panelSuperior.add(panelSuperiorS, BorderLayout.NORTH);

		
		menu = new JMenuBar();
		menuUsuarios = new JMenu(u.getEmail());
		menu.add(menuUsuarios);
		menuItem = new JMenuItem("Cerrar sesion");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaInicio vi = new VentanaInicio();
				vi.setVisible(true);
			}
		});

		menuUsuarios.add(menuItem);
		panelSuperiorS.add(menu);

		// Panel inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE);
		panelInferior.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelInferior, BorderLayout.SOUTH);

		JPanel panelInferiorCentral = new JPanel();
		panelInferiorCentral.setBackground(Color.WHITE);
		panelInferiorCentral.setLayout(new GridLayout(1, 1));
		panelInferior.add(panelInferiorCentral, BorderLayout.CENTER);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {

			@Override
			// sao
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
					/*String u_anioD = (String) cbAnioDesde.getSelectedItem();
					if (u_anioD == "Anio desde") {
						sql = sql + "ANIO BETWEEN 1950 AND ";
					} else {
						sql = sql + "ANIO BETWEEN " + u_anioD + " AND ";
					}
					String u_anioH = (String) cbAnioHasta.getSelectedItem();
					if (u_anioH == "Anio hasta") {
						sql = sql + "ANIO = " + anio + " AND ";
					} else {
						sql = sql + " " + u_anioH + " AND ";
					}
					String u_precioD = (String) cbPrecioDesde.getSelectedItem();
					if (u_precioD == "Precio desde") {
						sql = sql + "PRECIO BETWEEN 0 AND ";
					} else {
						sql = sql + "PRECIO BETWEEN " + u_precioD + " AND ";
					}
					String u_precioH = (String) cbPrecioHasta.getSelectedItem();
					if (u_precioH == "Precio hasta") {
						sql = sql + "PRECIO = 1000000 AND ";
					} else {
						sql = sql + " " + u_precioH + " AND ";
					}
					String u_kmsD = (String) cbKmsDesde.getSelectedItem();
					if (u_kmsD == "Kms desde") {
						sql = sql + "KILOMETROS BETWEEN 0 AND ";
					} else {
						sql = sql + "KILOMETROS BETWEEN " + u_kmsD + " AND ";
					}
					String u_kmsH = (String) cbKmsHasta.getSelectedItem();
					if (u_kmsH == "Kms hasta") {
						sql = sql + "KILOMETROS = 1000000 AND ";
					} else {
						sql = sql + " " + u_kmsH + " AND ";
					}*/
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

				/*
				 * try { Connection con = GestorBD.getConexion(); String sql =
				 * "SELECT * FROM vehiculos WHERE MARCA = ? AND MODELO = ? "; PreparedStatement
				 * ps = con.prepareStatement(sql); String u_marca =
				 * (String)cbMarca.getSelectedItem(); if (u_marca == "Marca") {u_marca = "*";};
				 * String u_modelo = (String)cbModelo.getSelectedItem(); if (u_modelo ==
				 * "Modelo") {u_modelo = "*";}; ps.setString(1, u_marca); ps.setString(2,
				 * u_modelo); ResultSet rs = ps.executeQuery(); if (rs.next()) {
				 * rs.beforeFirst(); while(rs.next()) {
				 * System.out.print(rs.getInt("IDVEHICULOS") + ", ");
				 * System.out.print(rs.getString("MARCA") + ", ");
				 * System.out.println(rs.getString("MODELO")); } } else
				 * {System.out.println("LO SIENTO CEPORRO, NO HAY");} } catch (SQLException ex)
				 * { ex.printStackTrace(); }
				 */

				/*
				 * Object[] parametros = new Object[15]; for (int i = 0; i<parametros.length;
				 * i++) { for(Component component : panelCentralContenidos.getComponents()) {
				 * if(component instanceof JComboBox<?>) { JComboBox<?> combo =
				 * (JComboBox<?>)component; Object value = combo.getSelectedIndex() > 0 ?
				 * combo.getSelectedItem() : null; parametros[i] = value; } }
				 * 
				 * } for(Object param : parametros) { System.out.println(param); }
				 * 
				 * Component[] componentes = panelCentralContenidos.getComponents(); for (int i
				 * = 0; i < componentes.length; i++) { if(componentes[i] instanceof JComboBox) {
				 * String seleccionado = ((JComboBox<String>)
				 * componentes[i]).getSelectedItem().toString();
				 * 
				 * }
				 * 
				 * }
				 */
				/*
				 * String sentencia = "SELECT * FROM vehiculos WHERE MARCA = '" + "?" + "' AND "
				 * + "MODELO = '" + "?" + "' AND CARROCERIA = '" + "?" + "' AND COMBUSTIBLE = '"
				 * + "?" + "' AND ANIO BETWEEN " + "?" + " AND " + "?" + " AND PRECIO BETWEEN "
				 * + "?" + " AND " + "?" + " AND KILOMETROS BETWEEN " + "?" + " AND " + "?" +
				 * " AND POTENCIA = " + "?" + " AND TRANSMISION = '" + "?" + "' AND NPLAZAS = "
				 * + "?" + " AND NPUERTAS = " + "?";
				 * 
				 * con = new GestorBD(); conn = con.getConexion(); try {
				 * 
				 * st = conn.prepareStatement(sentencia); ResultSet rs =
				 * st.executeQuery(sentencia); st.setString(1, (String)
				 * cbMarca.getSelectedItem()); if (rs.next()) { st.setString(2, (String)
				 * cbModelo.getSelectedItem()); st.setString(3, (String)
				 * cbCarroceria.getSelectedItem()); st.setString(4, (String)
				 * cbCombustible.getSelectedItem()); st.setString(5, (String)
				 * cbAnioDesde.getSelectedItem()); st.setString(6, (String)
				 * cbAnioHasta.getSelectedItem()); st.setString(7, (String)
				 * cbPrecioDesde.getSelectedItem()); st.setString(8, (String)
				 * cbPrecioHasta.getSelectedItem()); st.setString(9, (String)
				 * cbKmsDesde.getSelectedItem()); st.setString(10, (String)
				 * cbKmsHasta.getSelectedItem()); st.setString(11, (String)
				 * cbPotencia.getSelectedItem()); st.setString(12, (String)
				 * cbTransmision.getSelectedItem()); st.setString(13, (String)
				 * cbNPlazas.getSelectedItem()); st.setString(14, (String)
				 * cbNPuertas.getSelectedItem()); System.out.println(sentencia);
				 * 
				 * }
				 * 
				 * } catch (SQLException ex) { ex.printStackTrace(); }
				 * 
				 * VentanaResultados vr = new VentanaResultados(); vr.setVisible(true);
				 */
			}
		});

		panelInferiorCentral.add(btnBuscar);

		btnFiltrosAvanzados = new JButton("Filtros Avanzados");
		btnFiltrosAvanzados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaPrograma v = new VentanaPrograma(u);
				v.setVisible(true);
				setVisible(false);

			}
		});

		panelInferiorCentral.add(btnFiltrosAvanzados);

		btnVender = new JButton("Vender");
		btnVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaAddVehiculo v = new VentanaAddVehiculo(u);
				v.setVisible(true);
				setVisible(false);

			}
		});

		panelInferiorCentral.add(btnVender);

		panelCentralContenidos.add(cbMarca);
		panelCentralContenidos.add(cbModelo);
		panelCentralContenidos.add(cbAnio);
		panelCentralContenidos.add(cbPrecio);

		leeMarcas();
		leeModelos();

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
		VentanaProgramaFs v = new VentanaProgramaFs(null);
		v.setVisible(true);
	}

}
