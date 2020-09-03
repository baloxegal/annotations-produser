package annotations.ca;

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
			
			List<? extends Element> enclosedElements = e.getEnclosedElements();
						
			int incrementConstructor = 0;
			
			for(var ee : enclosedElements) {
				
				if(ee.getKind().equals(ElementKind.CONSTRUCTOR)){
					incrementConstructor++;
				}
				
				else if(ee.getKind().equals(ElementKind.FIELD)) {
					
					if(ee.getModifiers().isEmpty()) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD has no set modifier", ee);
					}
					
					if(ee.getModifiers().contains(Modifier.PUBLIC) && !ee.getModifiers().contains(Modifier.FINAL)) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is not FINAL and set to PUBLIC modifier", ee);
					}
					
					else if(ee.getModifiers().contains(Modifier.PRIVATE) || ee.getModifiers().contains(Modifier.PROTECTED)
							|| ee.getModifiers().isEmpty()){
						
						int incrementGet = 0, incrementSet = 0;
						
						for(var m : enclosedElements) {
							if(m.getKind().equals(ElementKind.METHOD)) {
								
								if(m.getModifiers().contains(Modifier.PRIVATE) && (m.getSimpleName().toString().startsWith("set")
									|| m.getSimpleName().toString().startsWith("get"))) {
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
			}
			
			if(incrementConstructor == 0){
				processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This CLASS has no CONSTRUCTOR");
			}
		}
					
		return true;
	}
}
