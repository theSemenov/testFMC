/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package test.fmc.simpleclient;

import javax.inject.Inject;

import test.fmc.simpleclient.protocol.Service;
import test.fmc.simpleclient.ui.IndexPage;
import test.fmc.simpleclient.ui.Pages;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Push
@Theme("simpleclienttheme")
@SuppressWarnings("serial")
@CDIUI("")
@Widgetset("test.fmc.simpleclient.SimpleClientlWidgetSet")
public class SimpleClientUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.navigateTo(Pages.INDEX);
	}

}
