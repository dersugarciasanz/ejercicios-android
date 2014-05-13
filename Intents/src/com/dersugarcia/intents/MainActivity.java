package com.dersugarcia.intents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText editText;
	private TextView textView;
	private ImageView imageView;
	private File image;
	
	public static final String EDIT_TEXT = "EDIT_TEXT";
	public static final int REQUEST_FORM = 1;
	public static final int REQUEST_CAMERA = 2;
	public static final int REQUEST_CONTACT = 3;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		editText = (EditText) findViewById(R.id.edittext_main);
		textView = (TextView) findViewById(R.id.textview_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		findViewById(R.id.button_form).setOnClickListener(new FormClickListener());
		findViewById(R.id.button_camera).setOnClickListener(new CameraClickListener());
		findViewById(R.id.button_contact).setOnClickListener(new ContactClickListener());

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_FORM:
			if (resultCode == Activity.RESULT_OK) {
				textView.setText(data.getCharSequenceExtra(FormActivity.EDIT_TEXT));
			}
			break;
		case REQUEST_CAMERA:
			setPic();
			break;
		case REQUEST_CONTACT:
			 if (resultCode == RESULT_OK) {
		            // Get the URI that points to the selected contact
		            Uri contactUri = data.getData();
		            // We only need the NUMBER column, because there will be only one row in the result
		            String[] projection = {Phone.NUMBER};

		            // Perform the query on the contact to get the NUMBER column
		            // We don't need a selection or sort order (there's only one result for the given URI)
		            // CAUTION: The query() method should be called from a separate thread to avoid blocking
		            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
		            // Consider using CursorLoader to perform the query.
		            Cursor cursor = getContentResolver()
		                    .query(contactUri, projection, null, null, null);
		            cursor.moveToFirst();

		            // Retrieve the phone number from the NUMBER column
		            int column = cursor.getColumnIndex(Phone.NUMBER);
		            String number = cursor.getString(column);

		            // Do something with the phone number...
		            textView.setText(number);
		        }
			break;


		default:
			break;
		}
	}
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = imageView.getWidth();
	    int targetH = imageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    imageView.setImageBitmap(bitmap);
	}
	
	
	private class FormClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			Log.d("INTENTS", "Form_button onClick()");
			String text = editText.getText().toString();
			if (text.length() != 0) {
				Intent intent = new Intent(MainActivity.this,FormActivity.class);
				intent.putExtra(EDIT_TEXT, text);
				startActivityForResult(intent, REQUEST_FORM);
			} 
		}
	}

	private class CameraClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		    	File photoFile = null;
		        try {
		        	photoFile = createImageFile();
		        } catch (IOException ex) {
		            Log.d("INTENTS","Error occurred while creating the File:" + ex.getMessage());
		        }
		        // Continue only if the File was successfully created
		        if (photoFile != null) {
		            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
		                    Uri.fromFile(photoFile));
		            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
		        }
		    }
		}

		private File createImageFile() throws IOException {
		    // Create an image file name
		    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
		    String imageFileName = "JPEG_" + timeStamp + "_";
		    File storageDir = Environment.getExternalStorageDirectory();
		    image = File.createTempFile(
		        imageFileName,  /* prefix */
		        ".jpg",         /* suffix */
		        storageDir      /* directory */
		    );

		    return image;
		}
		
		
	}

	private class ContactClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("@content://contacts/");
			Intent intent = new Intent(Intent.ACTION_PICK, uri); 
			intent.setType(Phone.CONTENT_TYPE);
			startActivityForResult(intent, REQUEST_CONTACT);
		}
	}
}
