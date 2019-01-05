package cz.logan.jutsudetection;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SimpleJutsuGridActivity extends Activity {

    //---The images to display---
    Integer[][] jutsuImages = {{R.drawable.headband_fix, R.drawable.punch, R.drawable.shadow_clone,
    R.drawable.rasengan, R.drawable.summon,R.drawable.sage_mode,R.drawable.sexy_jutsu}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        //---Assigns an adapter to provide the content for this pager---
        pager.setAdapter(new ImageAdapter(this));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);
    }

    public class ImageAdapter extends GridPagerAdapter {
        final Context mContext;

        ImageAdapter(final Context context) {
            mContext = context;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount(int i) {
            return 7;
        }

        //---Go to current column when scrolling up or down (instead of default column 0)---
        @Override
        public int getCurrentColumnForRow(int row, int currentColumn) {
            return currentColumn;
        }

        //---Return our car image based on the provided row and column---
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int row, int col) {
            ImageView imageView;
            imageView = new ImageView(mContext);
            imageView.setImageResource(jutsuImages[row][col]);
            imageView.setBackgroundColor(Color.rgb(236, 238, 242));
            viewGroup.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int i, int i2, Object o) {
            viewGroup.removeView((View) o);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view.equals(o);
        }
    }
}