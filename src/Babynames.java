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
		String fileName = path + "yob" + year + "short.csv";
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
	public int getRank(int year, String name, String gender)
	{
		CSVParser parser = parserFactory(year);
		return getRank(parser, name, gender);
	}
	public String getName(int year, int rank, String gender)
	{
		int rnk = 0;
		CSVParser parser = parserFactory(year);
		for(CSVRecord rec : parser)
		{
			if(!rec.get(1).equals(gender))
				continue;
			rnk++;
			if(rnk == rank)
				return rec.get(0);
		}
		return "NO NAME";
	}
	public String whatIsNameInYear(String name, int year, int newYear, String gender)
	{
		CSVParser parser = parserFactory(year);
		int rank = getRank(parser, name, gender);
		return getName(newYear, rank, gender);
	}
	private int findYear(String fName)
	{
		return Integer.parseInt(fName.substring(3, 7));
	}
	public int yearOfHighestRank(String name, String gender)
	{
		DirectoryResource dr = new DirectoryResource();
		int highestSoFar = -1;
		int yearOfRank = -1;
		
		for(File file : dr.selectedFiles())
		{
			String fName = file.getName();
			int year = findYear(fName);
			CSVParser parser = parserFactory(year);
			int rank = getRank(parser, name, gender);
			
			if(rank == -1)
				continue;
			if(highestSoFar == -1 || highestSoFar > rank)
			{	
				highestSoFar = rank;
				yearOfRank = year;
			}
			
		}
		return yearOfRank;
	}
	public double getAverageRank(String name, String gender)
	{
		DirectoryResource dr = new DirectoryResource();
		double average = 0.0;
		int rankCount = 0;
		
		for(File file : dr.selectedFiles())
		{
			String fName = file.getName();
			int year = findYear(fName);
			CSVParser parser = parserFactory(year);
			int rank = getRank(parser, name, gender);
			if(rank != -1)
			{	
				average += rank;
				rankCount++;
			}
		}
		if(average != 0.0)
			return average/(double)rankCount;
		return -1.0d;
	}
	public int getTotalBirthsRankedHigher(int year, String name, String gender)
	{
		int rank = getRank(year, name, gender);
		CSVParser parser = parserFactory(year);
		int totalBirthsHigherRanked = 0;
		for(CSVRecord rec : parser)
		{
			if(!rec.get(1).equals(gender))
				continue;
			String recName = rec.get(0);
			int recRank = getRank(year, recName, gender);
			if(recRank < rank)
			{
				totalBirthsHigherRanked += Integer.parseInt(rec.get(2));
			}
		}
		return totalBirthsHigherRanked;
	}
	public void tester()
	{
		FileResource fr = new FileResource();
		totalBirths(fr.getCSVParser());
		
		System.out.println("Mason name rank in 2012: " + getRank(2012, "Mason", "M"));
		
		System.out.println("Male name ranked 2 in 2012: " + getName(2012, 2, "M"));
		
		System.out.println("Isabella born in 2012 would be " + whatIsNameInYear("Isabella", 2012, 2014, "F") + " if she was born in 2014.");
		
		System.out.println("Year of highest Rank for Mason: " + yearOfHighestRank("Mason", "M"));
		
		System.out.println("Average Rank for Mason: " + getAverageRank("Mason", "M"));
		
		System.out.println("Total number of births in 2012 ranked higher than Ethan is : " + getTotalBirthsRankedHigher(2012, "Ethan", "M"));
	}
	public static void main(String[] args)
	{
		Babynames b = new Babynames();
		b.tester();
	}
}