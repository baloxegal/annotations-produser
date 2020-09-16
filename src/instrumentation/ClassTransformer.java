package instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class ClassTransformer {
	
	public static void transform(Class<?> cl) throws NotFoundException {
		
		System.out.println("STARTING CLASS TRANSFORMATION");
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.get(cl.getName());
				
		System.out.println("ENDING CLASS TRANSFORMATION");
	}
}
