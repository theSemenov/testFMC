package test.fmc.simpleclient.ui;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.Service;
import test.fmc.simpleclient.protocol.impl.ClientDataHandler;
import test.fmc.simpleclient.protocol.impl.DefaultClientData;
import test.fmc.simpleclient.protocol.impl.VoidHandler;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@CDIView(Pages.INDEX)
public class IndexPage extends VerticalLayout implements View {
	
	private static Log _log = LogFactory.getLog(IndexPage.class);
	
	private TextField fioField;
	private TextField workPhoneField;
	private TextField mobilePhoneField;
	private TextField emailField;
	private Button okButton;
	private Button refreshButton;
	private Service service;
	
	@Inject
	public IndexPage(Service service) {
		this.service = service;
		initUI();
		initHandlers();
	}
	
	private void initHandlers() {
		okButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				saveFields();
			}
		});
		
		refreshButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				relodFields();
			}
		});
		
	}

	private void initUI() {
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setSizeFull();

		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setMargin(true);
		
		fioField = new TextField("ФИО");
		fioField.setWidth("100%");
		main.addComponent(fioField);
		
		workPhoneField = new TextField("Рабочий телефон"); 
		workPhoneField.setWidth("100%");
		main.addComponent(workPhoneField);
		
		mobilePhoneField = new TextField("Мобильный телефон");
		mobilePhoneField.setWidth("100%");
		main.addComponent(mobilePhoneField);
		
		emailField = new TextField("Email"); 
		emailField.setWidth("100%");
		main.addComponent(emailField);
		
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setWidth("100%");
		toolbar.setMargin(new MarginInfo(true, false, false, false));

		okButton = new Button("Ok");
		okButton.setWidth("100%");
		okButton.addStyleName("primary");
		toolbar.addComponent(okButton);
		toolbar.setExpandRatio(okButton, 3);
		
		refreshButton = new Button("Перезагрузить");
		refreshButton.setWidth("100%");
		refreshButton.addStyleName("link");
		toolbar.addComponent(refreshButton);
		toolbar.setExpandRatio(refreshButton, 2);
		
		main.addComponent(toolbar);

		Panel panel = new Panel("Учетная запись");
		panel.setWidth("350px");
		panel.setContent(main);
		addComponent(panel);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		relodFields();
	}

	private void relodFields() {
		service.getClientData(getClientDataHandler());
	}

	private void saveFields() {
		DefaultClientData data = new DefaultClientData();
		data.setFio(fioField.getValue());
		data.setWorkPhone(workPhoneField.getValue());
		data.setMobilePhone(mobilePhoneField.getValue());
		data.setEmail(emailField.getValue());
		
		ClientDataHandler handler =getClientDataHandler();
		
		service.setClientData(data, handler);
	}

	private ClientDataHandler getClientDataHandler() {
		return new ClientDataHandler() {
			
			@Override
			public void onSuccess(ClientData data) {
				fioField.setValue(data.getFio());
				workPhoneField.setValue(data.getWorkPhone());
				mobilePhoneField.setValue(data.getMobilePhone());
				emailField.setValue(data.getEmail());
				Notification.show("Данные успешно обновлены");
			}
			
			@Override
			public void onFault(Throwable t) {
				Notification.show("Ошибка при обращении к сервису", t.getLocalizedMessage(), Type.ERROR_MESSAGE);
				_log.error(t);
			}
		};
	}

}
