package com.altabuild.AltabuildChat.ManagedBeans;

import com.altabuild.util.cryptordmslib.DMSCryptor;

public class CryptorChatBO{

	public CryptorChatBO(){

		dmsCryptor = getCryptor();
	}
	
	private DMSCryptor dmsCryptor = null;
	
	public DMSCryptor getCryptor(){

		if( dmsCryptor == null )
			dmsCryptor = new DMSCryptor();
		
		return dmsCryptor;
	}
	
	/*
	 * User Login
	 * User Password
	 * wsClientName
	 * wsClientSharedSecret
	 * email
	 * Certificate Password
	 */
	public String cryptCompTP(String data, int tamanho){
		return getCryptor().cryptCompTP_hex(data, tamanho);
	}
	public String decryptCompTP(String cryptData){
		return getCryptor().decryptCompTP_hex(cryptData);
	}

	
	/*
	 * Relica
	 * versao
	 * Database Login
	 * Database Password
	 */
	public String cryptCompTC(String data, int tamanho){
		return getCryptor().cryptCompTC(data, tamanho);
	}
	public String decryptCompTC(String cryptData){
		return getCryptor().decryptCompTC(cryptData);
	}
	
	/*
	 * License
	 */
	public String cryptF(String data){
		return getCryptor().cryptF(data);
	}
	public String decryptF(String cryptData){
		return getCryptor().decryptF(cryptData);
	}

	
	public byte[] encodeBase64ByteToByte(byte[] dado){
		return getCryptor().encodeBase64ByteToByte(dado);
	}
	public byte[] decodeBase64ByteToByte(byte[] dado){
		return getCryptor().decodeBase64ByteToByte(dado);
	}
	
	public String encodeBase64ByteToString(byte[] dado){
		return getCryptor().encodeBase64ByteToString(dado);
	}
	public String decodeBase64ByteToString(byte[] dado){
		return getCryptor().decodeBase64ByteToString(dado);
	}
	
	public byte[] encodeBase64StringToByte(String dado){
		return getCryptor().encodeBase64StringToByte(dado);
	}
	public byte[] decodeBase64StringToByte(String dado){
		return getCryptor().decodeBase64StringToByte(dado);
	}
	
	public byte[] encodeHexByteToByte(byte[] dado){
		return getCryptor().encodeHexByteToByte(dado);
	}
	public byte[] decodeHexByteToByte(byte[] dado){
		return getCryptor().decodeHexByteToByte(dado);
	}
}
