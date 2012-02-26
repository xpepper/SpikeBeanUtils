import java.lang.reflect.Method;

public class Beans {
	public static final int LEVEL_ARREST_BY_METHOD = 2; // Mi fermo se trovo una get che non corrisponde a nessuna set
	public static final int LEVEL_ARREST_BY_TYPE = 1; // Mi fermo se trovo un tipo che non si può mergiare
	public static final int LEVEL_ARREST_NEVER = 0; // Non mi fermo ma salto sempre

	/**
	 * Nel costruttore si fanno i controlli di tipo ed eventualmente si lancia una eccezione
	 * 
	 * @param source
	 * @param target
	 */
	public Beans(Object source, Object target, int level) throws Exception {
		// Ottengo tutti i get della source e le set della target... Controllo i tipi
		Method[] ms = source.getClass().getMethods();  // <== perch devo prendermi tutti i metodi? Non mi bastano i suoi getter?
		Method[] msT = target.getClass().getMethods(); // <== perch devo prendermi tutti i metodi? Non mi bastano i suoi setter? e perch questa variabile era assegnata ogni volta dentro il primo ciclo?
		/* Si deve riscrivere usando i metodi di utilitˆ di Spring: BeanUtils, PropertyDescriptor che fanno esattamente quello che si cerca di fare nei due cicli sottostanti:
		 * BeanUtils consente di estrarre da una classe solo i suoi getter o i suoi setter, evitando quindi gli "if (ms[i].getName().indexOf("get") == 0)"
		 * PropertyDesciptor mette a disposizione diversi metodi per avere getter e setter ed estrarre il nome della property, senza usare pi quindi il macchinoso "String targetName = msT[j].getName().substring(3);"
		 */
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getName().indexOf("get") == 0) {
				// Metodo di get potenziale
				boolean foundCorrispondentSet = false;
				boolean foundCorrispondentType = true;
				String sourceName = ms[i].getName().substring(3);
				
				for (int j = 0; j < msT.length; j++) {
					if (msT[j].getName().indexOf("set") == 0) {
						String targetName = msT[j].getName().substring(3);
						if (targetName.equals(sourceName)) {
							// Stesso nome... Si passa al controllo dei tipi
							foundCorrispondentSet = true;
							foundCorrispondentType = false; // Mi preparo al controllo

							if (msT[j].getParameterTypes().length != 1) {
								foundCorrispondentType = false;
							} else {
								Object oValue = ms[i].invoke(source, new Object[] {});

								try {
									msT[j].invoke(target, new Object[] { oValue });
									foundCorrispondentType = true;
								} catch (Exception e) {
									foundCorrispondentType = false;
								}
							}
						}
					}
				}

				if ((LEVEL_ARREST_BY_TYPE == level || LEVEL_ARREST_BY_METHOD == level) && !foundCorrispondentType)
					throw new Exception("Problema di tipi sul parametro: " + sourceName);
				if (LEVEL_ARREST_BY_METHOD == level && !foundCorrispondentSet)
					throw new Exception("Problema di corrispondenza nei nomi per il parametro: " + sourceName);
			}
		}

	}

}
