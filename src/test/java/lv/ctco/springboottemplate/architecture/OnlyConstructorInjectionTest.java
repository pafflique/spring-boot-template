package lv.ctco.springboottemplate.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OnlyConstructorInjectionTest {

  static JavaClasses importedClasses;

  @BeforeAll
  static void setup() {
    importedClasses = new ClassFileImporter().importPackages("lv.ctco.springboottemplate");
  }

  @Test
  void no_field_injection_should_be_used() {
    ArchRule rule =
        noFields()
            .should()
            .beAnnotatedWith(org.springframework.beans.factory.annotation.Autowired.class)
            .because("We enforce constructor injection for better testability and immutability");

    rule.check(importedClasses);
  }
}
