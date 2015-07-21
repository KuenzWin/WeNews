package com.kuenzWin.smartBJ.fragment;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kuenzWin.smartBJ.MainActivity;
import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BaseFragment;
import com.kuenzWin.smartBJ.base.impl.NewsCenterPage;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.NewsCenterData;

public class LeftMenuFragment extends BaseFragment implements
		OnItemClickListener {

	private List<NewsCenterData> datas;
	private ListView lv;

	private MenuAdapter adapter;
	/**
	 * �˵���ѡ���λ��
	 */
	private int selectPos;

	@Override
	protected View initView() {
		lv = new ListView(mContext);
		lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setDividerHeight(0);
		lv.setBackgroundColor(Color.BLACK);
		lv.setPadding(0, 50, 0, 0);
		lv.setSelector(android.R.color.transparent);
		return lv;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���ò˵��б������
	 * 
	 * @param datas
	 */
	public void setListData(List<NewsCenterData> datas) {
		this.datas = datas;
		adapter = new MenuAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

		switchNewsPage(0);
	}

	/**
	 * �л�����ҳ��
	 * 
	 * @param position
	 *            ����ҳ��λ��
	 */
	private void switchNewsPage(int position) {
		MainContentFragment mf = ((MainActivity) mContext)
				.getMainContentFragment();
		NewsCenterPage page = mf.getNewsCenterPage();
		page.switchPage(position);
	}

	/**
	 * �˵�������
	 * 
	 * @author ������
	 * 
	 */
	private class MenuAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = null;
			if (convertView == null) {
				tv = (TextView) View
						.inflate(mContext, R.layout.menu_item, null);
			} else {
				tv = (TextView) convertView;
			}

			tv.setEnabled(position == selectPos);
			tv.setText(datas.get(position).title);
			return tv;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MainActivity main = (MainActivity) mContext;
		selectPos = position;
		adapter.notifyDataSetChanged();
		main.getSlidingMenu().toggle();
		this.switchNewsPage(position);
	}

}
