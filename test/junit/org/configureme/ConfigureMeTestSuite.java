package org.configureme;

import org.configureme.environments.ApplicationEnvironmentTest;
import org.configureme.environments.DynamicEnvironmentTest;
import org.configureme.environments.LocaleBasedEnvironmentTest;
import org.configureme.repository.AttributeValueTest;
import org.configureme.repository.ConfigurationRepositoryTest;
import org.configureme.sources.ConfigurationSourceKeyTest;
import org.configureme.sources.ConfigurationSourceRegistryAddTest;
import org.configureme.sources.ConfigurationSourceRegistryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={DynamicEnvironmentTest.class, ApplicationEnvironmentTest.class, LocaleBasedEnvironmentTest.class, GlobalEnvironmentTest.class,
		ConfigurationSourceRegistryAddTest.class, ConfigurationSourceRegistryTest.class, ConfigurationSourceKeyTest.class,
		AttributeValueTest.class, ConfigurationRepositoryTest.class})
public class ConfigureMeTestSuite {

}
