package com.mirhoseini.fyber.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.mirhoseini.fyber.Presentation.LoginPresenter;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.module.LoginModule;
import com.mirhoseini.fyber.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    LoginPresenter presenter;

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
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.save)
    Button save;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private boolean checkValuesValidation() {
        boolean validation = true;
        View focusView = null;

        //check application id validations
        if (presenter.checkPub0Validation(pub0.getText().toString().trim())) {
            pub0.setError(resources.getString(R.string.pub0_required_error));
            focusView = pub0;
            validation = false;
        }

        //check application id validations
        if (presenter.checkApplicationIdValidation(applicationId.getText().toString().trim())) {
            applicationId.setError(resources.getString(R.string.application_id_required_error));
            focusView = applicationId;
            validation = false;
        }

        //check api key validations
        if (presenter.checkApiKeyValidation(apiKey.getText().toString().trim())) {
            apiKey.setError(resources.getString(R.string.api_key_required_error));
            focusView = apiKey;
            validation = false;
        }

        //check user id validations
        if (presenter.checkUserIdValidation(userId.getText().toString().trim())) {
            userId.setError(resources.getString(R.string.user_id_required_error));
            focusView = userId;
            validation = false;
        }

        if (null != focusView)
            focusView.requestFocus();

        return validation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        RxView.clicks(cancel).subscribe(this::cancelLogin);

        RxView.clicks(save).subscribe(this::saveLogin);

        setupToolbar();

        presenter.loadValues();

        Timber.d("Login Activity Created");
    }

    private void cancelLogin(Void v) {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void saveLogin(Void v) {
        if (checkValuesValidation()) {
            presenter.saveValues(
                    userId.getText().toString().trim(),
                    apiKey.getText().toString().trim(),
                    Integer.parseInt(applicationId.getText().toString().trim()),
                    pub0.getText().toString().trim()
            );

            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void fillValues(String userId, String apiKey, int applicationId, String pub0) {
        // filling values from cache or some sample values which must be removed in final version
        this.userId.setText(userId);
        this.apiKey.setText(apiKey);
        this.applicationId.setText(applicationId + "");
        this.pub0.setText(pub0);
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new LoginModule(this))
                .inject(this);
    }
}
