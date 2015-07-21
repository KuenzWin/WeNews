package com.kuenzWin.smartBJ.view;

//package com.kuenzWin.smartBJ.view;
//
//import java.text.SimpleDateFormat;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.kuenzWin.smartBJ.R;
//
///**
// * �Զ�������ˢ��ListView
// * 
// * @author Administrator
// * 
// */
//public class RefreshListView extends ListView {
//
//	/**
//	 * ����ͷ���ֶ���
//	 */
//	private LinearLayout mHeaderView;
//	/**
//	 * ��ӵ��Զ���ͷ����
//	 */
//	private View mCustomHeaderView;
//	/**
//	 * ͷ���ֵļ�ͷ
//	 */
//	private ImageView ivArrow;
//	/**
//	 * ͷ���ֵĽ���Ȧ
//	 */
//	private ProgressBar mProgressbar;
//	/**
//	 * ͷ���ֵ�״̬
//	 */
//	private TextView tvState;
//	/**
//	 * ͷ���ֵ����ˢ��ʱ��
//	 */
//	private TextView tvLastUpdateTime;
//
//	/**
//	 * ����ʱy���ƫ����
//	 */
//	private int downY = -1;
//	/**
//	 * ����ͷ���ֵĸ߶�
//	 */
//	private int mPullDownHeaderViewHeight;
//	/**
//	 * ����ͷ���ֵ�view����
//	 */
//	private View mPullDownHeaderView;
//	/**
//	 * ������ʱ����ת����
//	 */
//	private RotateAnimation upAnimation;
//	/**
//	 * ������ʱ����ת����
//	 */
//	private RotateAnimation downAnimation;
//
//	/**
//	 * ����ˢ��,ֵΪ0
//	 */
//	private static final int PULL_DOWN = 0;
//	/**
//	 * �ͷ�ˢ��,ֵΪ1
//	 */
//	private static final int RELEASE_REFRESH = 1;
//	/**
//	 * ����ˢ����...,ֵΪ2
//	 */
//	private static final int REFRESHING = 2;
//
//	/**
//	 * ��ǰ����ͷ���ֵ�״̬, Ĭ��Ϊ: ����ˢ��״̬
//	 */
//	private int currentState = PULL_DOWN;
//
//	/**
//	 * ʱ���ʽ��
//	 */
//	private SimpleDateFormat sdf;
//
//	/**
//	 * ˢ�½ӿ�
//	 */
//	private OnRefreshListener onRefreshListener;
//	/**
//	 * ���ؽӿ�
//	 */
//	private OnLoadListener onLoadListener;
//	private boolean isEnabledPullDownRefresh = true;
//
//	public RefreshListView(Context context) {
//		super(context);
//	}
//
//	public RefreshListView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.initHead();
//	}
//
//	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}
//
//	/**
//	 * ��ʼ��ListView��ͷ��
//	 */
//	private void initHead() {
//		mHeaderView = (LinearLayout) View.inflate(getContext(),
//				R.layout.refresh_header_view, null);
//		mPullDownHeaderView = mHeaderView
//				.findViewById(R.id.ll_refresh_header_view_pull_down);
//		ivArrow = (ImageView) mHeaderView
//				.findViewById(R.id.iv_refresh_header_view_pull_down_arrow);
//		mProgressbar = (ProgressBar) mHeaderView
//				.findViewById(R.id.pb_refresh_header_view_pull_down);
//		tvState = (TextView) mHeaderView
//				.findViewById(R.id.tv_refresh_header_view_pull_down_state);
//		tvLastUpdateTime = (TextView) mHeaderView
//				.findViewById(R.id.tv_refresh_header_view_pull_down_last_update_time);
//
//		tvLastUpdateTime.setText("���ˢ��ʱ��:");
//
//		mPullDownHeaderView.measure(0, 0);
//		// �õ�����ˢ��ͷ���ֵĸ߶�
//		mPullDownHeaderViewHeight = mPullDownHeaderView.getMeasuredHeight();
//		System.out.println("height:" + mPullDownHeaderViewHeight);
//		mPullDownHeaderView.setPadding(0, -1 * mPullDownHeaderViewHeight, 0, 0);
//
//		this.addHeaderView(mHeaderView);
//
//		this.initAnimation();
//	}
//
//	/**
//	 * ��ʼ������
//	 */
//	private void initAnimation() {
//		upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
//				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		upAnimation.setDuration(500);
//		upAnimation.setFillAfter(true);
//
//		downAnimation = new RotateAnimation(-180, -360,
//				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//				0.5f);
//		downAnimation.setDuration(500);
//		downAnimation.setFillAfter(true);
//	}
//
//	public void addCustomHeaderView(View view) {
//		this.addHeaderView(view);
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		System.out.println("action:" + ev.getAction());
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			downY = (int) ev.getY();
//			System.out.println("down:" + downY);
//			break;
//		case MotionEvent.ACTION_MOVE:
//			int moveY = (int) ev.getY();
//			System.out.println("move:" + moveY);
//			// ���û����������ˢ�¹���, ֱ������switch
//			if (!isEnabledPullDownRefresh) {
//				break;
//			}
//			// �ƶ��Ĳ�ֵ
//			int diffY = moveY - downY;
//			System.out.println("diff:" + diffY);
//			if (diffY > 0 && getFirstVisiblePosition() == 0) {
//				int paddingTop = -mPullDownHeaderViewHeight + diffY;
//				if (paddingTop > 0 && currentState != RELEASE_REFRESH) {
//					currentState = RELEASE_REFRESH;
//					refreshStateChanged();
//				} else if (paddingTop < 0 && currentState != PULL_DOWN) {
//					currentState = PULL_DOWN;
//					refreshStateChanged();
//				}
//
//				mPullDownHeaderView.setPadding(0, paddingTop, 0, 0);
//				return true;
//			}
//			break;
//
//		case MotionEvent.ACTION_UP:
//			downY = -1;
//			// ���״̬��������ˢ�£���ͷ����û����ʾ��ȫ����������ͷ����
//			if (currentState == PULL_DOWN) {
//				// ��ǰ״̬������ˢ��״̬, ��ͷ��������.
//				mPullDownHeaderView.setPadding(0, -mPullDownHeaderViewHeight,
//						0, 0);
//			} else if (currentState == RELEASE_REFRESH) {
//				// ��ǰ״̬���ͷ�ˢ��, ��ͷ������ȫ��ʾ, ���ҽ��뵽����ˢ����״̬
//				mPullDownHeaderView.setPadding(0, 0, 0, 0);
//				currentState = REFRESHING;
//				refreshStateChanged();
//
//				// �����û��Ļص��ӿ�
//				if (onRefreshListener != null) {
//					onRefreshListener.onRefresh();
//				}
//			}
//			break;
//		default:
//			break;
//		}
//		return super.onTouchEvent(ev);
//	}
//
//	/**
//	 * ��ͷ����״̬�ı�ʱ�����������
//	 */
//	private void refreshStateChanged() {
//		switch (currentState) {
//		// ����ˢ��״̬
//		case PULL_DOWN:
//			ivArrow.startAnimation(downAnimation);
//			tvState.setText("����ˢ��");
//			break;
//		// �ͷ�ˢ��״̬
//		case RELEASE_REFRESH:
//			ivArrow.startAnimation(upAnimation);
//			tvState.setText("�ͷ�ˢ��");
//			break;
//		// ����ˢ����
//		case REFRESHING:
//			ivArrow.clearAnimation();
//			ivArrow.setVisibility(View.GONE);
//			mProgressbar.setVisibility(View.VISIBLE);
//			tvState.setText("����ˢ����..");
//			break;
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * ����ˢ�¼�����
//	 * 
//	 * @param onRefreshListener
//	 *            ˢ�¼�����
//	 */
//	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
//		this.onRefreshListener = onRefreshListener;
//	}
//
//	/**
//	 * ���ü��ؼ�����
//	 * 
//	 * @param onLoadListener
//	 *            ���ؼ�����
//	 */
//	public void setOnLoadListener(OnLoadListener onLoadListener) {
//		this.onLoadListener = onLoadListener;
//	}
//
//	/**
//	 * ˢ�½ӿ�
//	 * 
//	 * @author Administrator
//	 * 
//	 */
//	public interface OnRefreshListener {
//		/**
//		 * ˢ�·�����֧�ֻص�
//		 */
//		public void onRefresh();
//	}
//
//	/**
//	 * ���ؽӿ�
//	 * 
//	 * @author Administrator
//	 * 
//	 */
//	public interface OnLoadListener {
//		/**
//		 * ���ط�����֧�ֻص�
//		 */
//		public void onLoad();
//	}
//
//}

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kuenzWin.smartBJ.R;

public class RefreshListView extends ListView implements OnScrollListener {
	private LinearLayout mHeaderView; // ����ͷ���ֶ���
	private View mCustomHeaderView; // ��ӵ��Զ���ͷ����
	private int downY = -1; // ����ʱy���ƫ����
	private int mPullDownHeaderViewHeight; // ����ͷ���ֵĸ߶�
	private View mPullDownHeaderView; // ����ͷ���ֵ�view����

	private final int PULL_DOWN = 0; // ����ˢ��
	private final int RELEASE_REFRESH = 1; // �ͷ�ˢ��
	private final int REFRESHING = 2; // ����ˢ����..

	private int currentState = PULL_DOWN; // ��ǰ����ͷ���ֵ�״̬, Ĭ��Ϊ: ����ˢ��״̬
	private RotateAnimation upAnim; // ������ת�Ķ���
	private RotateAnimation downAnim; // ������ת�Ķ���
	private ImageView ivArrow; // ͷ���ֵļ�ͷ
	private ProgressBar mProgressbar; // ͷ���ֵĽ���Ȧ
	private TextView tvState; // ͷ���ֵ�״̬
	private TextView tvLastUpdateTime; // ͷ���ֵ����ˢ��ʱ��
	private int mListViewYOnScreen = -1; // ListView����Ļ��y���ֵ

	private OnRefreshListener onRefreshListener; // ����ˢ�ºͼ��ظ���Ļص��ӿ�
	private View mFooterView; // �Ų��ֶ���
	private int mFooterViewHeight; // �Ų��ֵĸ߶�
	private boolean isLoadingMore = false; // �Ƿ����ڼ��ظ�����, Ĭ��Ϊ: false
	private boolean isEnabledPullDownRefresh = false; // �Ƿ���������ˢ��
	private boolean isEnabledLoadingMore = false; // �Ƿ����ü��ظ���

	public RefreshListView(Context context) {
		super(context);
		initHeader();
		initFooter();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeader();
		initFooter();
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeader();
		initFooter();
	}

	/**
	 * ��ʼ���Ų���
	 */
	private void initFooter() {
		mFooterView = View.inflate(getContext(), R.layout.refresh_footer_view,
				null);
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();

		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
		this.addFooterView(mFooterView);

		// ����ǰListview����һ�������ļ����¼�
		this.setOnScrollListener(this);
	}

	/**
	 * ��ʼ������ˢ��ͷ����
	 */
	private void initHeader() {
		mHeaderView = (LinearLayout) View.inflate(getContext(),
				R.layout.refresh_header_view, null);
		mPullDownHeaderView = mHeaderView
				.findViewById(R.id.ll_refresh_header_view_pull_down);
		ivArrow = (ImageView) mHeaderView
				.findViewById(R.id.iv_refresh_header_view_pull_down_arrow);
		mProgressbar = (ProgressBar) mHeaderView
				.findViewById(R.id.pb_refresh_header_view_pull_down);
		tvState = (TextView) mHeaderView
				.findViewById(R.id.tv_refresh_header_view_pull_down_state);
		tvLastUpdateTime = (TextView) mHeaderView
				.findViewById(R.id.tv_refresh_header_view_pull_down_last_update_time);

		tvLastUpdateTime.setText("���ˢ��ʱ��:" + getCurrentTime());

		// ��������ˢ��ͷ�ĸ߶�.
		mPullDownHeaderView.measure(0, 0);
		// �õ�����ˢ��ͷ���ֵĸ߶�
		mPullDownHeaderViewHeight = mPullDownHeaderView.getMeasuredHeight();
		System.out.println("ͷ���ֵĸ߶�: " + mPullDownHeaderViewHeight);

		// ����ͷ����
		mPullDownHeaderView.setPadding(0, -mPullDownHeaderViewHeight, 0, 0);

		this.addHeaderView(mHeaderView);

		// ��ʼ������
		initAnimation();
	}

	private void initAnimation() {
		upAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		upAnim.setDuration(500);
		upAnim.setFillAfter(true);

		downAnim = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnim.setDuration(500);
		downAnim.setFillAfter(true);
	}

	/**
	 * ���һ���Զ����ͷ����.
	 * 
	 * @param v
	 */
	public void addCustomHeaderView(View v) {
		this.mCustomHeaderView = v;
		mHeaderView.addView(v);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (downY == -1) {
				downY = (int) ev.getY();
			}

			// ���û����������ˢ�¹���, ֱ������switch
			if (!isEnabledPullDownRefresh) {
				break;
			}

			// ��ǰ����ˢ����, ����switch
			if (currentState == REFRESHING) {
				break;
			}

			// �ж���ӵ��ֲ�ͼ�Ƿ���ȫ��ʾ��, ���û����ȫ��ʾ,
			// ��ִ����������ͷ�Ĵ���, ��תswitch���, ִ�и�Ԫ�ص�touch�¼�.
			if (mCustomHeaderView != null) {
				int[] location = new int[2]; // 0λ��x���ֵ, 1λ��y���ֵ

				if (mListViewYOnScreen == -1) {
					// ��ȡListview����Ļ��y���ֵ.
					this.getLocationOnScreen(location);
					mListViewYOnScreen = location[1];
					// System.out.println("ListView����Ļ�е�y���ֵ: " +
					// mListViewYOnScreen);
				}

				// ��ȡmCustomHeaderView����Ļy���ֵ.
				mCustomHeaderView.getLocationOnScreen(location);
				int mCustomHeaderViewYOnScreen = location[1];

				if (mListViewYOnScreen > mCustomHeaderViewYOnScreen) {
					break;
				}
			}

			int moveY = (int) ev.getY();

			// �ƶ��Ĳ�ֵ
			int diffY = moveY - downY;

			/**
			 * ���diffY��ֵ����0, ������ק ���� ��ǰListView�ɼ��ĵ�һ����Ŀ����������0 �Ž�������ͷ�Ĳ���
			 */
			if (diffY > 0 && getFirstVisiblePosition() == 0) {
				int paddingTop = -mPullDownHeaderViewHeight + diffY;
				// System.out.println("paddingTop: " + paddingTop);

				if (paddingTop > 0 && currentState != RELEASE_REFRESH) {
					System.out.println("��ȫ��ʾ��, ���뵽�ͷ�ˢ��");
					currentState = RELEASE_REFRESH;
					refreshPullDownHeaderState();
				} else if (paddingTop < 0 && currentState != PULL_DOWN) {
					System.out.println("������ʾ��, ���뵽����ˢ��");
					currentState = PULL_DOWN;
					refreshPullDownHeaderState();
				}
				mPullDownHeaderView.setPadding(0, paddingTop, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			downY = -1;
			if (currentState == PULL_DOWN) {
				// ��ǰ״̬������ˢ��״̬, ��ͷ��������.
				mPullDownHeaderView.setPadding(0, -mPullDownHeaderViewHeight,
						0, 0);
			} else if (currentState == RELEASE_REFRESH) {
				// ��ǰ״̬���ͷ�ˢ��, ��ͷ������ȫ��ʾ, ���ҽ��뵽����ˢ����״̬
				mPullDownHeaderView.setPadding(0, 0, 0, 0);
				currentState = REFRESHING;
				refreshPullDownHeaderState();

				// �����û��Ļص��ӿ�
				if (onRefreshListener != null) {
					onRefreshListener.onPullDownRefresh();
				}
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ����currentState��ǰ��״̬, ��ˢ��ͷ���ֵ�״̬
	 */
	private void refreshPullDownHeaderState() {
		switch (currentState) {
		case PULL_DOWN: // ����ˢ��״̬
			ivArrow.startAnimation(downAnim);
			tvState.setText("����ˢ��");
			break;
		case RELEASE_REFRESH: // �ͷ�ˢ��״̬
			ivArrow.startAnimation(upAnim);
			tvState.setText("�ͷ�ˢ��");
			break;
		case REFRESHING: // ����ˢ����
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.INVISIBLE);
			mProgressbar.setVisibility(View.VISIBLE);
			tvState.setText("����ˢ����..");
			break;
		default:
			break;
		}
	}

	/**
	 * ������ˢ�����ʱ���ô˷���
	 */
	public void onRefreshFinish() {
		if (isLoadingMore) {
			// ��ǰ�Ǽ��ظ���Ĳ���, ���ؽŲ���
			isLoadingMore = false;
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
		} else {
			// ��ǰ������ˢ�µĲ���, ����ͷ���ֺ͸�λ����.
			mPullDownHeaderView.setPadding(0, -mPullDownHeaderViewHeight, 0, 0);
			currentState = PULL_DOWN;
			mProgressbar.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			tvState.setText("����ˢ��");
			tvLastUpdateTime.setText("���ˢ��ʱ��: " + getCurrentTime());
		}

	}

	/**
	 * ��ȡ��ǰʱ��, ��ʽΪ: 1990-09-09 09:09:09
	 * 
	 * @return
	 */
	private String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * ����ˢ�µļ����¼�
	 * 
	 * @param listener
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	/**
	 * @author andong ˢ�»ص��ӿ�
	 */
	public interface OnRefreshListener {

		/**
		 * ������ˢ��ʱ �����˷���, ʵ�ִ˷�����ץȡ����.
		 */
		public void onPullDownRefresh();

		/**
		 * �����ظ���ʱ, �����˷���.
		 */
		public void onLoadingMore();

	}

	/**
	 * ��������״̬�ı�ʱ�����˷���. scrollState ��ǰ��״̬
	 * 
	 * SCROLL_STATE_IDLE ͣ�� SCROLL_STATE_TOUCH_SCROLL �������� SCROLL_STATE_FLING
	 * ���Ի���(�͵�һ��)
	 * 
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// ������״̬�Ǵ���ֹͣ���߿��ٻ���ʱ, ���ҵ�ǰ��Ļ���һ����ʾ��item��������ListView����Ŀ-1
		if ((scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING)
				&& getLastVisiblePosition() == (getCount() - 1)) {

			if (!isLoadingMore) {
				System.out.println("��ǰ�������ײ���, ���Լ��ظ���");
				isLoadingMore = true;
				// ��ʾ�Ų���
				mFooterView.setPadding(0, 0, 0, 0);

				// �������ײ�, �ýŲ�����ʾ����
				setSelection(getCount() - 1);

				// �����û��Ļص��¼�, ���û�ȥ���ظ�������
				if (onRefreshListener != null) {
					onRefreshListener.onLoadingMore();
				}
			}
		}
	}

	/**
	 * ������ʱ�����˷���
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	/**
	 * �Ƿ���������ˢ�µĹ���
	 * 
	 * @param isEnabled
	 *            true ����
	 */
	public void isEnabledPullDownRefresh(boolean isEnabled) {
		isEnabledPullDownRefresh = isEnabled;
	}

	/**
	 * �Ƿ����ü��ظ���
	 * 
	 * @param isEnabled
	 */
	public void isEnabledLoadingMore(boolean isEnabled) {
		isEnabledLoadingMore = isEnabled;
	}

}
