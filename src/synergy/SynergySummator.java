package synergy;

import java.util.HashSet;
import java.util.List;

import main.console.Console;
import main.game.Side;
import main.statistics.HeroStatistics;

public class SynergySummator implements SynergyCalculator
{

	public HeroStatistics findBestHero(List<HeroStatistics> heroStats,HashSet<HeroStatistics> radiantTeam, HashSet<HeroStatistics> direTeam,
										Side side) {
		HeroStatistics heroStatistic = new HeroStatistics();
		HashSet<HeroStatistics> heroes = new HashSet<HeroStatistics>();
		for (int i = 0; i < heroStats.size(); ++i)
			heroes.add(heroStatistic.findHeroStatisticsWithName(heroStats.get(i).getName(), heroStats));
		for (HeroStatistics hero: direTeam)
			heroes.remove(hero);
		for (HeroStatistics hero: radiantTeam)
			heroes.remove(hero);
		HeroStatistics bestHero = null;
		double bestHeroSynergy = -10000;
		HashSet<HeroStatistics> lookingTeam = (side == Side.Radiant) ? radiantTeam : direTeam;
		HashSet<HeroStatistics> oppositeTeam = (side == Side.Radiant) ? direTeam : radiantTeam;
		for (HeroStatistics hero:heroes)
		{
			double synergy = 0;
			
			for (HeroStatistics anotherHero: lookingTeam)
				synergy += hero.getSynergyByHeroWith(anotherHero.getId());
			
			for (HeroStatistics anotherHero: oppositeTeam)
				synergy += hero.getSynergyByHeroVs(anotherHero.getId());
			
			if (synergy > bestHeroSynergy)
			{
				bestHeroSynergy = synergy;
				bestHero = hero;
			}
		}
		return bestHero;
	}

	public double getRelativeSynergy(List<HeroStatistics> first, List<HeroStatistics> second) {
		double synergy = 0.0;
		
		for(HeroStatistics firstListHero : first)
		{
			for (HeroStatistics anotherHero: first)
			{
				synergy += firstListHero.getSynergyByHeroWith(anotherHero.getId());
			}
			
			for (HeroStatistics anotherHero: second)
			{
				synergy += firstListHero.getSynergyByHeroVs(anotherHero.getId());
			}
		}
		
		return synergy;
	}
	
}
