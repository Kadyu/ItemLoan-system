public class Day implements Cloneable{
	
	private int year;
	private int month;
	private int day;
	
	private int nextMonth;
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    public void set(String sDay) { //Set year,month,day based on a string like 01-Jan-2021
	String[] sDayParts =  sDay.split("-");
	this.year = Integer.parseInt(sDayParts[2]);  //Apply Integer.parseInt for sDayParts[2]; 
	this.day = Integer.parseInt(sDayParts[0]); 
	this.month = MonthNames.indexOf(sDayParts[1])/3+1;

	if (sDayParts[1].equals("Dec")){
		this.nextMonth = 1;
	}else{
		this.nextMonth = MonthNames.indexOf(sDayParts[1])/3+2;
	}
	
}

    public Day(String sDay) {
        set(sDay);
    } //Constructor, simply call set(sDay)

    @Override
        public String toString() {		
        return day+"-"+ MonthNames.substring((month-1)*3,(month)*3) + "-"+ year; // (month-1)*3,(month)*3
    }

    @Override
        public Day clone() {
        Day copy=null;
        try {
            copy = (Day)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }


	//my code
	

	public String getNextDay(){

		String next_month = MonthNames.substring((nextMonth-1)*3,nextMonth*3);

		if ((this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10
			|| this.month == 12) && this.day >= 29 ){

			if (month == 12 && day == 31){
				return 3+"-"+"Jan"+"-"+(year+1);
			}
			else if (month == 12 && day != 31){
				return ((28-day)*(-1)) +"-"+next_month+"-"+(year+1);
			}
			else if (month != 12 && day == 31){
				return 3+"-"+next_month+"-"+year;
			}
			else{
				return ((28-day)*(-1)) +"-"+next_month+"-"+year;
			}
		}
		else if ((this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11 ) && this.day >= 28){

			if (day == 31){
				return 3+"-"+next_month+"-"+year;
			}
			else{
				return ((27-day)*(-1))+"-"+next_month+"-"+year;
			}
		}
		else if (this.month == 2 ){

			if ( (isLeapYear(this.year) && this.day == 29) || !isLeapYear(this.year) && this.day == 28 ){
				return 3+"-"+next_month+"-"+year;
			}
			else{
				if (isLeapYear(year) && day >= 27){
					return ((26-day)*(-1)) +"-"+next_month+"-"+year;
				}
				else if (!isLeapYear(year) && day >= 26){
					return ((25-day)*(-1)) +"-"+next_month+"-"+year;
				}
				else{
					return this.day+3+"-"+MonthNames.substring((month-1)*3,(month)*3)+"-"+year;
				}
			}
		}
		else {
			return this.day+3+"-"+MonthNames.substring((month-1)*3,(month)*3)+"-"+year;
		}
	}

	
	
	public static boolean isExpiredDate (String currentDay, String dueDate)
	{
		String todayParts[] = currentDay.split("-");
		String dueDateParts[] = dueDate.split("-");

		int todayYear = Integer.parseInt(todayParts[2]);
		int todayMonth = MonthNames.indexOf(todayParts[1])/3+1;
		int todayDay = Integer.parseInt(todayParts[0]);

		int dueYear = Integer.parseInt(dueDateParts[2]);
		int dueMonth = MonthNames.indexOf(dueDateParts[1])/3+1;
		int dueDay = Integer.parseInt(dueDateParts[0]);	


		if (todayYear < dueYear){
			return false;
		}
		else if (todayYear == dueYear){

			if (todayMonth < dueMonth){
				return false;
			}
			else if (todayMonth == dueMonth){

				if (todayDay < dueDay){
					return false;
				}
				else if (todayDay == dueDay){
					return false;
				}
				else{
					return true;
				}
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	}



}