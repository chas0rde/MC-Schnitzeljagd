package de.hsb.kss.mc_schnitzeljagd.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;

import android.net.Uri;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.logic.LogicHelper;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;

public class PlayerTextHintActivity extends SchnitzelActivity {
	Hint currentHint = null;
	private int hintType = 0;
	private int hintId = -1;
	private int scaleFactor = 8;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    
     
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Schnitzeljagd";
    private Uri fileUri; // file url to store image/video
    private LinearLayout imageLayout=null;
    
    
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT).show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try { 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            ImageView imgPreview = (ImageView)findViewById(R.id.image_view_preview);
 
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_text_hint);
		initUi();
	}
	
	
	
	protected void initUi()
	{
		super.initUi();
		
		// If Activity was started by ListHintsActivity 		
		if(this.getIntent() != null && this.getIntent().getExtras() != null ){
			
			if (this.getIntent().getExtras().containsKey("HintType")) {
				hintType = this.getIntent().getExtras().getInt("HintType");
			
				if(this.getIntent().getExtras().containsKey("hintId")){
					 hintId = this.getIntent().getExtras().getInt("hintId");
				}
				
				switch (hintType) {
				case 0: createTextHint(); break;
				case 1: createImageHint(); break;
				case 2: break;
				case 3: break;

				default:createTextHint(); break;
				}
			
			
			}
			
			

		}		
	}
	
	private void createImageHint() {
		// TODO Auto-generated method stub
		imageLayout = (LinearLayout)findViewById(R.id.image_layout);
		ImageView preview = (ImageView)findViewById(R.id.image_view_preview);
		preview.setImageDrawable(null);
		
		if(isDeviceSupportCamera()) {
			imageLayout.setVisibility(View.VISIBLE);
		} else {
			Toast.makeText(getApplicationContext(), "No Camera available", Toast.LENGTH_LONG).show();
		}
		
		if(hintId >= 0) {
			currentHint = gameCreation.getHint(hintId);
        	byte[] imageAsBytes = Base64.decode(currentHint.getDescription().getBytes(), 0);
        	preview.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
       } 
				
	}
	
	
	/*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */
	public void startCamera(View view) {
		   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		   
		    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		 
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		 
		    // start the image capture Intent
		    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
	    return Uri.fromFile(getOutputMediaFile(type));
	}
	 
	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {
	 
	    // External sdcard location
	    File mediaStorageDir = new File(
	            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
	 
	    // Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists()) {
	        if (!mediaStorageDir.mkdirs()) {
	            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                    + IMAGE_DIRECTORY_NAME + " directory");
	            return null;
	        }
	    }
	 
	    // Create a media file name String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + "tempimage" + ".jpg");
	    } else if (type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + "tempvideo" + ".mp4");
	    } else {
	        return null;
	    }
	 
	    return mediaFile;
	}
	
	/**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


	private void createTextHint() {		
		EditText editText = (EditText)findViewById(R.id.hint_text_hint_id);	
		if(hintId >= 0) {
			currentHint = gameCreation.getHint(hintId);
			editText.setText(currentHint.getDescription());
		} 
		editText.setVisibility(View.VISIBLE);
	}
	
	// adds hint to current Point
	public void startSaveTextHint(View view){
		boolean hasError = true;
		
		if(gameCreation != null)
		{
				if(currentHint == null) {
					 currentHint = new Hint();
					 gameCreation.addHint(currentHint);
				}
				
				switch (hintType) {
				case 0: hasError = saveEditTextHint(); break;
				case 1: hasError = saveImageHint(); break;
				case 2: break;
				case 3: break;

				default:saveEditTextHint(); break;
				}
				// if save new values for hint 
				currentHint.setHintType(LogicHelper.TranslateType(hintType));
				currentHint.setFree(true);
				if(!hasError) {
					Intent intent = new Intent(this, OrganizerCreatePoiActivity.class);
					startActivity(intent);
				} 
		} 
	}
	
	private boolean saveImageHint() {
		// TODO Auto-generated method stub
		boolean hasError = true;
		
		Bitmap bmf = BitmapFactory.decodeFile(fileUri.getPath());
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		Bitmap bm = Bitmap.createScaledBitmap(bmf, bmf.getWidth()/scaleFactor, bmf.getHeight()/scaleFactor, true);
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
		byte[] b = baos.toByteArray(); 		
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		
		ImageView preview = (ImageView)findViewById(R.id.image_view_preview);
		
		if(preview.getDrawable() != null && !encodedImage.isEmpty()) {
			currentHint.setDescription(encodedImage);			
			hasError = false;
		} else {
			setErrorMsg("Picture could not be taken!");
		}
		
		return hasError;
	}



	private boolean saveEditTextHint() {
		EditText hintText = (EditText)findViewById(R.id.hint_text_hint_id);
		boolean hasError = true;		
		
		if( hintText != null)
		{
			if (hintText.getText().toString().isEmpty()){
				setErrorMsg("Please set hinttext");
			} else {
				// if save new values for hint 
				currentHint.setDescription(hintText.getText().toString());
				hasError = false;
			}
		} 
		return hasError;
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_text_hint, menu);
		return true;
	}
	
	
}
