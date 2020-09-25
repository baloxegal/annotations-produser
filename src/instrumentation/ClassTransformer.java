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
		
		CtField[] cf = cc.getDeclaredFields();
		String[] cfm = new String[cf.length];
				
		for(int i = 0, j = 0; i < cf.length; i++) {
			if(cf[i].getModifiers() == Modifier.PRIVATE) {
				cfm[j] = cf[i].getType().getSimpleName() + " " + cf[i].getName();
				j++;
			}
		}		

//		CtConstructor ccons = CtNewConstructor.make("public Person(Integer age, Boolean isBuyer){this.age = age; this.isBuyer = isBuyer;}", cc);
		
		String consString = "public " + cc.getSimpleName() + " (";
		
		for(int i = 0; i < cfm.length; i++) {
			consString += cfm[i];
			if(i != cfm.length - 1) {
				consString += ", ";
			}
			else {
				consString += ") {";
			}
		}
		
		for(int i = 0; i < cfm.length; i++) {
			consString += "this." + cfm[i].substring(cfm[i].indexOf(" ") + 1) + " = " + cfm[i].substring(cfm[i].indexOf(" ") + 1);
			if(i != cfm.length - 1) {
				consString += "; ";
			}
			else {
				consString += ";}";
			}
		}		
		
		CtConstructor ccons = CtNewConstructor.make(consString, cc);
		
		cc.addConstructor(ccons);
		
		System.out.println("ENDING CLASS TRANSFORMATION");		
	
		return cc.toClass();
	}
}
