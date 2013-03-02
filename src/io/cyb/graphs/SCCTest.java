package io.cyb.graphs;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//TODO migrate testData to YAML
public class SCCTest {	
	
	private final boolean REVERSED = true;
	private final boolean FORWARD = false;
	
	@Test(dataProvider = "testData")
	public void testSCC(ArrayList<TIntArrayList> graph, 
			ArrayList<TIntArrayList> graphRev, int[] sccs) {
		DFS dfs = new DFS(graph, graphRev);
		dfs.dfsLoop();
//		dfs.search(1);
//		dfs.labelsToFinishTimes();
	}
	
	@Test
	public void testFinishingTimes() {}
	
	@Test
	public void testLeaders() {}
	
	@DataProvider(name = "testData", parallel=false)
	private Object[][] readTestData() throws IOException {
		File path = new File(".");
		File data = new File(path.getCanonicalPath()+"/data/sccTestData.txt");
		BufferedReader br = new BufferedReader(new FileReader(data));
		
		String line = null;
		
		StringBuffer buff = new StringBuffer();
		while(null != (line = br.readLine())) {
			buff.append(line).append("\n");
		}
		String[] chunks = buff.toString().split(";");
		
		return parseData(chunks);	
	}
	
	private Object[][] parseData(String[] chunks) {
		String data = null;
		Object[][] res = new Object[chunks.length][3];
		int[] scc = new int[5];
		String[] strScc = null;
		
		for (int i = 0; i < chunks.length-1; i+=3) {
			data = chunks[i+1];
			for (int j=0; j<5; j++) {
				strScc = chunks[i+2].trim().split(",");
				scc[j] = Integer.parseInt(strScc[j]);
			}
			
			res[i][0] = strToAdjList(data, FORWARD);
			res[i][1] = strToAdjList(data, REVERSED);
			res[i][2] = scc;
		}
		
//		return res;
//     debugging on test#1 data
		Object[][] val = new Object[1][3];
		val[0] = res[0];
		return val;
	}
	
	//TODO change reversed read to Topological Sort?
	//FIXME TIntLinkedList.indexOf is broken, use TIntArrayList  
	private ArrayList<TIntArrayList> strToAdjList(String data, boolean isReversed) {
		String[] rows = data.split("\n");
		String[] cel = new String[2];
		int tail, head = 0;
		ArrayList<TIntArrayList> list = new ArrayList<TIntArrayList>(rows.length);
		//FIXME init arrays size
		for (int i = 0; i < rows.length; i++) {
			list.add(null);
		}
		
		for (String row : rows) {
			if (row.trim().equals("")) continue;
			
			cel = row.split(" ");
			tail = isReversed ? Integer.parseInt(cel[1]) : Integer.parseInt(cel[0]);
			head = isReversed ? Integer.parseInt(cel[0]) : Integer.parseInt(cel[1]);
			
			if (null == list.get(tail)) {
				list.set(tail, new TIntArrayList());
				list.get(tail).add(tail);
			} 
			list.get(tail).add(head);
		}
		
		//FIXME init arrays size
		//remove null objects at end
		for (int i = list.size()-1; i >= 0; i--) {
			if (list.get(i) == null) {
				list.remove(i);
			} else break;
		}
		
		return list;
	}
			
}