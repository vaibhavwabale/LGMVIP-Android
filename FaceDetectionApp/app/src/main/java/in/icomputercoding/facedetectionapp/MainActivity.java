package in.icomputercoding.facedetectionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_IMAGE_CAPTURE = 300;
    Button camera;
    InputImage image;
    FaceDetector detector;


    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        camera = findViewById(R.id.cameraBtn);
        camera.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(MainActivity.this, "Camera Not Working", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK || requestCode == 121) {

            assert data != null;
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            detectFace(bitmap);
        }
    }

    private void detectFace(Bitmap bitmap) {
        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .setMinFaceSize(0.1f)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .enableTracking()
                        .build();

        try {
            image = InputImage.fromBitmap(bitmap, 0);
            detector = FaceDetection.getClient(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        detector.process(image).addOnSuccessListener(faces -> {
            String resultText = " ";
            int i = 0;
            DecimalFormat df = new DecimalFormat("#.00");
            for (Face face : faces) {


                resultText = resultText.concat("\n\nFACE NUMBER " + i)
                        .concat(
                                "\nSmile: " + (df.format(face.getSmilingProbability() * 100)) + "%")
                        .concat(
                                "\nLeft Eye: " + (df.format(face.getLeftEyeOpenProbability() * 100)) + "%")
                        .concat(
                                "\nRight Eye: " + (df.format(face.getRightEyeOpenProbability() * 100)) + "%");
                i++;
            }

            resultText = resultText.concat("\n\n" + i + " Faces Total Detected");
            if (faces.size() == 0) {
                Toast.makeText(MainActivity.this, "SORRY, NO FACES DETECTED", Toast.LENGTH_LONG).show();


            } else {
                Bundle bundle = new Bundle();
                bundle.putString(LCOFaceDetection.RESULT_TEXT, resultText);
                DialogFragment resultDialog = new ResultDialog();
                resultDialog.setArguments(bundle);
                resultDialog.setCancelable(false);
                resultDialog.show(getSupportFragmentManager(), LCOFaceDetection.RESULT_DIALOG);


            }
        });
    }

}