package instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.CtField;

import java.lang.reflect.Modifier;

import annotations.ca.AddConstructor;

import javassist.NotFoundException;
import javassist.CannotCompileException;

public class ClassTransformer {
	
	public static Class<?> transform(String className) throws NotFoundException, CannotCompileException {
		
		System.out.println("STARTING CLASS TRANSFORMATION");
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.get(className);
		
		if(!cc.getPackageName().equalsIgnoreCase("domain") || cc.isInterface() || !cc.hasAnnotation(AddConstructor.class)){
			return cc.toClass();
		}
		
		CtConstructor cconsDef = cc.getDeclaredConstructor(null);
		cc.removeConstructor(cconsDef);
		
		CtConstructor cstructor = CtNewConstructor.defaultConstructor(cc);
			
		cstructor.setBody("this.age = Integer.valueOf(70);");
		cc.addConstructor(cstructor);
		
//		CtField[] cf = cc.getDeclaredFields();
//		String[] cfm = new String[cf.length];
//		CtClass[] cft = new CtClass[cf.length];
//		
//		for(int i = 0, j = 0; i < cf.length; i++) {
//			if(cf[i].getModifiers() == Modifier.PRIVATE) {
//				cfm[j] = cf[i].getName();
//				cft[j] = cf[i].getType();
//				j++;
//			}
//		}		

		CtConstructor ccons = CtNewConstructor.make("public Person(Integer age, Boolean isBuyer){this.age = age; this.isBuyer = isBuyer;}", cc);
		
//		CtConstructor ccons = CtNewConstructor.make("public " + cc.getSimpleName()
//								+ "(" + cft[0] + " " + cfm[0] + ", " + cft[1] + " " + cfm[1]
//								+ ") {this." + cfm[0] + " = " + cfm[0] + "; "
//								+ "this." + cfm[1] + " = " + cfm[1] + ";}", cc);
		
		cc.addConstructor(ccons);
		
		System.out.println("ENDING CLASS TRANSFORMATION");		
	
		return cc.toClass();
	}
}
