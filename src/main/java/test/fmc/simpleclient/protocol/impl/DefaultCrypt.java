package test.fmc.simpleclient.protocol.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.fmc.simpleclient.protocol.CryptAlgoritm;
import test.fmc.simpleclient.utils.ContextUtil;

public class DefaultCrypt implements CryptAlgoritm {
	private static Log _log = LogFactory.getLog(DefaultCrypt.class);
	private String encoding = "UTF-8";
	@Resource(name="SimpleClient/remote-password")
	private String password;
	private Cipher chr;
	private byte[] salt;
	private boolean init = false;

	public DefaultCrypt() {
		
		
	}

	// Инициализация
	public void initCipher() throws Exception {
		try {
			byte[] pas = password.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			char[] pass = new String(Hex.encodeHex(md.digest(pas))).toCharArray();
	
			PBEKeySpec pbeKeySpec = new PBEKeySpec(pass);
			SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(pbeKeySpec);
			chr = Cipher.getInstance("PBEWithMD5AndDES");
			salt = new byte[8];
			Random rnd = new Random();
			rnd.nextBytes(salt);
			int iterations = 128;
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt,
					iterations);
			chr.init(Cipher.ENCRYPT_MODE, pbeKey, parameterSpec);
			init = true;
		} catch (InvalidAlgorithmParameterException ex) {
			_log.error(ex);
			throw ex;
		} catch (InvalidKeyException ex) {
			_log.error(ex);
			throw ex;
		} catch (NoSuchPaddingException ex) {
			_log.error(ex);
			throw ex;
		} catch (InvalidKeySpecException ex) {
			_log.error(ex);
			throw ex;
		} catch (NoSuchAlgorithmException ex) {
			_log.error(ex);
			throw ex;
		} 
	}

	/**
	 * Шифрование
	 * 
	 * @param command
	 *            Подготовленная комманда
	 * @return Зашифрованная комманда
	 */
	public String crypt(String command) throws Exception {
		if(!init)
			initCipher();
		if (chr == null)
			return "";
		try {
			byte[] encoded = chr.doFinal(command.getBytes(encoding));
			StringBuilder hexString = new StringBuilder();

			for (int i = 0; i < 8; i++) {
				String hex = Integer.toHexString(0xFF & salt[i]);
				if (hex.length() == 1)
					hex = "0" + hex;
				hexString.append(hex);
			}

			for (int i = 0; i < encoded.length; i++) {
				String hex = Integer.toHexString(0xFF & encoded[i]);
				if (hex.length() == 1)
					hex = "0" + hex;
				hexString.append(hex);
			}

			String salts = hexString.toString();
			byte[] bsalt = new byte[8];
			for (int i = 0; i < 16; i += 2) {
				String sub = salts.substring(i, i + 2);
				bsalt[i / 2] = (byte) Integer.parseInt(sub, 16);
			}
			return hexString.toString();
		} catch (IllegalBlockSizeException ex) {
			_log.error(ex);
			throw ex;
		} catch (BadPaddingException ex) {
			_log.error(ex);
			throw ex;
		} catch (UnsupportedEncodingException ex) {
			_log.error(ex);
			throw ex;
		}
	}

}
