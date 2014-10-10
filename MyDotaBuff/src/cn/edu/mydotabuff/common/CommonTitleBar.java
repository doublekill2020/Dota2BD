package cn.edu.mydotabuff.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;

public class CommonTitleBar {
	public static int textSize = 18;
	public final static int leftId = 0X0000;
	public final static int titleId = 0X0001;
	public final static int rightId = 0X0002;
	public final static int leftImageId = 0X0003;
	private static int leftResId = 0;
	private static int rightResId = 1;
	public static float margin_left_size = DotaApplication.getApplication()
			.getResources()
			.getDimensionPixelSize(R.dimen.commontitle_marginleft_dimen);

	public static float margin_right_size = DotaApplication.getApplication()
			.getResources()
			.getDimensionPixelSize(R.dimen.commontitle_marginleft_dimen);

	public static LinearLayout getTitleLayoutLeft(Activity context) {
		LinearLayout left = (LinearLayout) context
				.findViewById(R.id.layout_left);
		if (left != null) {
			left.setVisibility(View.VISIBLE);
		}
		return left;
	}

	public static LinearLayout getTitleLayoutRight(Activity context) {
		LinearLayout right = (LinearLayout) context
				.findViewById(R.id.layout_right);
		if (right != null) {
			right.setVisibility(View.VISIBLE);
		}
		return right;
	}

	public static LinearLayout getTitleLayoutMid(Activity context) {
		LinearLayout mid = (LinearLayout) context.findViewById(R.id.layout_mid);
		if (mid != null) {
			mid.setVisibility(View.VISIBLE);
		}
		return mid;
	}

	/**
	 * 添加具有返回键和中间标题
	 * 
	 * @param context
	 * @param leftresId
	 * @param leftClickListener
	 * @param midTitle
	 */
	public static void addLeftBackAndMidTitle(Activity context, int leftresId,
			OnClickListener leftClickListener, String midTitle) {
		leftResId = leftresId;
		addLeftBackAndMidTitle(context, leftClickListener, midTitle);
	}

	public static void addLeftBackAndMidTitle(Activity context,
			OnClickListener leftClickListener, String midTitle) {
		// 添加左边
		ImageButton left = new ImageButton(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		left.setLayoutParams(lp);
		if (leftResId == 0) {
			// 设置默认回退按钮
			leftResId = R.drawable.back_btn_style;
		}
		left.setId(leftId);
		left.setPadding(0, 0, 0, 0);
		left.setBackgroundResource(0);
		left.setImageResource(leftResId);
		if (CommonTitleBar.getTitleLayoutLeft(context) != null) {
			CommonTitleBar.getTitleLayoutLeft(context).addView(left);
		}

		left.setOnClickListener(leftClickListener);

		// 添加中间
		TextView title = new TextView(context);
		title.setTextSize(textSize);
		title.setId(titleId);
		title.setTextColor(context.getResources().getColor(R.color.white_color));
		title.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		title.setText(midTitle);
		if (CommonTitleBar.getTitleLayoutMid(context) != null) {
			CommonTitleBar.getTitleLayoutMid(context).addView(title);
		}
	}

	/**
	 * 添加带有右边图片按钮的左中右titlebar
	 * 
	 * @param context
	 * @param leftresId
	 * @param leftClickListener
	 * @param midTitle
	 * @param rightresId
	 * @param rightClickListener
	 */
	public static void addRightImgToTitleBar(Activity context, int leftresId,
			OnClickListener leftClickListener, String midTitle, int rightresId,
			OnClickListener rightClickListener) {
		if (leftresId != 0) {
			addLeftBackAndMidTitle(context, leftresId, leftClickListener,
					midTitle);
		} else {
			addLeftBackAndMidTitle(context, leftClickListener, midTitle);
		}
		// 添加右边
		CommonTitleBar.rightResId = rightresId;
		ImageButton right = new ImageButton(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, (int) margin_right_size, 0);
		right.setLayoutParams(lp);
		right.setPadding(6, 6, 22, 6);
		right.setId(rightId);
		right.setBackgroundResource(0);
		right.setImageResource(rightResId);
		right.setOnClickListener(rightClickListener);
		CommonTitleBar.getTitleLayoutRight(context).addView(right);
	}

	/**
	 * 添加右边文本的左中右Titlebar
	 * 
	 * @param context
	 * @param leftresId
	 * @param leftClickListener
	 * @param midTitle
	 * @param rightText
	 * @param rightClickListener
	 */
	public static void addRightTextToTitleBar(Activity context, int leftresId,
			OnClickListener leftClickListener, String midTitle,
			String rightText, OnClickListener rightClickListener) {
		if (leftresId != 0) {
			addLeftBackAndMidTitle(context, leftresId, leftClickListener,
					midTitle);
		} else {
			addLeftBackAndMidTitle(context, leftClickListener, midTitle);
		}

		// 添加右边文字
		TextView rightBar = new TextView(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		// lp.setMargins(0, 0, 0 , 0);
		rightBar.setLayoutParams(lp);
		rightBar.setPadding(0, 0, 0, 0);
		rightBar.setText(rightText);
		rightBar.setTextSize(18);
		rightBar.setTextColor(context.getResources().getColor(
				R.color.right_textcolor));
		rightBar.setId(CommonTitleBar.rightId);
		rightBar.setOnClickListener(rightClickListener);
		if (CommonTitleBar.getTitleLayoutRight(context) != null) {
			CommonTitleBar.getTitleLayoutRight(context).addView(rightBar);
		}
	}

	/**
	 * 添加具有左右切换开关的标题栏
	 */
	public static void addSwitcherBtnToTitleBar(Activity context,
			OnCheckedChangeListener listener, String leftText, String rightText) {
		LayoutInflater inflater = context.getLayoutInflater();
		View child = inflater.inflate(R.layout.common_titlebar_switcher, null);
		RadioGroup group = (RadioGroup) child
				.findViewById(R.id.titlebar_switch_group);
		group.setOnCheckedChangeListener(listener);
		RadioButton left = (RadioButton) group
				.findViewById(R.id.titlebar_switch_left);
		left.setText(leftText);
		RadioButton right = (RadioButton) group
				.findViewById(R.id.titlebar_switch_right);
		right.setText(rightText);
		CommonTitleBar.getTitleLayoutMid(context).addView(child);
	}

	/**
	 * 添加具有右边按钮的切换开关的标题栏
	 */
	public static void addRightImgSwitcherBtnToTitleBar(Activity context,
			OnCheckedChangeListener listener, String leftText,
			String rightText, int rightresId, OnClickListener rightClickListener) {
		addSwitcherBtnToTitleBar(context, listener, leftText, rightText);
		// 添加右边
		CommonTitleBar.rightResId = rightresId;
		ImageButton right = new ImageButton(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins((int) margin_right_size, 0, (int) margin_right_size, 0);
		right.setLayoutParams(lp);
		right.setBackgroundResource(0);
		right.setImageResource(rightResId);
		right.setOnClickListener(rightClickListener);
		CommonTitleBar.getTitleLayoutRight(context).addView(right);

	}

	/**
	 * 在整体布局中，总有3个子布局，分别为左边布局：内含2个子控件-1.image，2.textView，和其onClick事件。
	 * 中间布局：内含一个textView，和其onClick事件。 右边布局：内含一个textView，和其onClick事件。
	 * 
	 * @param context
	 *            调用它的Activity
	 * @param leftLayoutListener
	 *            左边布局的onClick事件
	 * @param leftImageId
	 *            左边布局中的image的id，可以自定义，默认为0
	 * @param leftTextContent
	 *            左边布局中TextView的文本
	 * @param midLayoutListener
	 *            中间布局的onClick事件
	 * @param midTextContent
	 *            中间布局的文本
	 * @param rightLayoutListener
	 *            右边布局的onClick事件
	 * @param rightTextContent
	 *            右边布局的文本
	 */
	@SuppressLint("NewApi")
	public static void addCurrencyTitleBar(Activity context,
			OnClickListener leftLayoutListener, int leftImageId,
			String leftTextContent, OnClickListener midLayoutListener,
			String midTextContent, OnClickListener rightLayoutListener,
			String rightTextContent) {
		// 添加左边布局图片
		ImageButton leftImage = new ImageButton(context);
		LinearLayout.LayoutParams imagelp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		imagelp.setMargins(0, 0, 0, 0);
		leftImage.setLayoutParams(imagelp);
		if (leftImageId == 0) {
			// 设置默认回退按钮
			leftImageId = R.drawable.back_btn_style;
		} else if (leftImageId == -1) {// 如果值为-1则隐藏
			leftImage.setVisibility(View.INVISIBLE);
			leftImageId = R.drawable.back_btn_style;
		}
		leftImage.setId(CommonTitleBar.leftImageId);
		leftImage.setPadding(0, 0, 0, 0);
		leftImage.setBackgroundResource(0);
		leftImage.setImageResource(leftImageId);
		leftImage.setClickable(false);
		// 添加左边布局文字
		TextView leftText = new TextView(context);
		LinearLayout.LayoutParams lefttextlp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		lefttextlp.setMargins(5, 0, 0, 0);
		leftText.setId(leftId);
		leftText.setLayoutParams(lefttextlp);
		leftText.setText(leftTextContent);
		leftText.setTextSize(textSize);
		leftText.setTextColor(context.getResources().getColor(
				R.color.white_color));

		if (CommonTitleBar.getTitleLayoutLeft(context) != null) {
			CommonTitleBar.getTitleLayoutLeft(context).addView(leftImage);
			CommonTitleBar.getTitleLayoutLeft(context).addView(leftText);
			if (leftLayoutListener != null) {
				CommonTitleBar.getTitleLayoutLeft(context).setOnClickListener(
						leftLayoutListener);
			}
		}

		// 添加中间局部文字
		if (midTextContent != null && !midTextContent.isEmpty()) {
			TextView midText = new TextView(context);
			LinearLayout.LayoutParams midtextlp = new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			midtextlp.setMargins(0, 0, 0, 0);
			midText.setId(titleId);
			midText.setLayoutParams(midtextlp);
			midText.setText(midTextContent);
			midText.setTextSize(textSize);
			midText.setTextColor(context.getResources().getColor(
					R.color.white_color));
			if (CommonTitleBar.getTitleLayoutMid(context) != null) {
				CommonTitleBar.getTitleLayoutMid(context).addView(midText);
				if (midLayoutListener != null) {
					CommonTitleBar.getTitleLayoutMid(context)
							.setOnClickListener(midLayoutListener);
				}
			}
		}
		// 添加右边布局文字
		if (rightTextContent != null) {
			TextView righttext = new TextView(context);
			LinearLayout.LayoutParams righttextlp = new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			righttextlp.setMargins(0, 0, 0, 0);
			righttext.setId(rightId);
			righttext.setClickable(false);
			// righttext.setEnabled(false);
			righttext.setLayoutParams(righttextlp);
			righttext.setText(rightTextContent);
			righttext.setTextSize(textSize);
			righttext.setTextColor(context.getResources().getColor(
					R.color.white_color));
			if (CommonTitleBar.getTitleLayoutRight(context) != null) {
				if (rightLayoutListener != null) {
					CommonTitleBar.getTitleLayoutRight(context)
							.setOnClickListener(rightLayoutListener);
				}
				CommonTitleBar.getTitleLayoutRight(context).addView(righttext);
			}
		}
	}

}
