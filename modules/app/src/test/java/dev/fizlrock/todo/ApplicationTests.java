package dev.fizlrock.todo;

import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ApplicationTests {

  // @Test
  void writeDocumentationSnippets() {

    // var modules = ApplicationModules.of(App.class);
    var modules = ApplicationModules.of(App.class).verify();

    new Documenter(modules).writeModulesAsPlantUml().writeIndividualModulesAsPlantUml();
  }
}
