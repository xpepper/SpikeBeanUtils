public class IndexOfSpike {
	public static void main(String args[]) {

		
		long value1 = System.currentTimeMillis();
		for (long i = 0; i < 1000000000; i++) {
			"getAbcdasdasdasd".startsWith("get");
		}
		long value2 = System.currentTimeMillis();
		System.out.println("startsWith ci mette ms:");		
		System.out.println(value2 - value1);
		
		
		long value3 = System.currentTimeMillis();
		for (long i = 0; i < 1000000000; i++) {
			boolean b = "getAbcdasdasdasd".indexOf("get") == 0;
			
		}
		long value4 = System.currentTimeMillis();
		System.out.println("indexOf ci mette ms:");
		System.out.println(value4 - value3);

	}
}
