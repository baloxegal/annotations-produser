package annotations.ca;

import java.util.Map;
import java.util.HashMap;
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

			for(var ee : enclosedElementsKinds.entrySet()) {
				
				if(ee.getValue().equals(ElementKind.FIELD)) {
				
					if(ee.getKey().getModifiers().isEmpty()) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set modifier", ee.getKey());
					}
					
					if(ee.getKey().getModifiers().contains(Modifier.PUBLIC) && !ee.getKey().getModifiers().contains(Modifier.STATIC)) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is not FINAL or STATIC and set to PUBLIC modifier", ee.getKey());
					}
					
					else if(ee.getKey().getModifiers().contains(Modifier.PRIVATE) || ee.getKey().getModifiers().contains(Modifier.PROTECTED)
							|| ee.getKey().getModifiers().isEmpty()){
						
						int incrementGet = 0, incrementSet = 0;
												
						for(var m : enclosedElementsKinds.keySet()) {
							if(enclosedElementsKinds.get(m).equals(ElementKind.METHOD)) {
								
								if(m.getModifiers().contains(Modifier.PRIVATE) &&
								(m.getSimpleName().toString().startsWith("set") || m.getSimpleName().toString().startsWith("get"))) {
									processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This METHOD is set to PRIVATE modifier", m);
								}
								
								if(m.getSimpleName().toString().equalsIgnoreCase("get".concat(ee.getKey().getSimpleName().toString()))){
									incrementGet++;
								}
								
								else if(m.getSimpleName().toString().equalsIgnoreCase("set".concat(ee.getKey().getSimpleName().toString()))){
									incrementSet++;
								}
							}
						}
						
						if(incrementGet == 0) {
							processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set GETTER", ee.getKey());
						}
						
						if(incrementSet == 0) {
							processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set SETTER", ee.getKey());
						}
					}
				}
			}
		}
					
		return true;
	}
}
