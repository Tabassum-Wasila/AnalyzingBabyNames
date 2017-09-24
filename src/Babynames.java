import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

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
	private CSVParser parserFactory(int year)
	{
		final String path = "C://Users/Tabassum Wasila Sus/Downloads/testing/";
		String fileName = path + "yob" + year + ".csv";
		FileResource fr = new FileResource(fileName);
		return fr.getCSVParser();
	}
	public int getRank(CSVParser parser, String name, String gender)
	{
		int rank = 0;
		for(CSVRecord rec : parser)
		{
			if(!rec.get(1).equals(gender))
				continue;
			rank++;
			if(rec.get(0).equals(name))
				return rank;
		}
		return -1;
	}
}
