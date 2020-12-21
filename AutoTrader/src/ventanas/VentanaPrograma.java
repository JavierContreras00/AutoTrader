package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ImageCapabilities;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bbdd.GestorBD;
import clasesBasicas.Coche;
import clasesBasicas.Marca;
import clasesBasicas.Render;
import clasesBasicas.Usuario;
import interfaces.LeeDeFichero;

import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.event.ItemEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPrograma extends JFrame implements LeeDeFichero {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5046619034480912034L;
//VARIABLES PARA LA BUSQUEDA DE VEHICULOS
	private HashMap<String, Marca> mapaMarcas = new HashMap<>();
	public VentanaPrograma vp;

	private JPanel panelContenidos;
	private JButton btnBuscar;
	private JButton btnVolver;
	private JButton btnVender;
	private JMenuBar menu;
	private JMenu menuUsuarios;
	private JMenuItem menuItem;

	// ComboBox y sus etquetas

	JComboBox<String> cbMarca;
	private String opcionNoSeleccionableMarca = "Marca";

	public JComboBox<String> cbModelo; /// Hasta quen no se eliga la marca va a estar en enable = false
	private String opcionNoSeleccionableModelo = "Modelo";

	public JComboBox<String> cbCombustible;
	private String opcionNoSeleccionableCombustible = "Tipo Combustible";

	public JComboBox<String> cbCarroceria;
	private String opcionNoSeleccionableCarroceria = "Carroceria";

	public JComboBox<String> cbAnioDesde;
	private String opcionNoSeleccionableAnioDesde = "Anio desde";

	public JComboBox<String> cbAnioHasta;
	private String opcionNoSeleccionableAnioHasta = "Anio hasta";

	public JComboBox<String> cbPrecioDesde;
	private String opcionNoSeleccionablePrecioDesde = "Precio desde";

	public JComboBox<String> cbPrecioHasta;
	private String opcionNoSeleccionablePrecioHasta = "Precio hasta";

	public JComboBox<String> cbKmsDesde;
	private String opcionNoSeleccionableKmsDesde = "Kms desde";

	public JComboBox<String> cbKmsHasta;
	private String opcionNoSeleccionableKmsHasta = "Kms hasta";

	public JComboBox<String> cbPotencia;
	private String opcionNoSeleccionablePotencia = "Potencia";

	public JComboBox<String> cbTransmision;
	private String opcionNoSeleccionableTransmision = "Transmision";

	public JComboBox<String> cbNPuertas;
	private String opcionNoSeleccionableNPuertas = "Puertas";

	public JComboBox<String> cbNPlazas;
	private String opcionNoSeleccionableNPlazas = "Plazas";

//VARIABLES DE RESULTADO DE BUSQUEDA

	public JPanel panelCentralR;
	public JPanel panelCentralS;
	public JPanel panelCentralI;
	public JPanel panelCentralD;
	public JPanel panelCentralInf;

	public VentanaPrograma(Usuario u) {
		this.setTitle("VVS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 300);
		this.setMinimumSize(new Dimension(250, 250));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("img/vvs.png"));

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
		panelCentralContenidos.setLayout(new GridLayout(4, 4, 10, 10));
		panelCentral.add(panelCentralContenidos, BorderLayout.NORTH);

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
		cbCombustible = new JComboBox<String>();
		cbCombustible.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableCombustible.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbCombustible.addItem(opcionNoSeleccionableCombustible);
		cbCombustible.addItem("Gasolina");
		cbCombustible.addItem("Diesel");
		cbCombustible.addItem("Electrico");

		cbCarroceria = new JComboBox<String>();
		cbCarroceria.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableCarroceria.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbCarroceria.addItem(opcionNoSeleccionableCarroceria);
		cbCarroceria.addItem("Coche pequeño");
		cbCarroceria.addItem("Cabrio");
		cbCarroceria.addItem("Coupe");
		cbCarroceria.addItem("4x4/Pickup");
		cbCarroceria.addItem("Sedan");
		cbCarroceria.addItem("Familiar");
		cbCarroceria.addItem("Monovolumen");

		cbAnioDesde = new JComboBox<String>();
		cbAnioDesde.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableAnioDesde.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbAnioDesde.addItem(opcionNoSeleccionableAnioDesde);

		cbAnioHasta = new JComboBox<String>();
		cbAnioHasta.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableAnioHasta.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbAnioHasta.addItem(opcionNoSeleccionableAnioHasta);
		Calendar cal = Calendar.getInstance();
		int anio = cal.get(Calendar.YEAR);
		for (int i = 1950; i <= anio; i++) {
			cbAnioDesde.addItem(Integer.toString(i));
			cbAnioHasta.addItem(Integer.toString(i));

		}

		cbPrecioDesde = new JComboBox<String>();
		cbPrecioDesde.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionablePrecioDesde.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbPrecioDesde.addItem(opcionNoSeleccionablePrecioDesde);

		cbPrecioHasta = new JComboBox<String>();
		cbPrecioHasta.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionablePrecioHasta.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbPrecioHasta.addItem(opcionNoSeleccionablePrecioHasta);
		for (int i = 1000; i <= 1000000; i += 1000) {
			cbPrecioDesde.addItem(Integer.toString(i));
			cbPrecioHasta.addItem(Integer.toString(i));
		}

		cbKmsDesde = new JComboBox<String>();
		cbKmsDesde.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableKmsDesde.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbKmsDesde.addItem(opcionNoSeleccionableKmsDesde);

		cbKmsHasta = new JComboBox<String>();
		cbKmsHasta.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableKmsHasta.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbKmsHasta.addItem(opcionNoSeleccionableKmsHasta);
		for (int i = 0; i <= 1000000; i += 10000) {
			cbKmsDesde.addItem(Integer.toString(i));
			cbKmsHasta.addItem(Integer.toString(i));
		}

		cbPotencia = new JComboBox<String>();
		cbPotencia.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionablePotencia.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbPotencia.addItem(opcionNoSeleccionablePotencia);
		for (int i = 50; i <= 1500; i += 50) {
			cbPotencia.addItem(Integer.toString(i));
		}

		cbTransmision = new JComboBox<String>();
		cbTransmision.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableTransmision.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbTransmision.addItem(opcionNoSeleccionableTransmision);
		cbTransmision.addItem("Automatico");
		cbTransmision.addItem("Manual");
		cbTransmision.addItem("Semiautomatico");

		cbNPuertas = new JComboBox<String>();
		cbNPuertas.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableNPuertas.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbNPuertas.addItem(opcionNoSeleccionableNPuertas);
		cbNPuertas.addItem("3");
		cbNPuertas.addItem("5");
		cbNPuertas.addItem("7");

		cbNPlazas = new JComboBox<String>();
		cbNPlazas.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableNPlazas.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbNPlazas.addItem(opcionNoSeleccionableNPlazas);
		cbNPlazas.addItem("2");
		cbNPlazas.addItem("4");
		cbNPlazas.addItem("5");
		cbNPlazas.addItem("7");

		panelCentralContenidos.add(cbMarca);
		panelCentralContenidos.add(cbModelo);
		panelCentralContenidos.add(cbCombustible);
		panelCentralContenidos.add(cbCarroceria);
		panelCentralContenidos.add(cbAnioDesde);
		panelCentralContenidos.add(cbAnioHasta);
		panelCentralContenidos.add(cbPrecioDesde);
		panelCentralContenidos.add(cbPrecioHasta);
		panelCentralContenidos.add(cbKmsDesde);
		panelCentralContenidos.add(cbKmsHasta);
		panelCentralContenidos.add(cbPotencia);
		panelCentralContenidos.add(cbTransmision);
		panelCentralContenidos.add(cbNPuertas);
		panelCentralContenidos.add(cbNPlazas);

//Panel superior
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(Color.WHITE);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelSuperiorDcha = new JPanel();
		panelSuperiorDcha.setBackground(Color.WHITE);
		panelSuperiorDcha.setLayout(new GridLayout(1, 2));
		panelSuperior.add(panelSuperiorDcha, BorderLayout.EAST);

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

		JMenuItem menuItem2 = new JMenuItem("Borrar cuenta");
		menuItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GestorBD bd = new GestorBD();
				int id = u.getIdusuarios();
				bd.borrarUsuario(u, id);
				dispose();
				VentanaInicio v = new VentanaInicio();
				v.setVisible(true);
			}
		});
		menuUsuarios.add(menuItem2);

		JMenuItem menuItem3 = new JMenuItem("Eliminar vehiculo");
		menuItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaVehiculosUsuario v = new VentanaVehiculosUsuario(u);
				v.setVisible(true);
				dispose();
			}
		});

		menuUsuarios.add(menuItem3);
		panelSuperiorDcha.add(menu);

		// Creacion de elementos de la ventana de busqueda

		// Panel Central (Toda la info de cada vehiculo de la busqueda)

//Panel inferior
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
			public void actionPerformed(ActionEvent e) {
				// GestorBD gestor = new GestorBD();
				// marca, modelo, carroceria, combustible, anio, precio, kilometros, potencia,
				// transmision, nplazas, npuertas ,imagen, email
				try {
					Connection con = GestorBD.getConexion();
					String sql = "SELECT * FROM vehiculos v, usuarios u WHERE v.idusuarios = u.idusuarios AND ";
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
					String u_anioD = (String) cbAnioDesde.getSelectedItem();
					if (u_anioD == "Anio desde") {
						sql = sql + "ANIO BETWEEN 1950 AND ";
					} else {
						sql = sql + "ANIO BETWEEN " + u_anioD + " AND ";
					}
					String u_anioH = (String) cbAnioHasta.getSelectedItem();
					if (u_anioH == "Anio hasta") {
						sql = sql + anio + " AND ";
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
						sql = sql + "1000000 AND ";
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
						sql = sql + "1000000 AND";
					} else {
						sql = sql + " " + u_kmsH + " AND ";
					}
					String u_potencia = (String) cbPotencia.getSelectedItem();

					if (u_potencia == "Potencia") {
						sql = sql + " POTENCIA LIKE '%' AND ";
					} else {
						sql = sql + " POTENIA = '" + u_potencia + "' AND";
					}
					String u_transmision = (String) cbTransmision.getSelectedItem();

					if (u_transmision == "Transmision") {
						sql = sql + " TRANSMISION LIKE '%' AND ";
					} else {
						sql = sql + " TRANSMISION = '" + u_transmision + "' AND";
					}
					String u_puertas = (String) cbNPuertas.getSelectedItem();

					if (u_puertas == "Puertas") {
						sql = sql + " NPUERTAS LIKE '%' AND ";
					} else {
						sql = sql + " NPUERTAS = '" + u_puertas + "' AND";
					}
					String u_plazas = (String) cbNPlazas.getSelectedItem();

					if (u_plazas == "Plazas") {
						sql = sql + " NPLAZAS LIKE '%'";
					} else {
						sql = sql + " NPLAZAS = '" + u_plazas + "'";
					}

					ResultSet rs = ps.executeQuery(sql);
					panelCentral.setVisible(false);
					setSize(1000,600);
					JPanel panelTabla = new JPanel();
					panelTabla.setBackground(Color.WHITE);
					panelTabla.setLayout(new BorderLayout(0, 0));
					panelContenidos.add(panelTabla, BorderLayout.CENTER);
					int size = 0;
					int num = 0;
					while (rs.next()) {
						num++;
					}
					rs.beforeFirst();
					Object[][] matriz = new Object[num][11];
					String[] columnas = { "Marca", "Modelo", "Carrocería", "Combustible", "Potencia", "Anio",
							"Kilometros", "Transmision", "Precio", "Imagen", "Vendedor" };
					DefaultTableModel model = new DefaultTableModel() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public Class<?> getColumnClass(int columnas) {
							switch (columnas) {
							case 9:
								return ImageIcon.class;
							default:
								return String.class;
							}
						}
						
					};
					model.setColumnIdentifiers(columnas);

					int i = 0;
					if (rs.next()) {

						rs.beforeFirst();
						size = size + 1;

						while (rs.next()) {

							String marca = rs.getString("MARCA");
							String modelo = rs.getString("MODELO");
							String carroceria = rs.getString("CARROCERIA");
							String combustible = rs.getString("COMBUSTIBLE");
							String potencia = rs.getString("POTENCIA");
							String anio = rs.getString("ANIO");
							String kms = rs.getString("KILOMETROS");
							String transmision = rs.getString("TRANSMISION");
							String precio = rs.getString("PRECIO") + "€";
							byte[] foto = rs.getBytes("IMAGEN");
							String usuario = rs.getString("email");

							matriz[i][0] = marca;
							matriz[i][1] = modelo;
							matriz[i][2] = carroceria;
							matriz[i][3] = combustible;
							matriz[i][4] = potencia;
							matriz[i][5] = anio;
							matriz[i][6] = kms;
							matriz[i][7] = transmision;
							matriz[i][8] = precio;
							matriz[i][10] = usuario;
							ImageIcon img = new ImageIcon();
							try {
								byte[] bi = foto;
								BufferedImage image = null;
								if (foto != null) {
									InputStream in = new ByteArrayInputStream(bi);
									image = ImageIO.read(in);
									img = new ImageIcon(image.getScaledInstance(300, 100, 0));

								}

							} catch (Exception e1) {
								e1.printStackTrace();

							}

							model.addRow(new Object[] { marca, modelo, carroceria, combustible, potencia, anio, kms,
									transmision, precio, img, usuario });

							i = i + 1;
						}
						JTable table = new JTable(model);
						
						table.setRowHeight(100);
						table.getColumnModel().getColumn(9).setPreferredWidth(300);

						table.setDefaultRenderer(Object.class, new Render());
						panelTabla.add(new JScrollPane(table));

					} else {
						JOptionPane.showMessageDialog(null,
								"No se encontro ningun vehiculo que coincida con su busqueda");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		});

		panelInferiorCentral.add(btnBuscar);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaProgramaFs v = new VentanaProgramaFs(u);
				v.setVisible(true);

			}
		});
		panelInferiorCentral.add(btnVolver);

		btnVender = new JButton("Vender");
		btnVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaAddVehiculo v = new VentanaAddVehiculo(u);
				v.setVisible(true);

			}
		});

		panelInferiorCentral.add(btnVender);

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

}