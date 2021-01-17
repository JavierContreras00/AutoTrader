package ventanas;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bbdd.GestorBD;
import clasesBasicas.Hash;
import clasesBasicas.TextPrompt;
import clasesBasicas.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

public class VentanaInicio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8612972599187772531L;

	public String emailVP;

	public JTextField tfEmail;
	private JPasswordField pfContrasenia;
	private JLabel lbEmail;
	private JLabel lblContrasenia;
	private JPanel panelContenidos;
	private final JButton btnIniciarSesion;
	private final JButton btnRegistrarse;
	private TextPrompt ph;
	private JCheckBox cb;
	private JLabel lb;
	public Usuario u;
	private JMenu menuUsuario;

	public VentanaInicio() {
		this.setTitle("AutoTrader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 250);
		this.setMinimumSize(new Dimension(250, 250));

		cb = new JCheckBox("Recordar usuario");
		cb.setBackground(Color.WHITE);

		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		panelContenidos.setLayout(new BorderLayout(15, 15));

//Panel derecho
		JPanel panelDer = new JPanel();
		panelDer.setBackground(Color.WHITE);
		panelDer.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelDer, BorderLayout.EAST);

		// Panel derecho inferior
		JPanel panelDerInf = new JPanel();
		panelDerInf.setBackground(Color.WHITE);
		panelDerInf.setLayout(new GridLayout(3, 1, 0, 5));
		panelDer.add(panelDerInf, BorderLayout.SOUTH);

		btnIniciarSesion = new JButton("Iniciar sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				GestorBD modSql = new GestorBD();
				Usuario mod = new Usuario();
				pfContrasenia.enableInputMethods(true);
				String contraseniaCifrada = String.valueOf(pfContrasenia.getPassword());

				if (!tfEmail.getText().equals("") && !pfContrasenia.equals("")) {
					String nuevaContrasenia = Hash.sha1(contraseniaCifrada);
					mod.setEmail(tfEmail.getText());
					mod.setContrasenia(nuevaContrasenia);

					if (modSql.iniciarSesion(mod)) {
						u = new Usuario(mod.getIdusuarios(), mod.getNick(), mod.getNombre(), mod.getApellido(),
								mod.getEmail(), mod.getContrasenia());
						mod.getNombre();
						if (cb.isSelected()) {
							Properties properties = new Properties();
							if (tfEmail.getText() != null) {
								properties.setProperty("Email", tfEmail.getText().toString());
							}
							if (pfContrasenia.getPassword() != null) {
								properties.setProperty("Password", String.valueOf(pfContrasenia.getPassword()));

							}
							try {
								properties.storeToXML(new FileOutputStream("data/Usuarios.xml"),
										"Propiedades de registro");
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							modSql.dissconect();
							VentanaPrograma vp = new VentanaPrograma(u);
							vp.setVisible(true);
							setVisible(false);

						} else {

							VentanaProgramaFs v = new VentanaProgramaFs(u);
							v.setVisible(true);
							setVisible(false);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Usuario no encontrado");
					}

				} else {
					JOptionPane.showMessageDialog(null, "No debes dejar campos vacios");
				}

			}
		});
		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VentanaRegistrarse vr = new VentanaRegistrarse();
				vr.setVisible(true);
				dispose();

			}
		});

		lb = new JLabel("");
		panelDerInf.add(btnIniciarSesion);
		panelDerInf.add(btnRegistrarse);
		panelDerInf.add(lb);

//Panel Central(Usuario y contrase�a) solo los texfield y labels
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelCentral, BorderLayout.CENTER);

		// Panel central inferior
		JPanel panelCentralInf = new JPanel();
		panelCentralInf.setBackground(Color.WHITE);
		panelCentralInf.setLayout(new GridLayout(3, 2, 5, 5));
		panelCentral.add(panelCentralInf, BorderLayout.SOUTH);

		lbEmail = new JLabel("Email: ");
		lblContrasenia = new JLabel("Contrase\u00F1a:");

		tfEmail = new JTextField();
		ph = new TextPrompt("email", tfEmail);

		pfContrasenia = new JPasswordField();
		ph = new TextPrompt("contrasenia", pfContrasenia);

		panelCentralInf.add(lbEmail);
		panelCentralInf.add(tfEmail);
		panelCentralInf.add(lblContrasenia);
		panelCentralInf.add(pfContrasenia);
		panelCentralInf.add(lb);
		panelCentralInf.add(cb);

		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				Properties properties = new Properties();
				try {
					properties.loadFromXML(new FileInputStream("data/Usuarios.xml"));
					String email = properties.getProperty("Email");
					String password = properties.getProperty("Password");
					VentanaInicio.this.tfEmail.setText(email);
					pfContrasenia.setText(password);

				} catch (FileNotFoundException ex1) {
					ex1.printStackTrace();
					// TODO: handle exception
				} catch (InvalidPropertiesFormatException ex2) {
					// TODO Auto-generated catch block
					ex2.printStackTrace();
				} catch (IOException ex3) {
					// TODO Auto-generated catch block
					ex3.printStackTrace();
				}

			}

		});

	}

}
