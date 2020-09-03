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
			
			for(var ee : enclosedElements) {
				if(ee.getKind() == ElementKind.FIELD) {
					if(ee.getModifiers().isEmpty()) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is has no set modifier", ee);
					}
					else if(ee.getModifiers().contains(Modifier.PUBLIC) && !ee.getModifiers().contains(Modifier.FINAL)) {
						processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is not FINAL and set to PUBLIC modifier", ee);
					}
					else{
						int increment = 0;
						for(var m : enclosedElements) {
							if(m.getKind() == ElementKind.METHOD) {
								if(m.getModifiers().contains(Modifier.PRIVATE) && (m.getSimpleName().toString().startsWith("set")
									|| m.getSimpleName().toString().startsWith("get"))) {
									processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This METHOD is set to PRIVATE modifier", m);
								}
								else if((m.getSimpleName().toString().startsWith("set")
										|| m.getSimpleName().toString().startsWith("get"))
										&& m.getSimpleName().toString().toLowerCase().contains(ee.getSimpleName().toString().toLowerCase())){
									increment++;
								}
							}
						}
						if(increment < 2) {
							processingEnv.getMessager().printMessage(Kind.WARNING, "Ooops! This FIELD is has no set GETTER OR SETTER", ee);
						}
					}
				}
			}
		}
					
		return true;
	}

}
