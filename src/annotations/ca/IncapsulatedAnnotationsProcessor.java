package annotations.ca;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("annotations.ca.Incapsulated")
public class IncapsulatedAnnotationsProcessor extends AbstractProcessor {
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		for(var e : roundEnv.getElementsAnnotatedWith(Incapsulated.class)) {
			
			Map<Element,ElementKind> enclosedElementsKinds = new HashMap<Element, ElementKind>();
			
			e.getEnclosedElements().forEach(ee -> enclosedElementsKinds.put(ee, ee.getKind()));
									
			if(!enclosedElementsKinds.containsValue(ElementKind.CONSTRUCTOR)) {
				processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This CLASS has no CONSTRUCTOR", e);	
			}
			
			int incrementConstructorParameters = 0;
									
			for(var ee : enclosedElementsKinds.keySet()) {
				
				if(enclosedElementsKinds.get(ee).equals(ElementKind.FIELD)) {
				
					if(ee.getModifiers().isEmpty()) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set modifier", ee);
					}
					
					if(ee.getModifiers().contains(Modifier.PUBLIC) && !ee.getModifiers().contains(Modifier.FINAL)) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is not FINAL and set to PUBLIC modifier", ee);
					}
					
					else if(ee.getModifiers().contains(Modifier.PRIVATE) || ee.getModifiers().contains(Modifier.PROTECTED)
							|| ee.getModifiers().isEmpty()){
						
						int incrementGet = 0, incrementSet = 0;
												
						for(var m : enclosedElementsKinds.keySet()) {
							if(enclosedElementsKinds.get(m).equals(ElementKind.METHOD)) {
								
								if(m.getModifiers().contains(Modifier.PRIVATE) &&
								(m.getSimpleName().toString().startsWith("set") || m.getSimpleName().toString().startsWith("get"))) {
									processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This METHOD is set to PRIVATE modifier", m);
								}
								
								if(m.getSimpleName().toString().equalsIgnoreCase("get".concat(ee.getSimpleName().toString()))){
									incrementGet++;
								}
								
								else if(m.getSimpleName().toString().equalsIgnoreCase("set".concat(ee.getSimpleName().toString()))){
									incrementSet++;
								}
							}
						}
						
						if(incrementGet == 0) {
							processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set GETTER", ee);
						}
						
						if(incrementSet == 0) {
							processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set SETTER", ee);
						}
					}
				}
				else if(enclosedElementsKinds.get(ee).equals(ElementKind.CONSTRUCTOR)) {
										
					processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops!" + ee.getClass().getSimpleName(), e);
					
					List<ElementKind> constructorElementsKind = new ArrayList<ElementKind>();
					ee.getEnclosedElements().forEach(ek -> constructorElementsKind.add(ek.getKind()));
										
					if(!constructorElementsKind.contains(ElementKind.PARAMETER)) {
						incrementConstructorParameters++;
						processingEnv.getMessager().printMessage(Kind.WARNING, "WOW!", e);
					}					
				}
				processingEnv.getMessager().printMessage(Kind.WARNING, "--!" + ee.getClass().getSimpleName(), e);
			}			
			if(incrementConstructorParameters == 0) {
				processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This CLASS has no CONSTRUCTOR with PARAMETERS", e);
			}
			else
				processingEnv.getMessager().printMessage(Kind.WARNING, "Booo!", e);
			
			processingEnv.getMessager().printMessage(Kind.WARNING, "++!", e);
		}
					
		return true;
	}
}
