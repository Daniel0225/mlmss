package com.app.mlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : InputGridAdapter
 * @date : 2019/1/4  15:43
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
public class InputGridAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private boolean isDartTheme = true;

    public InputGridAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public InputGridAdapter(Context context, boolean isDartTheme) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.isDartTheme = isDartTheme;
    }

    @Override
    public int getCount() {
        return Constants.BUTTONS.length;
    }

    @Override
    public Object getItem(int position) {
        return Constants.BUTTONS[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.number_button_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvButton.setText(Constants.BUTTONS[position]);
        if(!isDartTheme){
            holder.tvButton.setBackgroundResource(R.drawable.shape_number_button_light);
            holder.tvButton.setTextColor(context.getResources().getColor(R.color.text_dark));
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvButton)
        TextView tvButton;

        ViewHolder(View view) {ButterKnife.bind(this, view);}
    }
}
