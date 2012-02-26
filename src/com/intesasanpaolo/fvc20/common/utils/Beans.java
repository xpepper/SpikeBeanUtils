package com.intesasanpaolo.fvc20.common.utils;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * 
 * Copia properties tra classi
 * 
 * @author s.zonali
 *
 */
public class Beans 
{
	public static final int LEVEL_ARREST_BY_METHOD = 2;		// Mi fermo se trovo una get che non corrisponde a nessuna set
	public static final int LEVEL_ARREST_BY_TYPE = 1;			// Mi fermo se trovo un tipo che non si pu˜ mergiare
	public static final int LEVEL_ARREST_NEVER = 0;			// Non mi fermo ma salto sempre
	
	
	private LinkedList sourceListOfMehods;
	private LinkedList targetlistOfMehods;
	
	
	/**
	 * 
	 * Nel costruttore si fanno i controlli di tipo ed eventualmente si lancia una eccezione
	 * 
	 * @param source
	 * @param target
	 */
	public Beans(Object source, Object target, int level) throws Exception
	{
		// Ottengo tutti i get della source e le set della target... Controllo i tipi
		Method[] ms = source.getClass().getMethods();
		for(int i = 0; i<ms.length; i++)
		{
			if(ms[i].getName().indexOf("get") == 0)
			{
				// Metodo di get potenziale
				boolean foundCorrispondentSet = false;
				boolean foundCorrispondentType = true;
				String sourceName = ms[i].getName().substring(3); 
				
				Method[] msT = target.getClass().getMethods();
				for(int j = 0; j<msT.length; j++)
				{
					if(msT[j].getName().indexOf("set") == 0)
					{
						String targetName = msT[j].getName().substring(3);
						if(targetName.equals(sourceName))
						{
							// Stesso nome... Si passa al controllo dei tipi
							foundCorrispondentSet = true;
							foundCorrispondentType = false;		// Mi preparo al controllo
							
							Class cReturn = ms[i].getReturnType();
							Class[] cInput = msT[j].getParameterTypes();
							
							if(cInput.length != 1)
							{
								foundCorrispondentType = false;
							}
							else
							{
								Object oValue = ms[i].invoke(source, new Object[]{});
								
								try
								{
									msT[j].invoke(target, new Object[]{oValue});
									foundCorrispondentType = true;
								}
								catch(Exception e)
								{
									foundCorrispondentType = false;
								}
							}
						}
					}
				}
				
				if( (LEVEL_ARREST_BY_TYPE == level || LEVEL_ARREST_BY_METHOD == level) && !foundCorrispondentType)
					throw new Exception("Problema di tipi sul parametro: " + sourceName);
				if(LEVEL_ARREST_BY_METHOD == level && !foundCorrispondentSet)
					throw new Exception("Problema di corrispondenza nei nomi per il parametro: " + sourceName);
			}
		}
		
	}
	
	public void copy()
	{
		
	}
}