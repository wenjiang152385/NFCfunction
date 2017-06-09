package com.oraro.nfcfunction.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.utils.CustomFragmentManager;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class SlidingMenuFragment extends Fragment {
    private String[] texts = new String[]{"案件委托", "添加设备"};
    private int[] imageviews = {R.mipmap.entrust, R.mipmap.add};
    public int clickPosition = -1;//默认为-1
    private ListView listview;
    private MyAdapter adapter;
    private MainActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sliding_menu_fragment,container,false);
        initView(view);
        return view;

    }
    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }
    private void initView(View view) {
        listview = (ListView) view.findViewById(R.id.list_view);
            adapter = new MyAdapter();
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.notifyDataSetChanged();
                    CustomFragmentManager.getInstance(mActivity).startFragment(position);
                    clickPosition = position;
                    mActivity.hidingmenu();
                }
            });


    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.leftmenu_item, null);
                viewHolder.iv_delay = (ImageView) convertView.findViewById(R.id.iv_delay);
                viewHolder.tv_delay = (TextView) convertView.findViewById(R.id.tv_delay);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.iv_delay.setBackgroundResource(imageviews[position]);
            viewHolder.tv_delay.setText(texts[position]);
            if (clickPosition == position) {
                convertView.setBackgroundColor(Color.parseColor("#FF474747"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#FF363636"));
            }


            return convertView;
        }
    }
    class ViewHolder {
        ImageView iv_delay;
        TextView tv_delay;
    }
}
