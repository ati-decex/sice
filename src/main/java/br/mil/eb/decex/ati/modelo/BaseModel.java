package br.mil.eb.decex.ati.modelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe base para todos os modelos do sistema
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 *
 * @param <T> Tipo do identificador
 */
public abstract class BaseModel<T extends Serializable> {

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da obrigação
	 */			
	public abstract T getId();
	
	/**
	 * Realiza a clonagem do objeto
	 * @param obj objeto a ser clonado
	 * @return nova instancia do objeto clonado
	 */
	public static Object cloneSerializable(Serializable obj) {
		
		ObjectOutputStream out = null;  
		ObjectInputStream in = null;  
    
		try {  
			ByteArrayOutputStream bout = new ByteArrayOutputStream();  
			out = new ObjectOutputStream(bout);  
                
			out.writeObject(obj);  
			out.close();  
                
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());  
			in = new ObjectInputStream(bin);              
			Object copy = in.readObject();  
    
			in.close();  
                
			return copy;  
		} catch (Exception ex) {  
			ex.printStackTrace();  
		} finally {  
			try {  
				if(out != null) {  
					out.close();  
				}  
                    
				if(in != null) {  
					in.close();  
				}  
			} catch (IOException ignore) {}  
		}  
		return null;  
	} 
	
}