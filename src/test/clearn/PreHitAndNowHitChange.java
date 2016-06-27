package test.clearn;

import java.util.ArrayList;

public class PreHitAndNowHitChange extends ChangeInter{

	@Override
	public float[] change(float[] fval,float[] fint) {
		// TODO Auto-generated method stub
		float[] fnew=new float[fval.length+1];
		for(int i=0;i<fint.length;i++)
		{
			fnew[i]=fint[i];
		}
		float f=(fval[1]-fval[0])/Math.max(fval[1],fval[0]);
		if(f>0.1)
		{
			fnew[fval.length]=3;
		}else if(f<-0.1){
			fnew[fval.length]=1;
		}else{
			fnew[fval.length]=2;
		}
		return fnew;
	}

}
