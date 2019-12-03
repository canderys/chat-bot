package main.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.statistics.HeroStatistics;
import synergy.SynergyCalculator;
import synergy.SynergySummator;

public class DraftGame implements Game<HeroStatistics>
{
	
	private List<PickInfo> pickBanSequense = new ArrayList<PickInfo>();
	private List<HeroStatistics> heroes = new ArrayList<HeroStatistics>();
	private HashSet<String> availableHeroes = new HashSet<String>();
	private List<HeroStatistics> direPick = new ArrayList<HeroStatistics>();
	private List<HeroStatistics> radiantPick = new ArrayList<HeroStatistics>();
	private int phase = 0;
	private boolean isBeginGame = true;
	private Action lastAction = null;
	private Action nextAction = null;
	private HeroStatistics lastHero = null;
	private Side currentPlayer = Side.Radiant;
	
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
		nextAction = pickBanSequense.get(0).getAction();
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
		if(isBeginGame)
		{
			message.append("В этой игре нужно выбирать и запрещать героев для команды сил тьмы и сил света" + "\n");
			message.append("Чтобы выбрать или запретить героя просто напишите его имя" + "\n");
			isBeginGame = false;
			return message.toString();
		}
		if(!isEnd()) {
			if(lastAction != null) {
				String action = (lastAction == Action.Pick) ? "выбран" : "запрещен";
				message.append(String.format("Герой %s %s" + "\n",lastHero.getName(), action));
			}
			if(nextAction != null) {
				String action = (nextAction == Action.Pick) ? "выбрать" : "запретить";
				message.append(String.format("Сейчас вы должны %s героя" + "\n", action));
			}
			return message.toString();
		}
		message.append("Герои сил света: ");
		for(HeroStatistics hero : radiantPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		message.append("Герои сил тьмы : ");
		for(HeroStatistics hero : direPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		SynergyCalculator synergyCalculator = new SynergySummator();
		double synergyRadiantRelationDire = synergyCalculator.getRelativeSynergy(radiantPick, direPick);
		double synergyDireRelationRadiant = synergyCalculator.getRelativeSynergy(direPick, radiantPick);
		message.append("Синергия героев сил света " + synergyRadiantRelationDire + "\n");
		message.append("Синергия героев сил тьмы " + synergyDireRelationRadiant + "\n");
		if(synergyRadiantRelationDire > synergyDireRelationRadiant)
			message.append("Вероятность победы сил света выше");
		else
			message.append("Вероятность победы сил тьмы выше");
		return message.toString();
			
	}
	
	public double getRelativeSynergy(List<HeroStatistics> first, List<HeroStatistics> second)
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
		lastHero = message;
		if(!availableHeroes.contains(message.getName()))
			return false;
		PickInfo currentAction = pickBanSequense.get(phase);
		currentPlayer = currentAction.getSide();
		phase++;
		lastAction  = currentAction.getAction();
		if(phase < pickBanSequense.size())
			nextAction = pickBanSequense.get(phase).getAction();
		else
			nextAction = null;
		if(currentAction.getAction() == Action.Pick)
			pick(currentAction.getSide(), message);
		else
			ban(message);
		return true;
		
	}
	
	public boolean isEnd() {
		return !(phase < pickBanSequense.size());
	}
	
	public int getTurn() {
		return (currentPlayer == Side.Radiant) ? 0 : 1;
	}
}
