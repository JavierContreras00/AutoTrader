package clasesBasicas;

public class Hash {

	/**
	 * Clase que permite encriptar una contraseña mediante un hash
	 * 
	 * @param txt      contraseña a cifrar
	 * @param hashType tipo de hash
	 * @return
	 */

	/**
	 * 
	 * @param txt      tipo de texto
	 * @param hashType tipo de hash
	 * @return devuelve un hash a partir de un tipo y un texto
	 */
	public static String getHash(String txt, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
			byte[] array = md.digest(txt.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param txt texto a cirfrar
	 * @return devuelve un hash MD5 a partir del texto
	 */
	public static String md5(String txt) {
		return Hash.getHash(txt, "MD5");
	}

	/**
	 * 
	 * @param txt texto a cifrar
	 * @return devuelve un hash SHA1 a partir del texto
	 */
	public static String sha1(String txt) {
		return Hash.getHash(txt, "SHA1");
	}

}
