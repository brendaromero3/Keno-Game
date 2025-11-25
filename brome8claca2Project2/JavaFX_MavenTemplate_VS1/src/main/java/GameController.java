import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameController {
	private int pickedSpots; //number of spots chosen (1,4,8,10)
	private int drawingsPicked; //number of drawings(1-4)
	private Set<Integer> selectedNums; //Player's chosen numbers
	// added stuff
	private Set<Integer> matchedNums; //numbers matched in current drawing
	private int currentDrawing; //current drawing number
	private double totalWinnings; // total accumulated winnings
	private double amountWonThisDrawing; //winnings from this current drawing
	private final int MAXNUM = 80; //keno nums 1-80
	private Random rand;
	
	//added players
	private KenoMechanics mechanics;
	public DrawingRes res;
	
	public GameController(){
		pickedSpots = 0;	// Setting the amount of spots to zero at the start
		selectedNums = new HashSet<Integer>();
		drawingsPicked = 0;
		matchedNums = new HashSet<>();
		currentDrawing = 0;
		totalWinnings = 0;
		amountWonThisDrawing = 0;
		rand = new Random();
		//added stuff
		mechanics = new KenoMechanics();
		res = new DrawingRes();
	}
	
	public void chooseSpots(int spots) {
		// Setting the amount of spots
		this.pickedSpots = spots;
		// Resetting chosen numbers for the new card
		selectedNums.clear();
	}
	
	public int getNumSpots() {
		// Returning the number of spots chosen (1, 4, 8, 10)
		return pickedSpots;
	}
	
	public void chooseDrawings(int numDrawings) {
		// Setting the amount of drawings
		this.drawingsPicked = numDrawings;
		currentDrawing = 0;
	}
	
	public int getNumDrawings() {
		// Return the number of drawings chosen (1, 2, 3, 4)
		return drawingsPicked;
	}
	
	public void addSpot(int spot) {
		// Adding the spot to the set; preventing invalid duplicates
		if (selectedNums.size() < pickedSpots && spot >= 1 && spot <= 80) {
			selectedNums.add(spot);
		}
	}
	
	public void removeSpot(int spot) {
		// Removing the spot from the set
		selectedNums.remove(spot);
	}
	
	public int numSpotsChosen() {
		// Returning the size of the set
		return selectedNums.size();
	}
	
	public boolean hasNextDrawing() {
		return currentDrawing < drawingsPicked;
	}
	
	public boolean isLastDrawing() {
		return currentDrawing == drawingsPicked;
	}
	
	// simulating the next drawing
	public Set<Integer> playNextDrawing() {
		if (!hasNextDrawing()) {
			return null;
		}
		currentDrawing++;
		matchedNums.clear();
		amountWonThisDrawing = 0;
		
		//randomly drawing 20 numbers
		Set<Integer> draw = new HashSet<>();
		while (draw.size() < 20) {
			draw.add(rand.nextInt(MAXNUM) + 1);
		}
		
		//finding matches
		for (int n : selectedNums) {
			if (draw.contains(n)) {
				matchedNums.add(n);
				res.addMatchedNums(n);	// Adding the matched number to the overall set of numbers matched
			}
		}
		
		//computing winnings/payout logic here
		int matches = matchedNums.size();
		amountWonThisDrawing = calculateWinnings(matches);
		totalWinnings += amountWonThisDrawing;
		res.addDrawing();
		
		return draw;	// Returning the numbers that were drawn
	}
	
	private double calculateWinnings(int matches) {
		return mechanics.getPayout(pickedSpots, matches);
	}
	
	
	public boolean containsSpot(int spot) {
		// Returning if spot is in the set
		return selectedNums.contains(spot);
	}
	
	public Set<Integer> autoQuickPick() {
		Random ran = new Random();
		selectedNums.clear(); 	// Removing everything from selectedNums
		while(numSpotsChosen() < pickedSpots) {
			// Getting random numbers for the selected amount of spots, while loop accounts for duplicates
			selectedNums.add(ran.nextInt(80) + 1);	// Adding 1 because it will return a range between 0-79
		}
		
		return selectedNums;	
	}
	
	public int getCurrentDrawingNumber() {
		return currentDrawing;
	}
	
	public int getMatchedCount() {
		return matchedNums.size();
	}
	
	public Set<Integer> getMatchedNumbers() {
		return new HashSet<>(matchedNums);
	}
	
	public double getAmountWonThisDrawing() {
		return amountWonThisDrawing;
	}
	
	public double getTotalWinnings() {
		return totalWinnings;
	}
	
	public void clearSelectedSpots() {
		// Resetting chosen numbers for the new card
		selectedNums.clear();
	}
	
	// Resetting the whole player state and chosen card
	public void resetAll() {
		pickedSpots = 0;
		drawingsPicked = 0;
		selectedNums.clear();
		matchedNums.clear();
		currentDrawing = 0;
		amountWonThisDrawing = 0;
	}
	
	public Set<Integer> getSelectedNums() {
		return new HashSet<>(selectedNums);
	}
	
	public class KenoMechanics{
		private Random range;
		private Payouts payouts;
		
		KenoMechanics(){
			range = new Random();
			payouts = new Payouts();
		}
		
		
		public Set<Integer> generateDrawing(){
			Set<Integer> drawings = new HashSet<>();
			
			while(drawings.size() < 20) {
				// Generating 20 random numbers and adding it to a list
				drawings.add(range.nextInt(80) + 1);	// Adding 1 because it will return a range between 0-79
			}
			
			return drawings;
		}
		
		public double getPayout(int spots, int matches) {
			return payouts.getPayout(spots, matches);
		}
	}
	
	public String payoutAsString() {
		return mechanics.payouts.getPayoutAsString(pickedSpots);
	}
	
	private class Payouts{
		private Map<Integer, Map<Integer, Double>> payoutTable;
		
		Payouts(){
			payoutTable = new HashMap<Integer, Map<Integer, Double>>();
			popPayouts();
		}
		
		// Populating payouts
		private void popPayouts() {
			// example: $1 per bet
			// for 1 spot, only 1 match pays something
			Map<Integer, Double> oneSpot = new HashMap<>();
			oneSpot.put(0, 0.0);
			oneSpot.put(1, 3.0); //ex: match 1 pays $3 on $1
			payoutTable.put(1, oneSpot);
			
			// 4-spot example of matches->payout
			Map<Integer, Double> fourSpot = new HashMap<>();
			fourSpot.put(0, 0.0);
			fourSpot.put(1, 0.0);
			fourSpot.put(2, 1.0);
			fourSpot.put(3, 5.0);
			fourSpot.put(4, 75.0);
			payoutTable.put(4, fourSpot);
			
			// 8-spot example
			Map<Integer, Double> eightSpot = new HashMap<>();
			eightSpot.put(0, 0.0);
			eightSpot.put(1, 0.0);
			eightSpot.put(2, 0.0);
			eightSpot.put(3, 0.0);
			eightSpot.put(4, 2.0);
			eightSpot.put(5, 12.0);
			eightSpot.put(6, 50.0);
			eightSpot.put(7, 750.0);
			eightSpot.put(8, 10000.0);
			payoutTable.put(8, eightSpot);
			
			// 10-spot example
			Map<Integer, Double> tenSpot = new HashMap<>();
			tenSpot.put(0, 5.0);
			tenSpot.put(1, 0.0);
			tenSpot.put(2, 0.0);
			tenSpot.put(3, 0.0);
			tenSpot.put(4, 0.0);
			tenSpot.put(5, 2.0);
			tenSpot.put(6, 15.0);
			tenSpot.put(7, 40.0);
			tenSpot.put(8, 450.0);
			tenSpot.put(9, 4250.0);
			tenSpot.put(10, 100000.0);
			payoutTable.put(10, tenSpot);
		}
		
		public double getPayout(int spots, int matches) {
			if (!payoutTable.containsKey(spots)) {
				return 0.0;
			}
			Map<Integer, Double> map = payoutTable.get(spots);
			return map.getOrDefault(matches, 0.0);
		}
		
		public String getPayoutAsString(int spots) {
			if (!payoutTable.containsKey(spots)) {
				return "No payout table available.";
			}
			StringBuilder sb = new StringBuilder();
			Map<Integer, Double> map = payoutTable.get(spots);
			List<Integer> keys = new ArrayList<>(map.keySet());
			Collections.sort(keys);
			for (Integer k : keys) {
				sb.append("\t").append(k).append(" matches -> $").append(map.get(k)).append("0\n");
			}
			return sb.toString();
		}
	}
	
	public class DrawingRes{
		private List<Integer> matchedNums;
		private int drawingCount;
		
		DrawingRes() {
			matchedNums = new ArrayList<>();
		}
		
		private void addMatchedNums(int spot) {
			matchedNums.add(spot);
		}
		
		public List<Integer> matchedNumsSinceStart(){
			return matchedNums;	// Returning a set of all the matches made since the start of the program
		}
		
		public int matchesSinceStart() {
			return matchedNums.size();	// Returning the amount of matches since the start of the program
		}
		
		private void addDrawing() {
			drawingCount++;
		}
		
		public int getDrawingsSinceStart() {
			return drawingCount;	// Returning the amount of drawings since the start of the program
		}
		
		public String matchedNumString() {
			String matchedString = "";
			for(int i = 0; i < matchedNums.size(); i++) {
				if(i == matchedNums.size() - 1) {
					matchedString += String.valueOf(matchedNums.get(i));
				}
				else {
					matchedString += String.valueOf(matchedNums.get(i)) + ", ";
				}
			}
			
			return matchedString;
		}
	}
	
}
