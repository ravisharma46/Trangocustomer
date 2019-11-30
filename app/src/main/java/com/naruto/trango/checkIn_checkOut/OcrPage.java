package com.naruto.trango.checkIn_checkOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.naruto.trango.R;

public class OcrPage extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    final int REQUEST_CAMERA_PERMISION = 1001;
    private static final int GALLARY_REQUEST = 1;

    private CameraSource mCameraSource;

    SurfaceView mSurfaceView;

    // To ensure only one time text recognition
    boolean mReceivedDetection = false;
    boolean mFlashOn = false;

    TextView mBtnFlash;
    TextRecognizer mTextRecognizer;

    // mFlag is set 1 as soon as gallery is opened.
    int mFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_page);

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            startActivity(new Intent(this, Make_Payment.class));
            finish();
        }, 2000);

      /*  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSurfaceView = findViewById(R.id.surface_view);
        mBtnFlash    = findViewById(R.id.flash);

        mBtnFlash.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btnFlashClicked();
            }
        });

        mTextRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        startTextRecognition();
        clickListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume : mReceiveDetection = " + mReceivedDetection + " mFlag = "+mFlag);
        mReceivedDetection = false;
        mFlag = 0;

        startTextRecognition();
    }

    private void clickListener() {

        ImageView btnback   = findViewById(R.id.back_btn);
        TextView btnGallery = findViewById(R.id.gallery);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFlag = 1;

                Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent,GALLARY_REQUEST);
            }
        });
    }


    private void startTextRecognition()
    {
        if (!mTextRecognizer.isOperational()) {
            Log.e(TAG, "Detector Dependencies are not available yet");
            return;
        }

        com.naruto.trango.checkIn_checkOut.CameraSource.Builder builder = new
                com.naruto.trango.checkIn_checkOut.CameraSource.Builder(OcrPage.this, mTextRecognizer);

        mCameraSource = builder
                .setFlashMode(false ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .setFocusMode(true ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE:null)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1000000, 1000000)
                .setRequestedFps(1.0f)
                .build();

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                Log.e(TAG, "called : surfaceCreated");

                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OcrPage.this, new String[]{
                                Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISION);
                        Log.e(TAG,"Camera Permission not granted");
                        return;
                    }
                    mCameraSource.start(mSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "called : surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e(TAG, "called : surfaceDestroyed");
                mCameraSource.stop();
            }
        });

        mTextRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections)
            {
                if(mReceivedDetection)
                    return;

                Log.e(TAG, "called : receiveDectections");

                final SparseArray<TextBlock> items = detections.getDetectedItems();

                if (items.size() != 0) {

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i < items.size(); i++) {
                        Log.e(TAG, "called : for loop");
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }

                    String text = stringBuilder.toString().trim();

                    if (text.length() != 0) {
                        // To only detect for once
                        mReceivedDetection = true;
                        if (mFlag == 0){
                            text = removeUnwantedText(text);
                            Log.e(TAG,"text= "+text);
                            startIntent(text);

                        }


                    }
                }
            }
        });

    }

    private String removeUnwantedText(String text) {

        String reg = "(AP|AR|BR|CG|CH|DD|DL|DN|GA|GJ|HP|HR|JH|JK|KA|KL|LD|MH|ML|NL|OD|OR|PB|PY|RJ|SK|TN|TS|TR|UP|UK|WB)\\w{3,5}\\d{4}";

        Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()){
            return matcher.group(0);
        }

        return "NotDetected";
    }

    private void recogniseTextFromBitmap(Bitmap bitmap) {

        Log.e(TAG,"called : recogniseTextFromxImage");

        if(bitmap==null){
            return;
        }

        Log.e(TAG,"step1");

        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        Log.e(TAG,"step22");

        if (!textRecognizer.isOperational()) {
            Log.e(TAG, "Detector dependencies are not yet available.");
        }



        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();

        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

        String text ="";
        for (int i = 0; i < textBlocks.size(); i++) {

            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            text = text + " " + textBlock.getValue();
        }
        startIntent(text);
    }


    private void startIntent(String text){

        Intent i = new Intent(OcrPage.this, ResultPage.class);
        i.putExtra("text",text);
        startActivity(i);

        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, "called : onRequestPemissionResult");
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    }
                    try {
                        mCameraSource.start(mSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void btnFlashClicked() {

        if (!mFlashOn) {
            mCameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mBtnFlash.setText("Turn off");
            mBtnFlash.setBackground(getDrawable(R.drawable.button_rounded_green));
            mFlashOn = true;

        }
        else {
            mCameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mBtnFlash.setText("Turn on");
            mBtnFlash.setBackground(getDrawable(R.drawable.button_rounded_red));
            mFlashOn = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == GALLARY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // Cropping using 3rd party library
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                    recogniseTextFromBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(OcrPage.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }
*/


    }



    }
