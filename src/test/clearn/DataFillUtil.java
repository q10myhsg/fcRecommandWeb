package test.clearn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.util.FileUtil2;

/**
 * 缺失值填补方法
 * @author Administrator
 *
 */
public class DataFillUtil {
	
	/**
	 * 品牌中如果包含数据则剔除
	 */
	public String[] brandSkip = {"市场","奥特莱斯","电子城","早市","商行","尾货","商店","商城","庙会","广场","购物中心","百货","幼儿园","小学","建材","布艺城","市场","广场","工程","五金","工具","展厅","在线","直销处","俱乐部","汗蒸","桑拿",
    		"中学","小学","房产","中介","教室","加油站","停车","公交车","出租","学校","航空公司","研究所","代售点","墓地","停车场","驾校","加油站","景区","网吧","公园","篮球场","马术","足球","农家院","山庄"};
	/**
	 * 如果存在则剔除
	 */
	public HashSet<String> brandSkipSet=null;
	/**
	 * 过滤的文件
	 */
	public String skipFile=System.getProperty("user.dir")+"/data/shopClearnName.txt";
	/**
	 * 被过滤的id
	 */
	public HashSet<Long> skipBrandId=new HashSet<Long>();
	/**
	 * 默认聚类数量
	 */
	public int clusterCount = 3;
	/**
	 * 判断对应的DataIndexStatic.list的位置中对应的 数据列名的int值，value为过滤后的新的index值
	 */
	public HashMap<Integer, Integer> inputIndexMapUseIndex = new HashMap<Integer, Integer>();
	/**
	 * 从文件的index到数组中德index的对应
	 */
	public HashMap<Integer, Integer> inputIndexMapUseIndexRel = new HashMap<Integer, Integer>();
		/**
		 * 对应的映射列
		 */
		public  HashMap<Long, long[]> relMap=new HashMap<Long,long[]>();
		/**
		 * 需要操作的id
		 */
		public  LinkedList<Long> notFile=new LinkedList<Long>();
		/**
		 * content对应的值float值
		 */
		public HashMap<Long,float[]> map =new HashMap<Long,float[]>();
		
		/**
		 * 全部离散化的值
		 */
		public HashMap<Long,float[]> mapS =new HashMap<Long,float[]>();
		/**
		 * id对应mall信息
		 */
		public HashMap<Long,String> mall=new HashMap<Long,String>();
		
		/**
		 * id对应brand信息
		 */
		public HashMap<Long,String> brand=new HashMap<Long,String>();
		
		/**
		 * 业态信息
		 */
		public HashMap<Long,String> category=new HashMap<Long,String>();
		
		/**
		 * mall的统计结果
		 */
		public Statistics mallStatistic=new Statistics();
		
		/**
		 * brand的统计结果
		 */
		public Statistics brandStatistic=new Statistics();
		
		/**
		 * 用于从原始数据根据条件添加 新的feather的公共方法
		 */
		public ChangeInter change=null;
		
		public ChangeInter getChange() {
			return change;
		}

		public void setChange(ChangeInter change) {
			this.change = change;
		}

		/**
		 * 统一的执行程序
		 * 需要获取feather对应在文件种的index 从0开始
		 * @param feather key对应在content中对应的数据列的index value 为处理方法
		 * @param cluster 与feather中的key一样，value为聚类数量 其中 使用的函数为canopy
		 */
	    public void run(HashMap<Integer,Integer> feather,HashMap<Integer,Integer> cluster)
	    {
	    	
	    }
	    
	    public long[] getRel(Long l)
	    {
	    	return relMap.get(l);
	    }
	    public Long poll()
	    {
	    	return notFile.poll();
	    }
	    public void add(Long e)
	    {
	    	if(notFile.peekLast()!=e)
	    		notFile.add(e);
	    }
	    
	    //写文件
	    public void print(String filePath)
	    {
	    	FileUtil2 fileUtil=new FileUtil2(filePath,"utf-8","");
	    	LinkedList<String> write=new LinkedList<String>();
	    	for(Entry<Long,float[]> m:map.entrySet())
	    	{
	    		String str=Long.toString(m.getKey());
	    		int i=0;
	    		for(float f:m.getValue())
	    		{
	    			str+="\t"+f;
	    		}
	    		write.add(str);
	    	}
	    	fileUtil.wirte(write, write.size());
	    	System.out.println("文件写入完成");
	    }
	    /**
	     * 打印离散化的变量
	     * @param filePath
	     */
	    public void printS(String filePath)
	    {
	    	FileUtil2 fileUtil=new FileUtil2(filePath,"utf-8","");
	    	LinkedList<String> write=new LinkedList<String>();
	    	for(Entry<Long,float[]> m:mapS.entrySet())
	    	{
	    		String str=Long.toString(m.getKey());
	    	//	float[] val=map.get(m.getKey());
	    		for(float f:m.getValue())
	    		{
	    			str+="\t"+f;
	    		}
//	    		for(float f:val)
//	    		{
//	    			str+="\t"+f;
//	    		}
	    		write.add(str);
	    	}
	    	fileUtil.wirte(write, write.size());
	    	System.out.println("文件写入完成");
	    }
	    
	    
	    /**
		 * 获取交叉方法
		 * 
		 * @param index
		 *            使用的index1
		 * @param index2
		 *            使用的index2
		 * @param mapIndex
		 *            使用的分类
		 * @param rel
		 *            最终生成的分类号
		 * 
		 */
		public void runCoss(int index, int index2, ArrayList<int[]> mapIndex,
				ArrayList<int[]> mapIndex2, ArrayList<Integer> rel, Integer otherRel) {
			for (Entry<Long, float[]> m : mapS.entrySet()) {
				float[] val = m.getValue();
				float[] valEnd = new float[val.length + 1];
				for(int i=0;i<val.length;i++)
				{
					valEnd[i]=val[i];
				}
				boolean flagN = false;
				for (int i = 0; i < mapIndex.size(); i++) {
					boolean flag = false;
					for (int iL1 : mapIndex.get(i)) {
						// 如果满足要求
						if (val[index] == iL1) {
							flag = true;
							break;
						}
					}
					if (flag) {
						flag = false;
						for (int iL1 : mapIndex2.get(i)) {
							// 如果满足要求
							if (val[index2] == iL1) {
								flag = true;
								break;
							}
						}
					} else {
						continue;
					}
					if (flag) {
						valEnd[val.length] = rel.get(i);
						flagN = true;
						break;
					}
				}
				if (!flagN) {
					valEnd[val.length] = otherRel;
				}
				m.setValue(valEnd);
			}
		}
		/**
		 * 获取原始数据非离散数据
		 * 新添加字段
		 * @param index 需要获取的字段
		 * @param 
		 */
		public void addOne()
		{
			for(Entry<Long,float[]> val:map.entrySet())
			{
				float[] fval=val.getValue();
				float[] fint=mapS.get(val.getKey());
//				int[] fNew=new int[fint.length+1];
//				for(int i=0;i<fint.length;i++)
//				{
//					fNew[i]=fint[i];
//				}
//				int change2=this.change.change(fval);
//				fNew[fint.length]=change2;
				float[] fNew=this.change.change(fval,fint);
				mapS.put(val.getKey(), fNew);
			}
		}
		
		public void updateOne()
		{
			for(Entry<Long,float[]> val:map.entrySet())
			{
				float[] fval=val.getValue();
				float[] fint=mapS.get(val.getKey());
				//int[] fNew=new int[fint.length+1];
//				for(int i=0;i<fint.length;i++)
//				{
//					fNew[i]=fint[i];
//				}
				float[] fNew=this.change.change(fval,fint);
				mapS.put(val.getKey(), fNew);
			}
		}

		

		public Long parseLong(String str) {
			return Long.parseLong(str.equals("") ? "0" : str);
		}
		
		/**
		 * 
		 * @param shopName
		 * @return 是否为过滤 true 是 false 否
		 */
		public boolean clearnBrandName(String shopName,Long shopId)
		{
			if(brandSkipSet==null)
			{
				//则新增
				FileUtil2 fileUtilSkip=new FileUtil2(skipFile,"utf8",false);
				brandSkipSet=new HashSet<String>();
				LinkedList<String> skipList=fileUtilSkip.readAndClose();
				while(skipList.size()>0)
				{
					String skip=skipList.pollFirst().trim();
					if(skip.equals(""))
					{
						continue;
					}
					brandSkipSet.add(skip);
				}
			}
			if(brandSkipSet.add(shopName))
			{
				skipBrandId.add(shopId);
				return true;
			}
			for(String contans:brandSkip)
			{
				if(shopName.contains(contans))
				{
					skipBrandId.add(shopId);
					return true;
				}
			}
			return false;
		}
	
		/**
		 * 从品牌中获取对应关系
		 * @return
		 */
		public HashMap<String,float[]> getBrandMapping()
		{
			HashMap<String,float[]> brandMapping=new HashMap<String,float[]>();
			for(Entry<Long,String> b:brand.entrySet())
			{
				float[] fval=map.get(b.getKey());
				if(fval==null)
				{
					continue;
				}
				brandMapping.put(b.getValue(),fval);
			}
			return brandMapping;
		}
}
