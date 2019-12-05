package main.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.statistics.HeroStatistics;
import synergy.SynergyCalculator;
import synergy.SynergySummator;

public class DraftGame implements Game<HeroStatistics> {

	private List<PickInfo> pickBanSequense = new ArrayList<PickInfo>();
	private List<HeroStatistics> heroes = new ArrayList<HeroStatistics>();
	private HashSet<String> availableHeroes = new HashSet<String>();
	private List<HeroStatistics> direPick = new ArrayList<HeroStatistics>();
	private List<HeroStatistics> radiantPick = new ArrayList<HeroStatistics>();
	private List<HeroStatistics> banedHeroes = new ArrayList<HeroStatistics>();
	private int phase = 0;
	private Action currentAction = null;
	private Action previousAction = null;
	private HeroStatistics lastHero = null;
	private Side currentPlayer = null;

	public DraftGame(List<HeroStatistics> heroes) {
		this.heroes = heroes;
		fillPickBanSequense();
		for (HeroStatistics hero : heroes) {
			availableHeroes.add(hero.getName());
		}
	}

	private void fillPickBanSequense() {
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Ban));
		pickBanSequense.add(new PickInfo(Side.Radiant, Action.Pick));
		pickBanSequense.add(new PickInfo(Side.Dire, Action.Pick));
		currentAction = pickBanSequense.get(0).getAction();
		currentPlayer = pickBanSequense.get(0).getSide();
	}

	public void pick(Side side, HeroStatistics heroStat) {
		if (side == Side.Dire)
			direPick.add(heroStat);
		else
			radiantPick.add(heroStat);
		availableHeroes.remove(heroStat.getName());

	}

	public void ban(HeroStatistics heroStat) {
		availableHeroes.remove(heroStat.getName());
		banedHeroes.add(heroStat);
	}

	public String getState(int id) {
		Side curSide = (id == 0) ? Side.Radiant : Side.Dire;
		StringBuilder message = new StringBuilder();
		if (!isEnd()) {
			message.append("Герои сил света: ");
			for (HeroStatistics hero : radiantPick)
				message.append(hero.getName() + " ");
			message.append("\n");
			message.append("Герои сил тьмы : ");
			for (HeroStatistics hero : direPick)
				message.append(hero.getName() + " ");
			message.append("\n");
			message.append("Запрещенные герои: ");
			for (HeroStatistics hero : banedHeroes)
				message.append(hero.getName() + " ");
			message.append("\n");
			if (previousAction != null) {
				String action = (previousAction == Action.Pick) ? "выбран" : "запрещен";
				message.append(String.format("Герой %s %s" + "\n", action, lastHero.getName()));
			}
			if (currentAction != null) {
				String action = (currentAction == Action.Pick) ? "выбирают" : "запрещают";
				String side = (currentPlayer == Side.Radiant) ? "света" : "тьмы";
				message.append(String.format("Силы %s %s героя" + "\n", side, action));
				if (currentPlayer.equals(curSide))
					message.append("Ваш ход \n");

				else
					message.append("Ожидайте хода соперника \n");
			}
			return message.toString();
		}
		message.append("Герои сил света: ");
		for (HeroStatistics hero : radiantPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		message.append("Герои сил тьмы : ");
		for (HeroStatistics hero : direPick)
			message.append(hero.getName() + " ");
		message.append("\n");
		SynergyCalculator synergyCalculator = new SynergySummator();
		double synergyRadiantRelationDire = synergyCalculator.getRelativeSynergy(radiantPick, direPick);
		double synergyDireRelationRadiant = synergyCalculator.getRelativeSynergy(direPick, radiantPick);
		message.append("Синергия героев сил света " + synergyRadiantRelationDire + "\n");
		message.append("Синергия героев сил тьмы " + synergyDireRelationRadiant + "\n");
		if (synergyRadiantRelationDire > synergyDireRelationRadiant)
			message.append("Вероятность победы сил света выше");
		else
			message.append("Вероятность победы сил тьмы выше");
		return message.toString();

	}

	public boolean setState(HeroStatistics message) {
		lastHero = message;
		if (!availableHeroes.contains(message.getName()))
			return false;
		if (currentAction == Action.Pick)
			pick(currentPlayer, message);
		else
			ban(message);
		previousAction = currentAction;
		phase++;
		if (!isEnd()) {
			currentAction = pickBanSequense.get(phase).getAction();
			currentPlayer = pickBanSequense.get(phase).getSide();
		}
		return true;

	}

	public boolean isEnd() {
		return !(phase < pickBanSequense.size());
	}

	public int getTurn() {
		return (currentPlayer == Side.Radiant) ? 0 : 1;
	}
}
