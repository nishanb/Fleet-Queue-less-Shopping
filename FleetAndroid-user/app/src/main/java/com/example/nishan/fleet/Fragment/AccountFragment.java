package com.example.nishan.fleet.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nishan.fleet.Activity.MainActivity;
import com.example.nishan.fleet.Activity.SignInActivity;
import com.example.nishan.fleet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Account");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView tvUserName = view.findViewById(R.id.user_profile_name);
        tvUserName.setText(user.getEmail().substring(0, 6));

        TextView tvUserEmail = view.findViewById(R.id.user_profile_short_bio);
        tvUserEmail.setText(user.getEmail().toString());



        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure to logout ?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                return;
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getContext(), SignInActivity.class));
                            }
                        });
                AlertDialog alertDialog = builder.create();

                alertDialog.setTitle(R.string.app_name);
                alertDialog.show();


            }
        });
    }

}
