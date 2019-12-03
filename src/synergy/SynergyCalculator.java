package synergy;

import java.util.HashSet;
import java.util.List;

import main.game.Side;
import main.statistics.HeroStatistics;

public interface SynergyCalculator {
	public HeroStatistics findBestHero(List<HeroStatistics> heroStats, HashSet<HeroStatistics> radiantTeam, 
										HashSet<HeroStatistics> direTeam, Side side);
	public double getRelativeSynergy(List<HeroStatistics> first, List<HeroStatistics> second);
}
