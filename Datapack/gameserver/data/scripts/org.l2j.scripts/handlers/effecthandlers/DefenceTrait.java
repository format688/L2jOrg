/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.effecthandlers;

import org.l2j.gameserver.model.StatsSet;
import org.l2j.gameserver.model.actor.L2Character;
import org.l2j.gameserver.model.actor.stat.CharStat;
import org.l2j.gameserver.model.effects.AbstractEffect;
import org.l2j.gameserver.model.items.instance.L2ItemInstance;
import org.l2j.gameserver.model.skills.Skill;
import org.l2j.gameserver.model.stats.TraitType;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Defence Trait effect implementation.
 * @author NosBit
 */
public final class DefenceTrait extends AbstractEffect
{
	private final Map<TraitType, Float> _defenceTraits = new HashMap<>();
	
	public DefenceTrait(StatsSet params)
	{
		if (params.isEmpty())
		{
			LOGGER.warn(": must have parameters.");
			return;
		}
		
		for (Entry<String, Object> param : params.getSet().entrySet())
		{
			try
			{
				final TraitType traitType = TraitType.valueOf(param.getKey());
				final float value = Float.parseFloat((String) param.getValue());
				if (value == 0)
				{
					continue;
				}
				_defenceTraits.put(traitType, (value + 100) / 100);
			}
			catch (NumberFormatException e)
			{
				LOGGER.warn(": value of " + param.getKey() + " must be float value " + param.getValue() + " found.");
			}
			catch (Exception e)
			{
				LOGGER.warn(": value of L2TraitType enum required but found: " + param.getKey());
			}
		}
	}
	
	@Override
	public void onExit(L2Character effector, L2Character effected, Skill skill)
	{
		final CharStat charStat = effected.getStat();
		synchronized (charStat.getDefenceTraits())
		{
			for (Entry<TraitType, Float> trait : _defenceTraits.entrySet())
			{
				if (trait.getValue() < 2.0f)
				{
					if (charStat.getDefenceTraitsCount()[trait.getKey().ordinal()] == 0)
					{
						continue;
					}
					
					charStat.getDefenceTraits()[trait.getKey().ordinal()] /= trait.getValue();
					charStat.getDefenceTraitsCount()[trait.getKey().ordinal()]--;
				}
				else
				{
					if (charStat.getTraitsInvul()[trait.getKey().ordinal()] == 0)
					{
						continue;
					}
					
					charStat.getTraitsInvul()[trait.getKey().ordinal()]--;
				}
			}
		}
	}
	
	@Override
	public void onStart(L2Character effector, L2Character effected, Skill skill, L2ItemInstance item)
	{
		final CharStat charStat = effected.getStat();
		synchronized (charStat.getDefenceTraits())
		{
			for (Entry<TraitType, Float> trait : _defenceTraits.entrySet())
			{
				if (trait.getValue() < 2.0f)
				{
					charStat.getDefenceTraits()[trait.getKey().ordinal()] *= trait.getValue();
					charStat.getDefenceTraitsCount()[trait.getKey().ordinal()]++;
				}
				else
				{
					charStat.getTraitsInvul()[trait.getKey().ordinal()]++;
				}
			}
		}
	}
}
