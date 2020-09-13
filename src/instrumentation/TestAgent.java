package instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
//import java.util.Arrays;
import java.security.ProtectionDomain;

public class TestAgent {
	public static void premain(String args, Instrumentation instrumentation) {
		
		System.out.println("TestAgent running!");
		
//		Arrays.asList(instrumentation.getAllLoadedClasses()).forEach(System.out :: println);
				

		
		instrumentation.addTransformer(new ClassFileTransformer() {

			@Override
			public byte[] transform(Module module,
									ClassLoader loader,
									String name,
									Class<?> typeIfLoaded,
									ProtectionDomain domain,
									byte[] buffer) {
				
				System.out.println(loader);
				
				return null;
			}
						
		});
						
		for(var c : instrumentation.getInitiatedClasses(new ClassLoader() {
		})) {
			System.out.println(c.getName());
		}
		
	}
}
