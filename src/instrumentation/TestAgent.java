package instrumentation;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class TestAgent {
	
	public static void premain(String args, Instrumentation instrumentation) {
		
		System.out.println("TestAgent running!");
				
		instrumentation.addTransformer(new ClassFileTransformer() {

			@Override
			public byte[] transform(Module module,
									ClassLoader loader,
									String name,
									Class<?> typeIfLoaded,
									ProtectionDomain domain,
									byte[] buffer) {
								
//				System.out.println("Class was loaded: " + name);
//				System.out.println("Loader used: " + loader + "\n");
				
				
				
				return null;
			}
						
		});

	}
}
