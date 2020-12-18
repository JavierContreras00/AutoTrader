package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import clasesBasicas.Usuario;

public class VentanaResultados extends JFrame {
	private JPanel panelContenidos;
	private JScrollPane sp;
	private JMenuBar menu;
	private JMenu menuUsuarios;
	private JMenuItem menuItem;
	public JPanel panelCentral;
	public JPanel panelCentralS;
	public JPanel panelCentralI;
	public JPanel panelCentralD;
	public JPanel panelCentralInf;
	
	public JLabel lbMarca;
	public JLabel lbModelo;
	public JLabel lbCarroceria;
	public JLabel lbCombustible;
	public JLabel lbPotencia;
	public JLabel lbAnio;
	public JLabel lbKms;
	public JLabel lbTrans;
	public JLabel lbPlazas;
	public JLabel lbPuertas;
	public JLabel lbPrecio;
	public JLabel lbImgPrincipal;
	

	private static final long serialVersionUID = -796803919854258034L;
	//crear dos constructores para las ventanas de los filtros diferentes

	public VentanaResultados(Usuario u, VentanaPrograma vp) {
		this.setTitle("VVS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 500);
		this.setMinimumSize(new Dimension(250, 250));
//Creacion de paneles
		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		panelContenidos.setLayout(new BorderLayout(15, 15));
		
		sp = new JScrollPane(panelContenidos);
		sp.setBounds(panelContenidos.getBounds());
		
		

		// Panel superior
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(Color.WHITE);
		panelSuperior.setLayout(new GridLayout(1,1));
		panelContenidos.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelSuperiorS = new JPanel();
		panelSuperiorS.setBackground(Color.WHITE);
		panelSuperiorS.setLayout(new GridLayout(1, 2));
		panelSuperior.add(panelSuperiorS, BorderLayout.NORTH);
		
		JLabel logo = new JLabel(new ImageIcon("src/Q5.jpg"));
		panelSuperiorS.add(logo);

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
		
		//Panel Central (Toda la info de cada vehiculo de la busqueda)
		 panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BorderLayout());
		panelContenidos.add(panelCentral, BorderLayout.CENTER);
		
		 panelCentralS = new JPanel();
		panelCentralS.setBackground(Color.WHITE);
		panelCentralS.setLayout(new GridLayout(1,3));
		panelCentral.add(panelCentralS, BorderLayout.NORTH);

		 panelCentralI = new JPanel();
		panelCentralI.setBackground(Color.WHITE);
		panelCentralI.setLayout(new GridLayout(2,1));
		panelSuperior.add(panelCentralI, BorderLayout.WEST);
		
		
		 panelCentralD = new JPanel();
		panelCentralD.setBackground(Color.WHITE);
		panelCentralD.setLayout(new GridLayout(7,1));
		panelCentral.add(panelCentralD, BorderLayout.EAST);
		
		 panelCentralInf = new JPanel();
		panelCentralInf.setBackground(Color.WHITE);
		panelCentralInf.setLayout(new GridLayout(1,1));
		panelCentral.add(panelCentralInf, BorderLayout.SOUTH);
		
		

	}
	
	public JPanel crearPanel(VentanaPrograma vp) {
		lbMarca = new JLabel(vp.cbMarca.getSelectedItem().toString());
		panelCentralD.add(lbMarca);
		
		
		
		
		return panelCentral;
	}

}
