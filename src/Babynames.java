import edu.duke.*;
import org.apache.commons.csv.*;

public class Babynames {
	public void totalBirths(CSVParser parser)
	{
		int totalBirths = 0, maleNames = 0, femaleNames = 0;
		for(CSVRecord rec : parser)
		{
			int birth = Integer.parseInt(rec.get(2));
			totalBirths += birth; 
			if(rec.get(1).equals("M"))
			{
				maleNames++;
			}
			else
			{
				femaleNames++;
			}
		}
		System.out.println("total births = " + totalBirths + "\nNumber of male names : " + maleNames + "\nNumber of female names : " + femaleNames + "\nTotal number of baby names : " + (femaleNames + maleNames));
	}			
}
