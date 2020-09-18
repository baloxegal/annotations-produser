package instrumentation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

public class ClassTransformer {
	
	public static void transform(Class<?> cl) throws NotFoundException, CannotCompileException {
		
		System.out.println("STARTING CLASS TRANSFORMATION");
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.get(cl.getName());
		
		CtConstructor ccons = cc.getDeclaredConstructor(null);
		
		String x = "777";
		
		
		ccons.setBody("name = Vasea");
		
		
		
		
		
//		List <?> cparams = Arrays.asList(ccons).stream().filter(cons -> {
//					try {
//						return cons.getParameterTypes().length != 0;
//					} catch (NotFoundException e2) {
//						e2.printStackTrace();
//					}
//					return false;
//					})
//						   .filter(cons -> {
//							try {
//								return Arrays.asList(cons.getParameterTypes()).stream().map(p -> {
//									try {
//										return p.toClass();
//									} catch (CannotCompileException e1) {
//										e1.printStackTrace();
//									}
//									return null;
//								}).collect(Collectors.toList())
//								   .containsAll(Arrays.asList(cc.getDeclaredFields()).parallelStream().map(f -> {
//									try {
//										return f.getType().toClass();
//									} catch (CannotCompileException e) {
//										e.printStackTrace();
//									} catch (NotFoundException e) {
//										e.printStackTrace();
//									}
//									return null;
//								}).collect(Collectors.toList())) == true;
//							} catch (NotFoundException e) {
//								e.printStackTrace();
//							}
//							return false;
//						}).collect(Collectors.toList());
//		
//		if(cparams.size() == 0) {
//			System.out.println("Super!");
//		}

		System.out.println("ENDING CLASS TRANSFORMATION");
	}
}
