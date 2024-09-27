package testing.controller;

import main_package.persistence.impl.ServicesFactory;

public class TestServices {
    private final ServicesFactory servicesFactory;

    private static volatile TestServices instance;

    private TestServices() {
        this.servicesFactory = new ServicesFactory("jdbc:sqlite:RistoranteTest.db", "src/testing/filesTest/parametriConfigurazioneTEST.json", "src/testing/filesTest/parametriRistoranteTEST.json");;
    }

    public static TestServices getInstance() {
        TestServices result = instance;
        if (result == null) {
            synchronized (TestServices.class) {
                result = instance;
                if (result == null) {
                    instance = result = new TestServices();
                }
            }
        }
        return result;
    }

    public ServicesFactory getServicesFactory() {
        return this.servicesFactory;
    }
}
