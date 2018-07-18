package cn.bobo.hive.annology.distribute;

public class AppTask {
	public static void main(String[] args) {
		
		while(true){
			
			System.out.println("i am working.......");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
