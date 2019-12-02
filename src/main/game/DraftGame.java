package main.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.statistics.HeroStatistics;

public class DraftGame implements Game<HeroStatistics>
{
	
	private List<PickInfo> pickBanSequense = new ArrayList<PickInfo>();
	private List<HeroStatistics> heroes = new ArrayList<HeroStatistics>();
	private HashSet<String> availableHeroes = new HashSet<String>();
	private List<HeroStatistics> direPick = new ArrayList<HeroStatistics>();
	private List<HeroStatistics> radiantPick = new ArrayList<HeroStatistics>();
	private int phase = 0;
	
	public DraftGame(List<HeroStatistics> heroes)
	{
		this.heroes = heroes;
		fillPickBanSequense();
		for(HeroStatistics hero : heroes)
		{
			availableHeroes.add(hero.getName());
		}
	}
	private void fillPickBanSequense()
	{
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant,Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire,Action.Pick));
	}
	
	public void pick(Side side, HeroStatistics heroStat)
	{
		if(side == Side.Dire)
			direPick.add(heroStat);
		else
			radiantPick.add(heroStat);
		availableHeroes.remove(heroStat);
			
	}
	
	public void ban(HeroStatistics heroStat)
	{
		availableHeroes.remove(heroStat);
	}
	


	public String getState() {
		StringBuilder message = new StringBuilder();
		message.append("Герои сил света: ");
		for(HeroStatistics hero : radiantPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		message.append("Герои сил тьмы : ");
		for(HeroStatistics hero : direPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		double synergyRadiantRelationDire = getRatioSynergy(radiantPick, direPick);
		double synergyDireRelationRadiant = getRatioSynergy(direPick, radiantPick);
		message.append("Синергия героев сил света " + synergyRadiantRelationDire);
		message.append("Синергия героев сил тьмы " + synergyDireRelationRadiant);
		if(synergyRadiantRelationDire > synergyDireRelationRadiant)
			message.append("Вероятность победы сил света выше");
		else
			message.append("Вероятность победы сил тьмы выше");
		return message.toString();
			
	}
	
	public double getRatioSynergy(List<HeroStatistics> first, List<HeroStatistics> second)
	{
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

	public boolean setState(HeroStatistics message) {
		if(!availableHeroes.contains(message.getName()))
			return false;
		PickInfo currentAction = pickBanSequense.get(phase);
		phase++;
		if(currentAction.getAction() == Action.Pick)
			pick(currentAction.getSide(), message);
		else
			ban(message);
		return true;
		
	}
	
	public boolean isEnd() {
		return phase + 1 == pickBanSequense.size();
	}
}
