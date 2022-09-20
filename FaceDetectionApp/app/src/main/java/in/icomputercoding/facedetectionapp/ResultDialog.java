package in.icomputercoding.facedetectionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ResultDialog extends DialogFragment {

    Button okButton;
    TextView resultTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultdialog, container, false);
        String resultText = " ";

        okButton = view.findViewById(R.id.result_ok_button);
        resultTextView = view.findViewById(R.id.result_text_view);

        Bundle bundle = getArguments();
        resultText = Objects.requireNonNull(bundle).getString(LCOFaceDetection.RESULT_TEXT);
        resultTextView.setText(resultText);

        okButton.setOnClickListener(
                v -> dismiss());
        return view;
    }
}
