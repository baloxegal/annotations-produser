package annotations.ca;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("annotations.ca.Incapsulated")
public class IncapsulatedAnnotationsProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		for(var e : roundEnv.getElementsAnnotatedWith(Incapsulated.class)) {
			
			for(var ee : e.getEnclosedElements()) {
				if(ee.getKind() == ElementKind.FIELD) {
					if(ee.getModifiers().contains(Modifier.PUBLIC)) {
						processingEnv.getMessager().printMessage(Kind.ERROR, "Ooops! That class contains public FIELDS", ee);
					}
				}
			}
			
			
		}
					
		return true;
	}

}
