/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.configuration.field.notnull;

import static junit.framework.Assert.assertEquals;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.configuration.AbstractConfigurationTest;
import br.gov.frameworkdemoiselle.configuration.ConfigurationException;

@RunWith(Arquillian.class)
public class ConfigurationNotNullFieldTest extends AbstractConfigurationTest {

	@Inject
	private PropertyWithFilledFieldConfig filledFieldConfig;
	
	@Inject
	private PropertyWithEmptyFieldConfig emptyFieldsConfig;
	
	@Inject
	private PropertyWithoutNotNullField withoutNotNullField;

	@Inject
	private PropertyWithoutFileConfig noFileConfig;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive deployment = createConfigurationDeployment();

		deployment.addPackages(true, ConfigurationNotNullFieldTest.class.getPackage());
		deployment.addAsResource(
				new FileAsset(new File("src/test/resources/configuration/field/notnull/demoiselle.properties")),
				"demoiselle.properties").addAsResource(
				new FileAsset(new File("src/test/resources/configuration/field/notnull/empty-field.properties")),
				"empty-field.properties").addAsResource(
				new FileAsset(new File("src/test/resources/configuration/field/notnull/without-field.properties")),
				"without-field.properties");

		return deployment;
	}
	
	@Test
	public void loadFieldNotNullFromFilledProperty(){
		Integer expected = 1;
		
		assertEquals(expected, filledFieldConfig.getIntegerNotNull());
	}
	
	@Test(expected = ConfigurationException.class)
	public void loadFieldNotNullFromEmptyProperty(){
		emptyFieldsConfig.getIntegerNotNull();
	}
	
	@Test(expected = ConfigurationException.class)
	public void loadFieldFromPropertyFileWithoutNotNullField(){
		withoutNotNullField.getIntegerNotNull();
	}
	
	@Test//(expected = ConfigurationException.class)
	/*TODO: Lançar exceção quando o arquivo de propriedade declarado pela 
	 * classe de configuração que contenha um atributo anotado com @NotNull
	 * não existir */
	public void loadFieldNotNullFromInexistentPropertyFile(){
		noFileConfig.getIntegerNotNull();
	}	
}