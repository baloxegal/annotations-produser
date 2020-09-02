package annotations.ca;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("annotations.ca.AddConstructor")
public class AddConstructorAnnotationsProcessor extends AbstractProcessor {
	
	private Messager messager;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
		for(var a : annotations) {
			for(var e : roundEnv.getElementsAnnotatedWith(a)) {
				messager.printMessage(Kind.ERROR, "WOW! That's GOOD!", e);
			}
		}	
		
		return true;
	}

}
