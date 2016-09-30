package com.mirhoseini.fyber.Presentation;

import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.LoginView;

import javax.inject.Inject;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {

    @Inject
    ValueManager valueManager;

    private LoginView view;

    @Inject
    public LoginPresenterImpl() {
    }

    @Override
    public void setView(LoginView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void saveValues(String userId, String apiKey, int applicationId, String pub0) {
        valueManager.setUserId(userId);
        valueManager.setApiKey(apiKey);
        valueManager.setApplicationId(applicationId);
        valueManager.setPub0(pub0);
    }

    @Override
    public boolean checkPub0Validation(String value) {
        return checkStringValidation(value);
    }

    @Override
    public boolean checkApplicationIdValidation(String value) {
        return checkStringValidation(value) && checkNumberValidation(value);
    }

    @Override
    public boolean checkApiKeyValidation(String value) {
        return checkStringValidation(value);
    }

    @Override
    public boolean checkUserIdValidation(String value) {
        return checkStringValidation(value);
    }

    @Override
    public void loadValues() {
        String userId = null == valueManager.getUserId() ? Constants.SAMPLE_USER_ID : valueManager.getUserId();
        String apiKey = null == valueManager.getApiKey() ? Constants.SAMPLE_API_KEY : valueManager.getApiKey();
        int applicationId = null == valueManager.getApplicationId() ? Constants.SAMPLE_APPLICATION_ID : valueManager.getApplicationId();
        String pub0 = null == valueManager.getPub0() ? Constants.SAMPLE_PUB0 : valueManager.getPub0();

        if (null != view)
            view.fillValues(userId, apiKey, applicationId, pub0);
    }


    boolean checkStringValidation(String value) {
        return (null == value || value.isEmpty());
    }

    boolean checkNumberValidation(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
