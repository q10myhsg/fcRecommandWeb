package test.clearn;

import java.util.ArrayList;

public abstract class ChangeInter {

	/**
	 * 修改方法
	 * 需要被覆盖
	 * @param index
	 * @param fval
	 */	
	public abstract float[] change(float[] fval,float[] fint);
	
	public void print(float[] fval)
	{
		for(float f:fval)
		{
			System.out.print(f+"\t");
		}
		System.out.println();
	}
	
	public void print(int[] fint)
	{
		for(int i:fint)
		{
			System.out.print(i+"\t");
		}
		System.out.println();
	}
}
