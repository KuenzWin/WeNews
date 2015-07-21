package com.kuenzWin.smartBJ.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.provider.MediaStore.Audio.Radio;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kuenzWin.smartBJ.R;

public class CustomRadioDialog extends Dialog implements OnItemClickListener {

	private Context context;
	private ListView lv_radio;
	private OnListItemClickListener onListItemClickListener;
	/**
	 * ѡ���λ��
	 */
	private int selected;
	private RadioAdapter adapter;

	public CustomRadioDialog(Context context, int defaultSelected) {
		super(context, R.style.dialog_style);
		this.context = context;
		this.selected = defaultSelected;
		setContentView(R.layout.view_dialog_radio);

		TextView view_dialog_title = (TextView) this
				.findViewById(R.id.view_dialog_title);
		view_dialog_title.setText("ѡ������");

		lv_radio = (ListView) this.findViewById(R.id.lv_radio);
	}

	/**
	 * ���öԻ����е�ListView��������
	 * 
	 * @param adapter
	 *            �Ի����е�ListView��������
	 * @return �Ի�����
	 */
	public CustomRadioDialog setData(String[] data) {
		adapter = new RadioAdapter(data);
		lv_radio.setAdapter(adapter);
		lv_radio.setOnItemClickListener(this);
		return this;
	}

	private class RadioAdapter extends BaseAdapter {

		private String[] data;

		public RadioAdapter(String[] data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.length;
		}

		@Override
		public Object getItem(int position) {
			return data[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(context);
			}
			TextView tv = (TextView) convertView;
			tv.setTextSize(20);
			tv.setText(data[position]);
			tv.setPadding(10, 5, 5, 0);
			if (position == selected) {
				tv.setTextColor(Color.BLUE);
			} else {
				tv.setTextColor(Color.BLACK);
			}
			return tv;
		}
	}

	/**
	 * ���öԻ����е�ListView��OnItemClickListener
	 * 
	 * @param listener
	 *            �Ի����е�ListView��OnItemClickListener
	 * @return �Ի�����
	 */
	public interface OnListItemClickListener {
		void onItemClick(int position);
	}

	public void setOnListItemClickListener(
			OnListItemClickListener onListItemClickListener) {
		this.onListItemClickListener = onListItemClickListener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selected = position;
		adapter.notifyDataSetChanged();
		if (onListItemClickListener != null) {
			onListItemClickListener.onItemClick(position);
		}
		this.dismiss();
	}
}
