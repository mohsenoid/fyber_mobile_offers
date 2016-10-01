package com.mirhoseini.fyber.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.ValueManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class LoginActivity extends BaseActivity {

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.uid)
    EditText userId;
    @BindView(R.id.apiKey)
    EditText apiKey;
    @BindView(R.id.appId)
    EditText applicationId;
    @BindView(R.id.pub0)
    EditText pub0;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @OnClick(R.id.save)
    void onSaveClick() {
        if (checkValuesValidation()) {
            saveValues();

            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean checkValuesValidation() {
        boolean validation = true;
        View focusView = null;

        //check application id validations
        if (pub0.getText().toString().trim().isEmpty()) {
            pub0.setError(resources.getString(R.string.pub0_required_error));
            focusView = pub0;
            validation = false;
        }

        //check application id validations
        if (applicationId.getText().toString().trim().isEmpty()) {
            applicationId.setError(resources.getString(R.string.application_id_required_error));
            focusView = applicationId;
            validation = false;
        } else {
            try {
                Integer.parseInt(applicationId.getText().toString().trim());
            } catch (NumberFormatException e) {
                applicationId.setError(resources.getString(R.string.application_id_number_error));
                focusView = applicationId;
                validation = false;
            }
        }

        //check api key validations
        if (apiKey.getText().toString().trim().isEmpty()) {
            apiKey.setError(resources.getString(R.string.api_key_required_error));
            focusView = apiKey;
            validation = false;
        }

        //check user id validations
        if (userId.getText().toString().trim().isEmpty()) {
            userId.setError(resources.getString(R.string.user_id_required_error));
            focusView = userId;
            validation = false;
        }

        if (null != focusView)
            focusView.requestFocus();

        return validation;
    }

    private void saveValues() {
        ValueManager.setUserId(context, userId.getText().toString().trim());
        ValueManager.setApiKey(context, apiKey.getText().toString().trim());
        ValueManager.setApplicationId(context, Integer.parseInt(applicationId.getText().toString().trim()));
        ValueManager.setPub0(context, pub0.getText().toString().trim());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        fillValues();

        Timber.d("Login Activity Created");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fillValues() {
        // filling values from cache or some sample values which must be removed in final version
        userId.setText(null == ValueManager.getUserId(context) ? Constants.SAMPLE_USER_ID : ValueManager.getUserId(context));
        apiKey.setText(null == ValueManager.getApiKey(context) ? Constants.SAMPLE_API_KEY : ValueManager.getApiKey(context));
        applicationId.setText(null == ValueManager.getApplicationId(context) ? Constants.SAMPLE_APPLICATION_ID : ValueManager.getApplicationId(context) + "");
        pub0.setText(null == ValueManager.getPub0(context) ? Constants.SAMPLE_PUB0 : ValueManager.getPub0(context));
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }
}
