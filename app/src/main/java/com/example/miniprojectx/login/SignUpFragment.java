package com.example.miniprojectx.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniprojectx.R;
import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.example.miniprojectx.manager.NetworkManager;
import com.example.miniprojectx.manager.NetworkRequest;
import com.example.miniprojectx.manager.PropertyManager;
import com.example.miniprojectx.request.SignUpRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.edit_username)
    EditText nameView;
    @BindView(R.id.edit_password)
    EditText passwordView;
    @BindView(R.id.edit_email)
    EditText emailView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_sign_up)
    public void onSingUp() {
        String username = nameView.getText().toString();
        final String email = emailView.getText().toString();
        final String password = passwordView.getText().toString();
        SignUpRequest request = new SignUpRequest(getContext(), username, password, email, "1234");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                User user = result.getResult();
                PropertyManager.getInstance().setEmail(email);
                PropertyManager.getInstance().setPassword(password);
                PropertyManager.getInstance().setRegistrationId("1234");
                Toast.makeText(getContext(), "user id :" + user.getId(), Toast.LENGTH_SHORT).show();
                ((SimpleLoginActivity)getActivity()).moveMainActivity();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "message : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
