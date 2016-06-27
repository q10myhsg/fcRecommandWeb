package test.clearn;

import java.util.ArrayList;

/**
 * 输入数据的index静态类
 * 对应到文件中的 列值
 * @author Administrator
 *
 */
public class DataIndexStatic {

	public static final int prevWeeklyHits=3;
	public static final int weeklyHits=4;
	public static final int monthlyHits=5;
	public static final int hits=6;
	public static final int avgPrice=7;
	public static final int shopPower=8;
	public static final int popularity=9;
	public static final int power=10;
	public static final int score=12;
	/**
	 * 全部可用的字段
	 */
	public static ArrayList<Integer> list=new ArrayList<Integer>();
	static{
		list.add(prevWeeklyHits);
		list.add(weeklyHits);
		list.add(monthlyHits);
		list.add(hits);
		list.add(avgPrice);
		list.add(shopPower);
		list.add(popularity);
		list.add(power);
		list.add(score);
	}
}
