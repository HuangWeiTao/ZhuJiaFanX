package zhujiafanx.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zhujiafanx.demo.R;

public class GetImageActivity extends Activity {

    private final static int cameraRequestCode=2;

    private final static int galleryRequestCode=3;

    public final static String ImagePathConstant="path";

    @InjectView(R.id.btn_from_camera)
    Button cameraButton;

    @InjectView(R.id.btn_from_gallery)
    Button galleryButton;

    @InjectView(R.id.btn_image_ok)
    ImageButton okButton;

    @InjectView(R.id.btn_image_cancel)
    ImageButton cancelButton;

    @InjectView(R.id.iv_preview)
    ImageView preview;


    private String imageRelativePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_from_camera)
    public void onCameraButtonClick(View v) {
        call_camera();
    }

    @OnClick(R.id.btn_from_gallery)
    public void onGalleryButtonClick(View v) {
        call_gallery();
    }

    @OnClick(R.id.btn_image_ok)
    public void onOKButtonClick(View v) {
        Intent intent = new Intent();
        intent.putExtra(ImagePathConstant, imageRelativePath);
        setResult(RESULT_OK, intent);

        finish();
    }

    @OnClick(R.id.btn_image_cancel)
    public void onCancelButtonClick(View v) {
        imageRelativePath = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode== Activity.RESULT_OK) {

            if (requestCode == cameraRequestCode) {
                this.imageRelativePath = save_camera_image((Bitmap) data.getExtras().get("data"));
            }
            else if(requestCode==galleryRequestCode)
            {
                Uri selectedImage=data.getData();

                this.imageRelativePath = get_gallery_image(selectedImage);
            }

            //show the select image
            Bitmap bitmap = BitmapFactory.decodeFile(this.imageRelativePath);
            if(bitmap!=null) {
                preview.setImageBitmap(bitmap);
            }
        }
    }

    private String save_camera_image(Bitmap bitmap)
    {
        String filepath=null;

        String filename= UUID.randomUUID().toString()+".png";

        try
        {

            File rootPath=new File(getFilesDir(),"images");
            if(!rootPath.exists())
            {
                rootPath.mkdirs();
            }

            File dataFile=new File(rootPath,filename);

            filepath=dataFile.getPath();

            FileOutputStream stream = new FileOutputStream(dataFile,false);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        }
        catch(IOException ex)
        {
            filepath=null;
        }

        return filepath;
    }

    private String get_gallery_image(Uri imageUri)
    {
        String[] filePathColumn={MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(imageUri,filePathColumn,null,null,null);
        cursor.moveToFirst();

        int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
        String filePath=cursor.getString(columnIndex);

        cursor.close();

        return filePath;
    }

    private void call_camera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,cameraRequestCode);
    }

    private void call_gallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, galleryRequestCode);
    }
}
