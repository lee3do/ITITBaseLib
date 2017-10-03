package io.itit.androidlibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//表情
public class ExpressionUtils {
	public static final String f_static_00 = "[大笑]";
	public static final String f_static_01 = "[):]";
	public static final String f_static_02 = "[:D]";
	public static final String f_static_03 = "[;)]";
	public static final String f_static_04 = "[:-o]";
	public static final String f_static_05 = "[:p]";
	public static final String f_static_06 = "[(H)]";
	public static final String f_static_07 = "[:@]";
	public static final String f_static_08 = "[:s]";
	public static final String f_static_09 = "[:$]";
	public static final String f_static_010 = "[:(]";
	public static final String f_static_011 = "[:'(]";
	public static final String f_static_012 = "[:|]";
	public static final String f_static_013 = "[(a)]";
	public static final String f_static_014 = "[8o|]";
	public static final String f_static_015 = "[8-|]";
	public static final String f_static_016 = "[+o(]";
	public static final String f_static_017 = "[<o)]";
	public static final String f_static_018 = "[|-)]";
	public static final String f_static_019 = "[*-)]";
	public static final String f_static_020 = "[:-#]";
	public static final String f_static_021 = "[:-*]";
	public static final String f_static_022 = "[^o)]";
	public static final String f_static_023 = "[8-)]";
	public static final String f_static_024 = "[(|)]";
	public static final String f_static_025 = "[(u)]";
	public static final String f_static_026 = "[(S)]";
	public static final String f_static_027 = "[(*)]";
	public static final String f_static_028 = "[(#)]";
	public static final String f_static_029 = "[(R)]";
	public static final String f_static_030 = "[({)]";
	public static final String f_static_031 = "[(})]";
	public static final String f_static_032 = "[(k)]";
	public static final String f_static_033 = "[(F)]";
	public static final String f_static_034 = "[(W)]";
	public static final String f_static_035 = "[(D)]";

	public static final String f_static_036 = "[(D1)]";
	public static final String f_static_037 = "[(D2)]";
	public static final String f_static_038 = "[(D3)]";
	public static final String f_static_039 = "[(D4)]";
	public static final String f_static_040 = "[(D5)]";
	public static final String f_static_041 = "[(D6)]";
	public static final String f_static_042 = "[(D7)]";
	public static final String f_static_043 = "[(D8)]";
	public static final String f_static_044 = "[(D9)]";
	public static final String f_static_045 = "[(D10)]";
	public static final String f_static_046 = "[(D11)]";
	public static final String f_static_047 = "[(D12)]";
	public static final String f_static_048 = "[(D13)]";
	public static final String f_static_049 = "[(D14)]";
	public static final String f_static_050 = "[(D15)]";
	public static final String f_static_051 = "[(D16)]";
	public static final String f_static_052 = "[(D17)]";
	public static final String f_static_053 = "[(D18)]";
	public static final String f_static_054 = "[(D19)]";
	public static final String f_static_055 = "[(D20)]";
	public static final String f_static_056 = "[(D21)]";
	public static final String f_static_057 = "[(D22)]";
	public static final String f_static_058 = "[(D23)]";
	public static final String f_static_059 = "[(D24)]";
	public static final String f_static_060 = "[(D25)]";
	public static final String f_static_061 = "[(D26)]";
	public static final String f_static_062 = "[(D27)]";

	public static final String f_dynamic_001 = "害羞";
	public static final String f_dynamic_002 = "呲牙";
	public static final String f_dynamic_003 = "疑问";
	public static final String f_dynamic_004 = "亲亲";
	public static final String f_dynamic_005 = "大笑";
	public static final String f_dynamic_006 = "睡觉";
	public static final String f_dynamic_007 = "奋斗";
	public static final String f_dynamic_008 = "大哭";
	public static final String f_dynamic_009 = "喜欢";
	public static final String f_dynamic_010 = "你好";
	public static final String f_dynamic_011 = "酷";
	public static final String f_dynamic_012 = "调皮";
	public static final String f_dynamic_013 = "发火";
	public static final String f_dynamic_014 = "惊吓";
	public static final String f_dynamic_015 = "委屈";
	public static final String f_dynamic_016 = "赞";
	public static final String f_dynamic_017 = "谢谢";
	public static final String f_dynamic_018 = "再见";
	public static final String f_dynamic_019 = "晕";
	
	

	private static final Factory spannableFactory = Factory
			.getInstance();

	public static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();
	public static final ArrayList<Integer> dynamics = new ArrayList<Integer>();
	
	private static Map<Integer,String> resStrMap = new HashMap<Integer,String>();
	
	public static final int MAX_DEFAUT_ICON_NUM = 21;
	public static final int MAX_DYNAMIC_ICON_NUM = 8;
	
//	static {
//		dynamics.add(R.drawable.tsj_1);
//		dynamics.add(R.drawable.tsj_2);
//		dynamics.add(R.drawable.tsj_3);
//		dynamics.add(R.drawable.tsj_4);
//		dynamics.add(R.drawable.tsj_5);
//		dynamics.add(R.drawable.tsj_6);
//		dynamics.add(R.drawable.tsj_7);
//		dynamics.add(R.drawable.tsj_8);
//		dynamics.add(R.drawable.tsj_9);
//		dynamics.add(R.drawable.tsj_10);
//		dynamics.add(R.drawable.tsj_11);
//		dynamics.add(R.drawable.tsj_12);
//		dynamics.add(R.drawable.tsj_13);
//		dynamics.add(R.drawable.tsj_14);
//		dynamics.add(R.drawable.tsj_15);
//		dynamics.add(R.drawable.tsj_16);
//		dynamics.add(R.drawable.tsj_17);
//		dynamics.add(R.drawable.tsj_18);
//		dynamics.add(R.drawable.tsj_19);
//
//
//		resStrMap.put(R.drawable.tsj_1, f_dynamic_001);
//		resStrMap.put(R.drawable.tsj_2, f_dynamic_002);
//		resStrMap.put(R.drawable.tsj_3, f_dynamic_003);
//		resStrMap.put(R.drawable.tsj_4, f_dynamic_004);
//		resStrMap.put(R.drawable.tsj_5, f_dynamic_005);
//		resStrMap.put(R.drawable.tsj_6, f_dynamic_006);
//		resStrMap.put(R.drawable.tsj_7, f_dynamic_007);
//		resStrMap.put(R.drawable.tsj_8, f_dynamic_008);
//
//		resStrMap.put(R.drawable.tsj_9, f_dynamic_009);
//		resStrMap.put(R.drawable.tsj_10, f_dynamic_010);
//		resStrMap.put(R.drawable.tsj_11, f_dynamic_011);
//		resStrMap.put(R.drawable.tsj_12, f_dynamic_012);
//		resStrMap.put(R.drawable.tsj_13, f_dynamic_013);
//		resStrMap.put(R.drawable.tsj_14, f_dynamic_014);
//		resStrMap.put(R.drawable.tsj_15, f_dynamic_015);
//		resStrMap.put(R.drawable.tsj_16, f_dynamic_016);
//		resStrMap.put(R.drawable.tsj_17, f_dynamic_017);
//		resStrMap.put(R.drawable.tsj_18, f_dynamic_018);
//		resStrMap.put(R.drawable.tsj_19, f_dynamic_019);
//
//
//
//		resStrMap.put(R.drawable.f_static_00, f_static_00);
//		resStrMap.put(R.drawable.f_static_01, f_static_01);
//		resStrMap.put(R.drawable.f_static_02, f_static_02);
//		resStrMap.put(R.drawable.f_static_03, f_static_03);
//		resStrMap.put(R.drawable.f_static_04, f_static_04);
//		resStrMap.put(R.drawable.f_static_05, f_static_05);
//		resStrMap.put(R.drawable.f_static_06, f_static_06);
//		resStrMap.put(R.drawable.f_static_07, f_static_07);
//		resStrMap.put(R.drawable.f_static_08, f_static_08);
//		resStrMap.put(R.drawable.f_static_09, f_static_09);
//		resStrMap.put(R.drawable.f_static_010, f_static_010);
//		resStrMap.put(R.drawable.f_static_011, f_static_011);
//		resStrMap.put(R.drawable.f_static_012, f_static_012);
//		resStrMap.put(R.drawable.f_static_013, f_static_013);
//		resStrMap.put(R.drawable.f_static_014, f_static_014);
//		resStrMap.put(R.drawable.f_static_015, f_static_015);
//		resStrMap.put(R.drawable.f_static_016, f_static_016);
//		resStrMap.put(R.drawable.f_static_017, f_static_017);
//		resStrMap.put(R.drawable.f_static_018, f_static_018);
//		resStrMap.put(R.drawable.f_static_019, f_static_019);
//		resStrMap.put(R.drawable.f_static_020, f_static_020);
//		resStrMap.put(R.drawable.f_static_021, f_static_021);
//		resStrMap.put(R.drawable.f_static_022, f_static_022);
//		resStrMap.put(R.drawable.f_static_023, f_static_023);
//		resStrMap.put(R.drawable.f_static_024, f_static_024);
//		resStrMap.put(R.drawable.f_static_025, f_static_025);
//		resStrMap.put(R.drawable.f_static_026, f_static_026);
//		resStrMap.put(R.drawable.f_static_027, f_static_027);
//		resStrMap.put(R.drawable.f_static_028, f_static_028);
//		resStrMap.put(R.drawable.f_static_029, f_static_029);
//		resStrMap.put(R.drawable.f_static_030, f_static_030);
//		resStrMap.put(R.drawable.f_static_031, f_static_031);
//		resStrMap.put(R.drawable.f_static_032, f_static_032);
//		resStrMap.put(R.drawable.f_static_033, f_static_033);
//		resStrMap.put(R.drawable.f_static_034, f_static_034);
//		resStrMap.put(R.drawable.f_static_035, f_static_035);
//		resStrMap.put(R.drawable.f_static_036, f_static_036);
//		resStrMap.put(R.drawable.f_static_037, f_static_037);
//		resStrMap.put(R.drawable.f_static_038, f_static_038);
//		resStrMap.put(R.drawable.f_static_039, f_static_039);
//		resStrMap.put(R.drawable.f_static_040, f_static_040);
//		resStrMap.put(R.drawable.f_static_041, f_static_041);
//		resStrMap.put(R.drawable.f_static_042, f_static_042);
//		resStrMap.put(R.drawable.f_static_043, f_static_043);
//		resStrMap.put(R.drawable.f_static_044, f_static_044);
//		resStrMap.put(R.drawable.f_static_045, f_static_045);
//		resStrMap.put(R.drawable.f_static_046, f_static_046);
//		resStrMap.put(R.drawable.f_static_047, f_static_047);
//		resStrMap.put(R.drawable.f_static_048, f_static_048);
//		resStrMap.put(R.drawable.f_static_049, f_static_049);
//		resStrMap.put(R.drawable.f_static_050, f_static_050);
//		resStrMap.put(R.drawable.f_static_051, f_static_051);
//		resStrMap.put(R.drawable.f_static_052, f_static_052);
//		resStrMap.put(R.drawable.f_static_053, f_static_053);
//		resStrMap.put(R.drawable.f_static_054, f_static_054);
//		resStrMap.put(R.drawable.f_static_055, f_static_055);
//		resStrMap.put(R.drawable.f_static_056, f_static_056);
//		resStrMap.put(R.drawable.f_static_057, f_static_057);
//		resStrMap.put(R.drawable.f_static_058, f_static_058);
//		resStrMap.put(R.drawable.f_static_059, f_static_059);
//		resStrMap.put(R.drawable.f_static_060, f_static_060);
//		resStrMap.put(R.drawable.f_static_061, f_static_060);
//		resStrMap.put(R.drawable.f_static_062, f_static_062);
//
//		addPattern(emoticons, f_static_00, R.drawable.f_static_00);
//		addPattern(emoticons, f_static_01, R.drawable.f_static_01);
//		addPattern(emoticons, f_static_02, R.drawable.f_static_02);
//		addPattern(emoticons, f_static_03, R.drawable.f_static_03);
//		addPattern(emoticons, f_static_04, R.drawable.f_static_04);
//		addPattern(emoticons, f_static_05, R.drawable.f_static_05);
//		addPattern(emoticons, f_static_06, R.drawable.f_static_06);
//		addPattern(emoticons, f_static_07, R.drawable.f_static_07);
//		addPattern(emoticons, f_static_08, R.drawable.f_static_08);
//		addPattern(emoticons, f_static_09, R.drawable.f_static_09);
//		addPattern(emoticons, f_static_010, R.drawable.f_static_010);
//		addPattern(emoticons, f_static_011, R.drawable.f_static_011);
//		addPattern(emoticons, f_static_012, R.drawable.f_static_012);
//		addPattern(emoticons, f_static_013, R.drawable.f_static_013);
//		addPattern(emoticons, f_static_014, R.drawable.f_static_014);
//		addPattern(emoticons, f_static_015, R.drawable.f_static_015);
//		addPattern(emoticons, f_static_016, R.drawable.f_static_016);
//		addPattern(emoticons, f_static_017, R.drawable.f_static_017);
//		addPattern(emoticons, f_static_018, R.drawable.f_static_018);
//		addPattern(emoticons, f_static_019, R.drawable.f_static_019);
//		addPattern(emoticons, f_static_020, R.drawable.f_static_020);
//		addPattern(emoticons, f_static_021, R.drawable.f_static_021);
//		addPattern(emoticons, f_static_022, R.drawable.f_static_022);
//		addPattern(emoticons, f_static_023, R.drawable.f_static_023);
//		addPattern(emoticons, f_static_024, R.drawable.f_static_024);
//		addPattern(emoticons, f_static_025, R.drawable.f_static_025);
//		addPattern(emoticons, f_static_026, R.drawable.f_static_026);
//		addPattern(emoticons, f_static_027, R.drawable.f_static_027);
//		addPattern(emoticons, f_static_028, R.drawable.f_static_028);
//		addPattern(emoticons, f_static_029, R.drawable.f_static_029);
//		addPattern(emoticons, f_static_030, R.drawable.f_static_030);
//		addPattern(emoticons, f_static_031, R.drawable.f_static_031);
//		addPattern(emoticons, f_static_032, R.drawable.f_static_032);
//		addPattern(emoticons, f_static_033, R.drawable.f_static_033);
//		addPattern(emoticons, f_static_034, R.drawable.f_static_034);
//		addPattern(emoticons, f_static_035, R.drawable.f_static_035);
//
//		addPattern(emoticons, f_static_036, R.drawable.f_static_036);
//		addPattern(emoticons, f_static_037, R.drawable.f_static_037);
//		addPattern(emoticons, f_static_038, R.drawable.f_static_038);
//		addPattern(emoticons, f_static_039, R.drawable.f_static_039);
//		addPattern(emoticons, f_static_040, R.drawable.f_static_040);
//		addPattern(emoticons, f_static_041, R.drawable.f_static_041);
//		addPattern(emoticons, f_static_042, R.drawable.f_static_042);
//		addPattern(emoticons, f_static_043, R.drawable.f_static_043);
//		addPattern(emoticons, f_static_044, R.drawable.f_static_044);
//		addPattern(emoticons, f_static_045, R.drawable.f_static_045);
//		addPattern(emoticons, f_static_046, R.drawable.f_static_046);
//		addPattern(emoticons, f_static_047, R.drawable.f_static_047);
//		addPattern(emoticons, f_static_048, R.drawable.f_static_048);
//		addPattern(emoticons, f_static_049, R.drawable.f_static_049);
//		addPattern(emoticons, f_static_050, R.drawable.f_static_050);
//		addPattern(emoticons, f_static_051, R.drawable.f_static_051);
//		addPattern(emoticons, f_static_052, R.drawable.f_static_052);
//		addPattern(emoticons, f_static_053, R.drawable.f_static_053);
//		addPattern(emoticons, f_static_054, R.drawable.f_static_054);
//		addPattern(emoticons, f_static_055, R.drawable.f_static_055);
//		addPattern(emoticons, f_static_056, R.drawable.f_static_056);
//
//		addPattern(emoticons, f_static_057, R.drawable.f_static_057);
//		addPattern(emoticons, f_static_058, R.drawable.f_static_058);
//		addPattern(emoticons, f_static_059, R.drawable.f_static_059);
//		addPattern(emoticons, f_static_060, R.drawable.f_static_060);
//		addPattern(emoticons, f_static_061, R.drawable.f_static_060);
//		addPattern(emoticons, f_static_062, R.drawable.f_static_062);
//	}

	private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
		map.put(Pattern.compile(Pattern.quote(smile)), resource);
	}

	/**
	 * replace existing spannable with smiles
	 * 
	 * @param context
	 * @param spannable
	 * @return
	 */
	public static boolean addSmiles(Context context, Spannable spannable) {
		boolean hasChanges = false;
		for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
			Matcher matcher = entry.getKey().matcher(spannable);
			while (matcher.find()) {
				boolean set = true;
				for (ImageSpan span : spannable.getSpans(matcher.start(),
						matcher.end(), ImageSpan.class))
					if (spannable.getSpanStart(span) >= matcher.start()
							&& spannable.getSpanEnd(span) <= matcher.end())
						spannable.removeSpan(span);
					else {
						set = false;
						break;
					}
				if (set) {
					hasChanges = true;
					
					Drawable drawable = context.getResources().getDrawable(
							entry.getValue());
					drawable.setBounds(0, 0, 50, 50);// 这里设置图片的大小
					ImageSpan imageSpan = new ImageSpan(drawable,
							ImageSpan.ALIGN_BOTTOM);

					spannable.setSpan(imageSpan, matcher.start(),
							matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
		return hasChanges;
	}

	public static Spannable getSmiledText(Context context, CharSequence text) {
		Spannable spannable = spannableFactory.newSpannable(text);
		addSmiles(context, spannable);
		return spannable;
	}

	public static boolean containsKey(String key) {
		boolean b = false;
		for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
			Matcher matcher = entry.getKey().matcher(key);
			if (matcher.find()) {
				b = true;
				break;
			}
		}

		return b;
	}

	public static CharSequence getStringByResId(int resId) {
		return resStrMap.get(resId);
	}

	public static int getResIDByString(String content) {
		for (Entry<Integer, String> entry : resStrMap.entrySet()) {
			if (entry.getValue().equals(content.trim())) {
				return entry.getKey();
			}
		}
		return -1;
	}

}
