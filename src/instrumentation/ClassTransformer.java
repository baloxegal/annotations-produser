package instrumentation;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.NotFoundException;

public class ClassTransformer {
	
	public static Class<?> transform(String className) throws NotFoundException, CannotCompileException {
		
		System.out.println("STARTING CLASS TRANSFORMATION");
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.get(className);
				
		CtConstructor ccons = cc.getDeclaredConstructor(null);
		cc.removeConstructor(ccons);
		
		CtConstructor cstructor = CtNewConstructor.defaultConstructor(cc);
			
		cstructor.setBody("this.age = 50;");
		cc.addConstructor(cstructor);
		
		System.out.println("ENDING CLASS TRANSFORMATION");
	
		return cc.toClass();
	}
}
