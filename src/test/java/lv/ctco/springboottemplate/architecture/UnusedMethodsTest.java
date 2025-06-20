package lv.ctco.springboottemplate.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class UnusedMethodsTest {

  protected static JavaClasses importedClasses;

  @BeforeAll
  static void setup() {
    importedClasses = new ClassFileImporter().importPackages("lv.ctco.springboottemplate");
  }

  @Test
  void injectable_beans_should_not_have_unused_non_private_methods() {
    ArchRule rule =
        classes()
            .that()
            .areAnnotatedWith(Component.class)
            .or()
            .areAnnotatedWith(Service.class)
            .or()
            .areAnnotatedWith(Repository.class)
            .should(
                new ArchCondition<>("have all non-private methods used") {
                  @Override
                  public void check(JavaClass item, ConditionEvents events) {
                    item.getMethods().stream()
                        .filter(method -> !method.getModifiers().contains(JavaModifier.PRIVATE))
                        .filter(method -> !isSpringLifecycleMethod(method))
                        .filter(method -> method.getAccessesToSelf().isEmpty())
                        .forEach(
                            method ->
                                events.add(
                                    new SimpleConditionEvent(
                                        method,
                                        false,
                                        String.format(
                                            "Method %s in %s is not private but never used",
                                            method.getName(), item.getName()))));
                  }

                  private boolean isSpringLifecycleMethod(JavaMethod method) {
                    return method.getName().equals("init")
                        || method.getName().equals("destroy")
                        || method.getName().equals("afterPropertiesSet")
                        || method.getName().startsWith("set");
                  }
                })
            .because(
                "Non-private methods in Spring beans should be used somewhere in the codebase");

    rule.check(importedClasses);
  }
}
