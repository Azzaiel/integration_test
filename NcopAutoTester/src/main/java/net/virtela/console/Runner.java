package net.virtela.console;

import net.virtela.tester.AdministationTester;
import net.virtela.tester.StandardQuationTester;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Runner {

	public static void main(String[] args) {
		
		final TestListenerAdapter tla = new TestListenerAdapter();
		final TestNG testng = new TestNG();
		testng.addListener(tla);
		testng.setTestClasses(new Class[] { StandardQuationTester.class, AdministationTester.class }); //Add Other test class here
		testng.run();

	}

}
