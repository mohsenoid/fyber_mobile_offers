package com.mirhoseini.fyber.Presentation;

import com.mirhoseini.fyber.view.LoginView;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface LoginPresenter extends BasePresenter<LoginView> {

    void saveValues(String userId, String apiKey, int applicationId, String pub0);

    boolean checkPub0Validation(String value);

    boolean checkApplicationIdValidation(String value);

    boolean checkApiKeyValidation(String value);

    boolean checkUserIdValidation(String value);

    void loadValues();

}
