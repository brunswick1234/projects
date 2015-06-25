package com.example.gomokuV1;

/**
 * Created by dell on 6/6/2014.
 * ChoosePhotoActivity will displays a grid of photos that stored in the app for player to choose from.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;
import java.util.Objects;

public class ChoosePhotoActivity extends Activity
{

    //Photos stored in the application
    private static final Integer[] photos = {R.drawable.peter,R.drawable.lois,R.drawable.stewie,
            R.drawable.chris,R.drawable.brian, R.drawable.meg,R.drawable.glenn};


    public void onCreate(Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultphotos);
        GridView gridView = (GridView)findViewById(R.id.gridviewForPhotos);
        gridView.setAdapter(new ImageAdapter(this));

        //Click the image and go back to AddOrEditPlayerActivity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                intent.putExtra("photoIndex", photos[position]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    //Adapter Class for the image grid
    class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount()
        {
            return photos.length;
        }

        public Objects getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if (convertView == null)
            {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(255, 255));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(1, 1, 1, 1);
            }
            else
            {
                imageView = (ImageView)convertView;
            }

            imageView.setImageResource(photos[position]);
            return imageView;

        }




    }










}
