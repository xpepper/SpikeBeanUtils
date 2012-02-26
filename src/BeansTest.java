import org.junit.Test;

import static org.junit.Assert.*;

public class BeansTest {
	@Test
	public void testBeans() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) { 

			MyBean source = new MyBean("piero", "di bello", "via cadore 45");
			YetAnotherBean target = new YetAnotherBean(null, null, null);

			new Beans(source, target, Beans.LEVEL_ARREST_NEVER);

			assertEquals(source.getName(), target.getName());
			assertEquals(source.getSurname(), target.getSurname());
		}
		long middle = System.currentTimeMillis();
		
		for (int i = 0; i < 100000; i++) { 

			MyBean source = new MyBean("piero", "di bello", "via cadore 45");
			YetAnotherBean target = new YetAnotherBean(null, null, null);

			new com.intesasanpaolo.fvc20.common.utils.Beans(source, target, Beans.LEVEL_ARREST_NEVER);

			assertEquals(source.getName(), target.getName());
			assertEquals(source.getSurname(), target.getSurname());
		}		
		long end = System.currentTimeMillis();
		
		System.out.println("Prima");
		System.out.println(end-middle);
		
		System.out.println("Dopo");
		System.out.println(middle-start);		
	}

}