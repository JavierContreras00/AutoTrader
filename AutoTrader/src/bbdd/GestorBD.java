package bbdd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import clasesBasicas.Coche;
import clasesBasicas.Usuario;

public class GestorBD {

	private PreparedStatement ps = null;

	private static Connection con = null;

	private static boolean LOGGING = true;

	private static Logger logger = null;

	/**
	 * Establece una conexion con la base de datos
	 * 
	 * @return con Si todo vaa bien devuelve la conexion que ha creado con la base
	 *         de datos
	 */
	public static Connection getConexion() {
		try {
			// jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/autotrader?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "deusto");

			log(Level.INFO, "Base de datos conectada", null);

		} catch (ClassNotFoundException e) {
			log(Level.SEVERE, "No se ha podido conectar a la base de datos", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * Se desconecta de la base de datos
	 */
	public void dissconect() {
		try {
			if (con != null) {
				con.close();
			}
			log(Level.INFO, "Cierre de base de datos", null);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en cierre de base de datos", e);

		}
	}

	/**
	 * Añade en la base de datos un nuevo usuario desde la ventana de registro
	 * 
	 * @param u Llama a la clase usuario para poder utilizar los atríbutos de la
	 *          clase
	 * @return true si los datos son correctos, false si entra en el catch
	 * 
	 */
	public boolean registrar(Usuario u) {

		con = getConexion();

		String sql = "INSERT INTO usuarios (idusuarios,"
				+ "nick, nombre, apellido, email, contrasenia) VALUES(?,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, u.getIdusuarios());
			ps.setString(2, u.getNick());
			ps.setString(3, u.getNombre());
			ps.setString(4, u.getApellido());
			ps.setString(5, u.getEmail());
			ps.setString(6, u.getContrasenia());
			ps.execute();
			log(Level.INFO, "Usuario registrado", null);

			return true;

		} catch (SQLException e) {
			log(Level.SEVERE, "Error al insertar usuario", e);
			return false;
		}

	}

	/**
	 * Consulta a la base de datos si ya existe algún usuario introducido en la
	 * ventana de inicio de sesión
	 * 
	 * @param u LLama a la clase usuario para poder utilizar los atributos de la
	 *          clase
	 * @return true si todo va bien, false si salta al catch
	 */

	public boolean iniciarSesion(Usuario u) {
		con = getConexion();
		String sql = "SELECT idusuarios, nick, nombre, apellido, email, contrasenia FROM usuarios WHERE email = ? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, u.getEmail());

			ResultSet rs = ps.executeQuery();
			log(Level.INFO, "Buscando usuario en la base de datos", null);
			if (rs.next())
				if (u.getContrasenia().equals(rs.getString(6))) {
					u.setIdusuarios(rs.getInt(1));
					u.setNick(rs.getString(2));
					u.setNombre(rs.getString(3));
					u.setApellido(rs.getString(4));
					u.setEmail(rs.getString(5));
					u.setContrasenia(rs.getString(6));
					log(Level.INFO, "Usuario encontrado", null);

					return true;
				} else {
					log(Level.INFO, "Usuario no encontrado", null);

					return false;
				}

			return false;

		} catch (SQLException e) {
			log(Level.SEVERE, "Error al buscar usuario en la base de datos", e);
			return false;

		}

	}

	public int obtenerIdVehiculo() {
		con = getConexion();
		int idMax = 0;
		String sql = "SELECT MAX(IDVEHICULOS) AS  IDMAXIMO FROM VEHICULOS";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			log(Level.INFO, "Buscando id en la base de datos", null);
			if (rs.next()) {
				idMax = rs.getInt("IDMAXIMO");

				log(Level.INFO, "Id encontrado", null);

			}

		} catch (SQLException e) {
			log(Level.SEVERE, "Error al buscar el id en la base de datos", e);
		}
		return idMax + 1;

	}

	public boolean aniadirCoche(Coche c, Usuario u, File ficheroImg) {
		con = getConexion();
		String sql = "INSERT INTO vehiculos (IDVEHICULOS,MARCA, MODELO, CARROCERIA, COMBUSTIBLE, ANIO, PRECIO, KILOMETROS, POTENCIA, TRANSMISION, NPLAZAS, 	NPUERTAS,idusuarios, IMAGEN)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, c.getIdvehiculos());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getCarroceria());
			ps.setString(5, c.getCombustible());
			ps.setInt(6, c.getAnio());
			ps.setInt(7, c.getPrecio());
			ps.setLong(8, c.getKilometros());
			ps.setInt(9, c.getPotencia());
			ps.setString(10, c.getTransmision());
			ps.setInt(11, c.getnPlazas());
			ps.setInt(12, c.getnPuertas());
			ps.setInt(13, u.getIdusuarios());
			try {

				FileInputStream fis = new FileInputStream(ficheroImg);
				ps.setBinaryStream(14, fis, (int) ficheroImg.length());

			} catch (FileNotFoundException e) {
				log(Level.SEVERE, "No se ha encontrado la imagen", e);
			}

			ps.execute();
			log(Level.INFO, "Vehiculos insertado conrrectamente", null);
			return true;

		} catch (SQLException e) {
			log(Level.SEVERE, "Error al insertar coche en la base de datos", e);
			return false;
		}

	}

	public int borrarUsuario(Usuario u, int id) {
		try {
			con = getConexion();
			String sql = "DELETE FROM USUARIOS WHERE IDUSUARIOS =?";
			ps = con.prepareStatement(sql);
			id = u.getIdusuarios();
			ps.setInt(1, id);
			ps.execute();

		} catch (SQLException e) {
			log(Level.SEVERE, "No se ha podido eliminar el usuario de la bd", e);
		}

		return id;

	}

	private static void log(Level level, String msg, Throwable exception) {
		if (!LOGGING) {
			return;
		}
		if (logger == null) {
			logger = Logger.getLogger(GestorBD.class.getName());
			logger.setLevel(level.ALL);
		}
		if (exception == null) {
			logger.log(level, msg);
		} else {
			logger.log(level, msg, exception);
		}
	}

}
