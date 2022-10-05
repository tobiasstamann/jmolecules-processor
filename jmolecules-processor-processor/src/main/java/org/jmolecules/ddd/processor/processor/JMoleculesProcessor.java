package org.jmolecules.ddd.processor.processor;


import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.spiap.api.SpiService;
import org.jmolecules.ddd.annotation.Service;
import org.jmolecules.ddd.annotation.ValueObject;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Annotation Processor for {@link org.jmolecules.ddd.annotation.ValueObject}.
 * <p>
 * This demo processor does some validations and creates a class.
 */

@SpiService(Processor.class)
public class JMoleculesProcessor extends AbstractAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATIONS = createSupportedAnnotationSet(ValueObject.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // process Services annotation
        for (Element element : roundEnv.getElementsAnnotatedWith(ValueObject.class)) {

            // ----------------------------------------------------------
            // Check rule for ValidationObject
            // 1. Must not use Service
            // 2. Must not use Infra
            // ----------------------------------------------------------

            TypeElementWrapper valueObjectTypeElement = TypeElementWrapper.wrap((TypeElement) element);

            List<TypeElementWrapper> dependencies = getDependenciesOfTypeElement(valueObjectTypeElement);

            // now do checks - for service annotation
            List<TypeElementWrapper> referencedServiceTypeElements = dependencies.stream().filter(e -> e.getAnnotation(Service.class).isPresent()).collect(Collectors.toList());
            for (TypeElementWrapper referencedType : referencedServiceTypeElements){
                MessagerUtils.error(element, JMoleculesProcessorMessages.ERROR_ILLEGAL_REFERENCE, element.getSimpleName(), ValueObject.class.getSimpleName(), referencedType.getSimpleName(), Service.class.getSimpleName());
            }


        }

        return false;

    }

    // -----------------------------------------------------------------------------------------
    // Everything down below this comment will be integrated in https://github.com/toolisticon/aptk soon.
    // This will reduced the setup of such kind of processors to a minimum.
    // -----------------------------------------------------------------------------------------

    List<TypeElementWrapper> getDependenciesOfTypeElement(TypeElementWrapper typeElementWrapper) {

        List<TypeElementWrapper> dependenciesOfType = new ArrayList<>();

        // need to check type itself and all inner types
        for (TypeElementWrapper innerType : typeElementWrapper.getInnerTypes()) {
            dependenciesOfType.addAll(getDependenciesOfTypeElement(innerType));
        }

        // check fields, constructors, methods and interfaces and superclasses
        // unsure if types used in annotations should be checked as well

        // get interface
        dependenciesOfType.addAll(typeElementWrapper.getInterfaces().stream()
                .map(e -> e.getTypeElement())
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList()));

        // get super type - safe not to check if optinal is present due to implicit Object superclass
        dependenciesOfType.add(typeElementWrapper.getSuperclass().getTypeElement().get());

        // get fields - type of field
        for (VariableElementWrapper variableElement : typeElementWrapper.getFields()) {
            dependenciesOfType.addAll(getDependenciesOfTypeMirrorWrapper(variableElement.asType()));
        }

        // get constructors - type of parameters
        for (ExecutableElementWrapper constructorElementWrapper : typeElementWrapper.getConstructors()) {

            // Just need to handle parameters
            for (VariableElementWrapper parameter : constructorElementWrapper.getParameters()) {
                dependenciesOfType.addAll(getDependenciesOfTypeMirrorWrapper(parameter.asType()));
            }

        }

        // get methods - return type and type of parameters
        for (ExecutableElementWrapper methodElementWrapper : typeElementWrapper.getMethods()) {

            // need to handle parameters and return type
            dependenciesOfType.addAll(getDependenciesOfTypeMirrorWrapper(methodElementWrapper.getReturnType()));

            // handle parameters
            for (VariableElementWrapper parameter : methodElementWrapper.getParameters()) {
                dependenciesOfType.addAll(getDependenciesOfTypeMirrorWrapper(parameter.asType()));
            }

        }

        return dependenciesOfType;
    }

    List<TypeElementWrapper> getDependenciesOfTypeMirrorWrapper(TypeMirrorWrapper typeMirrorWrapper) {

        // need to apply the following rules:
        // - primitive types have to be ignored
        // - must handle arrays separately

        TypeMirrorWrapper workTypeMirrorWrapper = typeMirrorWrapper;

        // handle array - need to process its component type
        if (workTypeMirrorWrapper.isArray()) {
            workTypeMirrorWrapper = workTypeMirrorWrapper.getWrappedComponentType();
        }

        List<TypeElementWrapper> dependencyTypeElements = new ArrayList<>();

        if (!workTypeMirrorWrapper.isVoidType()) {

            // Leave out primitives
            if (!workTypeMirrorWrapper.isPrimitive()) {
                // we will take the getImports for now, since it just filters out java.lang based classes.
                // Therefore, need to convert String to TypeElementWrappers
                // Can ignore primitives at this step, since they are not supported in generics
                dependencyTypeElements.addAll(workTypeMirrorWrapper.getImports().stream()
                        .map(TypeElementWrapper::getByFqn)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()));
            }

        }

        return dependencyTypeElements;
    }


}
