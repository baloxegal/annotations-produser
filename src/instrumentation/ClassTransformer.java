package instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtNewConstructor;

import javassist.NotFoundException;
import annotations.ca.AddConstructor;
import javassist.CannotCompileException;

public class ClassTransformer {
	
	public static Class<?> transform(String className) throws NotFoundException, CannotCompileException {
		
		System.out.println("STARTING CLASS TRANSFORMATION - Premain");
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.get(className);
		
//		if(!cc.getPackageName().equalsIgnoreCase("domain") || cc.isInterface() || !cc.hasAnnotation(AddConstructor.class)){
//			return cc.toClass();
//		}
		
//		CtConstructor ccons = cc.getDeclaredConstructor(null);
//		cc.removeConstructor(ccons);
//		
//		CtConstructor cstructor = CtNewConstructor.defaultConstructor(cc);
//			
//		cstructor.setBody("this.age = Integer.valueOf(50);");
//		cc.addConstructor(cstructor);
		
		CtField[] cf = cc.getDeclaredFields();
		CtField[] cfm = cc.getDeclaredFields();
		CtClass[] cft = new CtClass[cf.length];
		
		for(int i = 0, j = 0; i < cf.length; i++) {
			if(cf[i].getModifiers() != 1) {
				cfm[j] = cf[i];
				cft[j] = cf[i].getType();
				j++;
			}
		}
		
//		CtConstructor ccons = CtNewConstructor.make("public Person (Integer age, Boolean isBuyer)", cc);
//		ccons.setBody("this.age = age; this.isBuyer = isBuyer;");
		
		CtConstructor ccons = CtNewConstructor.make("Person(Integer age, Boolean isBuyer){this.age = age; this.isBuyer = isBuyer;}", cc);
		
		cc.addConstructor(ccons);
		
		System.out.println("ENDING CLASS TRANSFORMATION - Premain");		
	
		return cc.toClass();
	}
}
