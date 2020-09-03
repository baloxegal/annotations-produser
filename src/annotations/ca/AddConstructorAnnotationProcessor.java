package annotations.ca;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("annotations.ca.AddConstructor")
public class AddConstructorAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		for(var e : roundEnv.getElementsAnnotatedWith(AddConstructor.class)) {
			processingEnv.getClass().
		}
		
		return true;
	}
	
}
