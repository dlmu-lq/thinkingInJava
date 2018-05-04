class Test7{
	public void f(){
		System.out.println("Test7 f(){}");
	}
	static class Test7Test{
		public static void main(String...args){
			Test7 test7 =new Test7();
			test7.f();
		}
	}
}