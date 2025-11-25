import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GCTest {
	private GameController game;
	
	@BeforeEach
	void init() {
		game = new GameController();
		game.chooseDrawings(1);
		game.chooseSpots(10);
		game.addSpot(9);
		game.addSpot(80);
		game.addSpot(1);
		game.addSpot(64);
		game.addSpot(27);
		game.addSpot(3);
	}
	
	@Test
	void constructorTest() {
		GameController game1 = new GameController();
		assertEquals(0, game1.getNumSpots(), "Number of spots should be initialized to 0");
		assertEquals(0, game1.numSpotsChosen(), "Size of the set containing the player's spots should be empty");
	}
	
	@Test
	void chooseAndGetSpotsTest() {
		game.chooseSpots(10);
		assertEquals(10, game.getNumSpots(), "After calling chooseSpots(10), the number of spots should be 10");
		game.chooseSpots(8);
		assertEquals(8, game.getNumSpots(), "After calling chooseSpots(8), the number of spots should be 8");
		game.chooseSpots(4);
		assertEquals(4, game.getNumSpots(), "After calling chooseSpots(4), the number of spots should be 4");
		game.chooseSpots(1);
		assertEquals(1, game.getNumSpots(), "After calling chooseSpots(1), the number of spots should be 1");
	}
	
	@Test
	void chooseAndGetDrawingsTest() {
		assertEquals(1, game.getNumDrawings(), "After calling chooseDrawing(1), the number of drawings should be 1");
		game.chooseDrawings(2);
		assertEquals(2, game.getNumDrawings(), "After calling chooseDrawing(2), the number of drawings should be 2");
		game.chooseDrawings(3);
		assertEquals(3, game.getNumDrawings(), "After calling chooseDrawing(3), the number of drawings should be 3");
		game.chooseDrawings(4);
		assertEquals(4, game.getNumDrawings(), "After calling chooseDrawing(4), the number of drawings should be 4");
	}
	
	@Test
	void addSpotAndContainsTest() {
		GameController game1 = new GameController();
		game1.chooseSpots(10);
		game1.addSpot(10);
		game1.addSpot(1);
		game1.addSpot(3);
		game1.addSpot(80);
		game1.addSpot(55);
		game1.addSpot(33);
		
		assertEquals(true, game1.containsSpot(10), "The number 10 should be a spot in the set");
		assertEquals(true, game1.containsSpot(1), "The number 10 should be a spot in the set");
		assertEquals(false, game1.containsSpot(90), "The number 90 should not be a spot in the set");
		assertEquals(true, game1.containsSpot(3), "The number 10 should be a spot in the set");
		assertEquals(false, game1.containsSpot(47), "The number 47 should not be a spot in the set");
		assertEquals(true, game1.containsSpot(80), "The number 80 should be a spot in the set");
		assertEquals(true, game1.containsSpot(55), "The number 55 should be a spot in the set");
		assertEquals(true, game1.containsSpot(33), "The number 33 should be a spot in the set");
		assertEquals(false, game1.containsSpot(29), "The number 29 should not be a spot in the set");
	}
	
	@Test
	void removeSpotTest() {
		game.removeSpot(1);
		game.removeSpot(27);
		
		assertEquals(false, game.containsSpot(1), "After removing the number 1 from the spots, it should not be in the set");
		assertEquals(false, game.containsSpot(27), "After removing the number 27 from the spots, it should not be in the set");
		assertEquals(4, game.numSpotsChosen(), "After removing 2 spots, the number of spots should be 4");
		
		
		game.removeSpot(9);
		game.addSpot(10);
		
		assertEquals(false, game.containsSpot(9), "After removing the number 9 from the spots, it should not be in the set");
		assertEquals(4, game.numSpotsChosen(), "After removing 1 spot and adding another, the number of spots should be 4");
		
		game.removeSpot(10);
		game.removeSpot(80);
		game.removeSpot(64);
		game.removeSpot(3);
		
		assertEquals(false, game.containsSpot(10), "After removing the number 10 from the spots, it should not be in the set");
		assertEquals(false, game.containsSpot(80), "After removing the number 80 from the spots, it should not be in the set");
		assertEquals(false, game.containsSpot(64), "After removing the number 64 from the spots, it should not be in the set");
		assertEquals(false, game.containsSpot(3), "After removing the number 3 from the spots, it should not be in the set");
		assertEquals(0, game.numSpotsChosen(), "After removing 4 spots, the number of spots should be 0");
	}
	
	@Test
	void numSpotsChosenTest() {
		assertEquals(6, game.numSpotsChosen(), "The number of spots should be 6");
		
		game.addSpot(15);
		game.addSpot(15);
		assertEquals(7, game.numSpotsChosen(), "The number of spots should be 7 after adding 15 twice");
		
		game.addSpot(32);
		game.addSpot(73);
		game.addSpot(46);
		assertEquals(10, game.numSpotsChosen(), "The number of spots should be 10 after adding 3 more spots");
		
		game.removeSpot(15);
		game.removeSpot(32);
		game.removeSpot(46);
		assertEquals(7, game.numSpotsChosen(), "The number of spots should be 7 after removing 3 spots");
		
		game.removeSpot(80);
		game.removeSpot(73);
		game.removeSpot(64);
		game.removeSpot(3);
		assertEquals(3, game.numSpotsChosen(), "The number of spots should be 3 after removing 4 spots");
	}
	
	@Test
	void autoQuickPickTest() {
		game.chooseSpots(10);
		game.autoQuickPick();
		assertEquals(10, game.numSpotsChosen(), "The number of spots should be 10 after calling autoQuickPick() with number of spots = 10");
		
		game.chooseSpots(8);
		game.autoQuickPick();
		assertEquals(8, game.numSpotsChosen(), "The number of spots should be 8 after calling autoQuickPick() with number of spots = 8");
		
		game.chooseSpots(4);
		game.autoQuickPick();
		assertEquals(4, game.numSpotsChosen(), "The number of spots should be 4 after calling autoQuickPick() with number of spots = 4");
		
		game.chooseSpots(1);
		game.autoQuickPick();
		assertEquals(1, game.numSpotsChosen(), "The number of spots should be 1 after calling autoQuickPick() with number of spots = 1");
	}
	
	@Test
	void playDrawingTest() {
		// Testing playNextDrawing(), isLastDrawing(), hasNextDrawing(), and getCurrentDrawingNumber()
		assertEquals(true, game.hasNextDrawing(), "There has been no drawings yet, so hasNextDrawing() should return true");
		assertEquals(false, game.isLastDrawing(), "There has been no drawings yet, so isLastDrawing() should return false");
		assertEquals(0, game.getCurrentDrawingNumber(), "There has been no drawings, so getCurrentDrawingNumber() should return 0");
		
		Set<Integer> selected  = game.playNextDrawing();
		
		assertEquals(false, game.hasNextDrawing(), "There has been 1 drawing, so hasNextDrawing() should return false, since chosen drawings was 1");
		assertEquals(true, game.isLastDrawing(), "There has been 1 drawing, so isLastDrawing() should return true, since chosen drawings was 1");
		assertEquals(20, selected.size(), "The amount of random numbers chosen should be 20, since it is in a set, there are no duplicates");
		assertEquals(1, game.getCurrentDrawingNumber(), "There has been 1 drawing, so getCurrentDrawingNumber() should return 1");
		
		game.chooseDrawings(4);
		assertEquals(true, game.hasNextDrawing(), "After making the chosen drawings 4, there has been no drawings, so hasNextDrawing() should return false");
		assertEquals(false, game.isLastDrawing(), "There has been no drawings, so isLastDrawing() should return true, after making the chosen drawings 4");
		assertEquals(0, game.getCurrentDrawingNumber(), "There has been no drawings, so getCurrentDrawingNumber() should return 0");
		
		// Drawing 1
		Set<Integer> selected2  = game.playNextDrawing();
		assertEquals(true, game.hasNextDrawing(), "There has been 1 drawing, so hasNextDrawing() should return true, since there are still 3 left");
		assertEquals(false, game.isLastDrawing(), "There has been 1 drawing, so isLastDrawing() should return false, since there are still 3 left");
		assertEquals(20, selected2.size(), "The amount of random numbers chosen should be 20, since it is in a set, there are no duplicates");
		assertEquals(1, game.getCurrentDrawingNumber(), "There has been 1 drawing, so getCurrentDrawingNumber() should return 1");
		
		// Drawing 2
		Set<Integer> selected3  = game.playNextDrawing();
		assertEquals(true, game.hasNextDrawing(), "There has been 2 drawings, so hasNextDrawing() should return true, since there is still 2 left");
		assertEquals(false, game.isLastDrawing(), "There has been 2 drawings, so isLastDrawing() should return false, since there is still 2 left");
		assertEquals(20, selected3.size(), "The amount of random numbers chosen should be 20, since it is in a set, there are no duplicates");
		assertEquals(2, game.getCurrentDrawingNumber(), "There has been 2 drawings, so getCurrentDrawingNumber() should return 2");
		
		// Drawing 3
		Set<Integer> selected4  = game.playNextDrawing();
		assertEquals(true, game.hasNextDrawing(), "There has been 3 drawings, so hasNextDrawing() should return true, since there is still 1 left");
		assertEquals(false, game.isLastDrawing(), "There has been 3 drawings, so isLastDrawing() should return false, since there is still 1 left");
		assertEquals(20, selected4.size(), "The amount of random numbers chosen should be 20, since it is in a set, there are no duplicates");
		assertEquals(3, game.getCurrentDrawingNumber(), "There has been 3 drawings, so getCurrentDrawingNumber() should return 3");
		
		// Drawing 4
		Set<Integer> selected5  = game.playNextDrawing();
		assertEquals(false, game.hasNextDrawing(), "There has been 4 drawings, so hasNextDrawing() should return false, since there is none left");
		assertEquals(true, game.isLastDrawing(), "There has been 4 drawings, so isLastDrawing() should return true, since there is none left");
		assertEquals(20, selected5.size(), "The amount of random numbers chosen should be 20, since it is in a set, there are no duplicates");
		assertEquals(4, game.getCurrentDrawingNumber(), "There has been 4 drawings, so getCurrentDrawingNumber() should return 4");
	}
	
	@Test
	void getMatchedCountTest() {
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.chooseDrawings(4);
		
		// First drawing
		Set<Integer> selected  = game.playNextDrawing();
		int count = 0;
		
		for(int num : selected) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		// Second drawing
		Set<Integer> selected2  = game.playNextDrawing();
		count = 0;
		
		for(int num : selected2) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		// Third drawing
		Set<Integer> selected3  = game.playNextDrawing();
		count = 0;
		
		for(int num : selected3) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		// Fourth drawing
		Set<Integer> selected4  = game.playNextDrawing();
		count = 0;
		
		for(int num : selected4) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
	}
	
	@Test
	void getMatchedNumTest() {
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		Set<Integer> selected  = game.playNextDrawing();
		Set<Integer> matched = new HashSet<Integer>();
		int count = 0;
		
		for(int num : selected) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				matched.add(num);
				count++;
			}
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		Set<Integer> gameMatched = game.getMatchedNumbers();
		
		assertEquals(true, matched.size() == gameMatched.size(), "The number of matches should be the same in the manually made set and the set from getMatchedNumbers()");
		
		if(matched.size() == gameMatched.size()) {
			for(int match : gameMatched) {
				assertEquals(true, matched.contains(match), "The numbers from getMatchedNumbers() should be the same as the numbers in the manually made set");
			}
		}
	}
	
	@Test
	void amountWonTest() {
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);

		Set<Integer> selected  = game.playNextDrawing();
		int count = 0;
		
		for(int num : selected) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		double amount = 0.0;
		if(count == 0) {
			amount = 5.0;
		}
		else if(count == 5) {
			amount = 2.0;
		}
		else if(count == 6) {
			amount = 15.0;
		}
		else if(count == 7) {
			amount = 40.0;
		}
		else if(count == 8) {
			amount = 450.0;
		}
		else if(count == 9) {
			amount = 4250.0;
		}
		else if(count == 10) {
			amount = 100000.0;
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		assertEquals(amount, game.getAmountWonThisDrawing(), "The amount won in this drawing should be " + amount + " since there were " + count + " matches with 10 spots");
	}
	
	@Test
	void totalAmountWonTest() {
		game.chooseDrawings(2);
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);

		// Drawing 1
		Set<Integer> selected  = game.playNextDrawing();
		int count = 0;
		
		for(int num : selected) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		double amount = 0.0;
		if(count == 0) {
			amount = 5.0;
		}
		else if(count == 5) {
			amount = 2.0;
		}
		else if(count == 6) {
			amount = 15.0;
		}
		else if(count == 7) {
			amount = 40.0;
		}
		else if(count == 8) {
			amount = 450.0;
		}
		else if(count == 9) {
			amount = 4250.0;
		}
		else if(count == 10) {
			amount = 100000.0;
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		// Drawing 2
		Set<Integer> selected2  = game.playNextDrawing();
		
		count = 0;
		for(int num : selected2) {
			if(num == 9 || num == 80 || num == 1 || num == 64 || num == 27 || num == 3 || num == 43 || num == 39 || num == 55 || num == 78) {
				count++;
			}
		}
		
		if(count == 0) {
			amount += 5.0;
		}
		else if(count == 5) {
			amount += 2.0;
		}
		else if(count == 6) {
			amount += 15.0;
		}
		else if(count == 7) {
			amount += 40.0;
		}
		else if(count == 8) {
			amount += 450.0;
		}
		else if(count == 9) {
			amount += 4250.0;
		}
		else if(count == 10) {
			amount += 100000.0;
		}
		
		assertEquals(count, game.getMatchedCount(), "The number of matches should be " + count);
		
		assertEquals(amount, game.getTotalWinnings(), "The amount won in total should be " + amount);
	}
	
	@Test
	void clearSelectedSpotsTest() {
		game.clearSelectedSpots();
		
		for(int i = 0; i < 80; i++) {
			assertEquals(false, game.containsSpot(i), "After clearSelectedSpots() there should be no spots, so containsSpot() should return false");
		}
	}
	
	@Test
	void resetAllTest() {
		game.resetAll();
		
		assertEquals(0, game.getNumSpots(), "The number of spots should be 0 after resetting");
		assertEquals(0, game.getNumDrawings(), "The number of drawings should be 0 after resetting");
		assertEquals(0, game.numSpotsChosen(), "The number of spots chosen should be 0 after resetting");
		assertEquals(0, game.getMatchedCount(), "The number of spots matched should be 0 after resetting");
		assertEquals(0, game.getCurrentDrawingNumber(), "The current drawing should be 0 after resetting");
		assertEquals(0, game.getAmountWonThisDrawing(), "The amount won in the drawing should be 0 after resetting");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {9,80,1,64,27,3})
	void getSelectedNumsTest(int val) {
		Set<Integer> selected = game.getSelectedNums();
		assertEquals(true, selected.contains(val), val + " should be in the selected numbers");
	}
	
	// Payout tests
	@Test
	void payoutsReturnRightVals() {
		GameController.KenoMechanics mechanics = game.new KenoMechanics();
		assertEquals(3.0, mechanics.getPayout(1, 1));
		assertEquals(75.0, mechanics.getPayout(4, 4));
		assertEquals(0.0, mechanics.getPayout(4, 1));
		assertEquals(15.0, mechanics.getPayout(10, 6));
		assertEquals(50.0, mechanics.getPayout(8, 6));
	}
	
	@Test
	void payoutZeroForInvalidSpot() {
		GameController.KenoMechanics mechanics = game.new KenoMechanics();
		assertEquals(0.0, mechanics.getPayout(12, 2));
	}
	
	// KenoMechanics tests
	@Test
	void kenoGenerates20UniqueNums() {
		GameController.KenoMechanics mechanics = game.new KenoMechanics();
		Set<Integer> drawing = mechanics.generateDrawing();
		assertEquals(20, drawing.size());
		assertTrue(drawing.stream().allMatch(n -> n >= 1 && n <= 80));
	}
	
	//tests for reset and tracking winnings
	@Test
	void resetClearingEverything() {
		game.chooseSpots(4);
		game.addSpot(1);
		game.chooseDrawings(2);
		game.playNextDrawing();
		game.resetAll();
		assertEquals(0, game.getNumSpots());
		assertEquals(0, game.getNumDrawings());
		assertEquals(0, game.getSelectedNums().size());
	}
	
	@Test
	void totalWinningsIncreasing() {
		//making sure the total winnings increases or stays consistent after each drawing
		game.chooseSpots(4);
		game.addSpot(1);
		game.addSpot(2);
		game.chooseDrawings(2);
		game.playNextDrawing();
		double firstWin = game.getTotalWinnings();
		game.playNextDrawing();
		assertTrue(game.getTotalWinnings() >= firstWin);
	}
	
	@Test
	void getMatchedNumsandCount() {
		// checking that matched numbers and the match count are consistent
		game.chooseSpots(4);
		game.addSpot(1);
		game.addSpot(2);
		game.chooseDrawings(1);
		game.playNextDrawing();
		Set<Integer> matched = game.getMatchedNumbers();
		assertEquals(game.getMatchedCount(), matched.size());
	}
	
	@Test
	void payoutAsStringTest() {
		// 1 Spot Game
		game.chooseSpots(1);
		String payout = "\t0 matches -> $0.00\n\t1 matches -> $3.00\n";
		
		assertEquals(payout, game.payoutAsString(), "Wrong payout!!");
		
		// 4 Spot Game
		game.chooseSpots(4);
		payout = "\t0 matches -> $0.00\n\t1 matches -> $0.00\n\t2 matches -> $1.00\n\t3 matches -> $5.00\n\t4 matches -> $75.00\n";
		
		assertEquals(payout, game.payoutAsString(), "Wrong payout!!");
		
		// 8 Spot Game
		game.chooseSpots(8);
		payout = "\t0 matches -> $0.00\n\t1 matches -> $0.00\n\t2 matches -> $0.00\n\t3 matches -> $0.00\n\t4 matches -> $2.00\n"
				+ "\t5 matches -> $12.00\n\t6 matches -> $50.00\n\t7 matches -> $750.00\n\t8 matches -> $10000.00\n";
		
		assertEquals(payout, game.payoutAsString(), "Wrong payout!!");
		
		// 10 Spot Game
		game.chooseSpots(10);
		payout = "\t0 matches -> $5.00\n\t1 matches -> $0.00\n\t2 matches -> $0.00\n\t3 matches -> $0.00\n\t4 matches -> $0.00\n\t5 matches -> $2.00\n"
				+ "\t6 matches -> $15.00\n\t7 matches -> $40.00\n\t8 matches -> $450.00\n\t9 matches -> $4250.00\n\t10 matches -> $100000.00\n";
		
		assertEquals(payout, game.payoutAsString(), "Wrong payout!!");
	}
	
	@Test
	void drawingResConstructorTest() {
		List<Integer> list = new ArrayList<>();
		assertEquals(list, game.res.matchedNumsSinceStart(), "Wrong payout!!");
	}
	
	@Test
	void matchedNumsSinceStartTest() {
		game.chooseDrawings(4);
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.playNextDrawing();
		Set<Integer> selected1 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected2 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected3 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected4 = game.getMatchedNumbers();
		
		// Starting a new game
		game.resetAll();
		game.chooseDrawings(4);
		game.chooseSpots(4);
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.playNextDrawing();
		Set<Integer> selected5 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected6 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected7 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected8 = game.getMatchedNumbers();
		
		List<Integer> total = new ArrayList<>();
		total.addAll(selected1);
		total.addAll(selected2);
		total.addAll(selected3);
		total.addAll(selected4);
		total.addAll(selected5);
		total.addAll(selected6);
		total.addAll(selected7);
		total.addAll(selected8);
		
		assertEquals(total, game.res.matchedNumsSinceStart(), "Wrong list of matched numbers");
	}
	
	@Test
	void matchesSinceStartTest() {
		game.chooseDrawings(2);
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.playNextDrawing();
		Set<Integer> selected1 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected2 = game.getMatchedNumbers();
		
		// Starting a new game
		game.resetAll();
		game.chooseDrawings(2);
		game.chooseSpots(4);
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.playNextDrawing();
		Set<Integer> selected3 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected4 = game.getMatchedNumbers();
		
		List<Integer> total = new ArrayList<>();
		total.addAll(selected1);
		total.addAll(selected2);
		total.addAll(selected3);
		total.addAll(selected4);
		
		assertEquals(total.size(), game.res.matchesSinceStart(), "Wrong amount of matched numbers");
	}
	
	@Test
	void getDrawingsSinceStartTest() {
		game.chooseDrawings(4);
		game.playNextDrawing();
		game.playNextDrawing();
		game.playNextDrawing();
		game.playNextDrawing();
		
		assertEquals(4, game.res.getDrawingsSinceStart(), "There have been 4 drawings in total");
		
		// Starting a new game
		game.resetAll();
		game.chooseDrawings(2);
		game.chooseSpots(1);
		game.addSpot(43);
		
		game.playNextDrawing();
		game.playNextDrawing();
		
		assertEquals(6, game.res.getDrawingsSinceStart(), "There have been 6 drawings in total");
		
		// Starting a new game
		game.resetAll();
		game.chooseDrawings(3);
		game.chooseSpots(1);
		game.addSpot(43);
		
		game.playNextDrawing();
		game.playNextDrawing();
		game.playNextDrawing();
		
		assertEquals(9, game.res.getDrawingsSinceStart(), "There have been 9 drawings in total");
	}
	
	@Test
	void matchedNumStringTest() {
		game.chooseDrawings(4);
		// Adding 4 more spots to make it 10
		game.addSpot(43);
		game.addSpot(39);
		game.addSpot(55);
		game.addSpot(78);
		
		game.playNextDrawing();
		Set<Integer> selected1 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected2 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected3 = game.getMatchedNumbers();
		game.playNextDrawing();
		Set<Integer> selected4 = game.getMatchedNumbers();
		
		List<Integer> total = new ArrayList<>();
		total.addAll(selected1);
		total.addAll(selected2);
		total.addAll(selected3);
		total.addAll(selected4);
		
		String matched = "";
		for(int i = 0; i < total.size(); i++) {
			if(i == total.size() - 1) {
				matched += String.valueOf(total.get(i));
			}
			else {
				matched += String.valueOf(total.get(i)) + ", ";
			}
		}

		assertEquals(matched, game.res.matchedNumString(), "There have been 9 drawings in total");
	}
}
