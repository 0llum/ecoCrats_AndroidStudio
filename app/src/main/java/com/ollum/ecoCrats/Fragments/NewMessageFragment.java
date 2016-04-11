package com.ollum.ecoCrats.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ollum.ecoCrats.BackgroundTasks.BackgroundTask;
import com.ollum.ecoCrats.Adapters.FriendlistAdapter;
import com.ollum.ecoCrats.Activities.MainActivity;
import com.ollum.ecoCrats.R;

public class NewMessageFragment extends Fragment implements View.OnClickListener {
    EditText etReceiver, etSubject, etMessage;
    Button send;
    String sender, receiver, subject;
    String current, previous;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager.BackStackEntry currentFragment = MainActivity.fragmentManager.getBackStackEntryAt(MainActivity.fragmentManager.getBackStackEntryCount() - 1);
        current = currentFragment.getName();

        if (current == "FriendlistFragment" && FriendlistAdapter.bundle != null) {
            receiver = FriendlistAdapter.bundle.getString("receiver");
        } else if (current == "MessageDetailsFragment" && MessageDetailsFragment.bundle != null) {
            receiver = MessageDetailsFragment.bundle.getString("receiver");
            subject = "Re: " + MessageDetailsFragment.bundle.getString("subject");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);

        sender = MainActivity.user.username;

        etReceiver = (EditText) view.findViewById(R.id.message_details_sender);
        etSubject = (EditText) view.findViewById(R.id.message_details_subject);
        etMessage = (EditText) view.findViewById(R.id.new_message_message);
        send = (Button) view.findViewById(R.id.new_message_send);
        send.setOnClickListener(this);

        etReceiver.setText(receiver);
        etSubject.setText(subject);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_message_send:
                String receiver = etReceiver.getText().toString().trim();
                String subject = etSubject.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                if (receiver.equals("")) {
                    Toast.makeText(getContext(), "Please choose a receiver", Toast.LENGTH_LONG).show();
                } else if (subject.equals("")) {
                    Toast.makeText(getContext(), "Please enter a subject", Toast.LENGTH_LONG).show();
                } else if (message.equals("")) {
                    Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_LONG).show();
                } else {
                    String method = "sendMessage";
                    BackgroundTask backgroundTask = new BackgroundTask(getContext());
                    backgroundTask.execute(method, sender, receiver, subject, message);
                }

                break;
            /*case R.id.new_message_cancel:
                break;*/
        }
    }
}
