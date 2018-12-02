package org.l2j.gameserver.network.l2.c2s;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.model.Player;
import org.l2j.gameserver.model.items.ManufactureItem;
import org.l2j.gameserver.network.l2.components.SystemMsg;
import org.l2j.gameserver.utils.TradeHelper;

public class RequestRecipeShopListSet extends L2GameClientPacket
{
	private int[] _recipes;
	private long[] _prices;
	private int _count;

	@Override
	protected void readImpl()
	{
		_count = readInt();
		if(_count * 12 > availableData() || _count > Short.MAX_VALUE || _count < 1)
		{
			_count = 0;
			return;
		}
		_recipes = new int[_count];
		_prices = new long[_count];
		for(int i = 0; i < _count; i++)
		{
			_recipes[i] = readInt();
			_prices[i] = readLong();
			if(_prices[i] < 0)
			{
				_count = 0;
				return;
			}
		}
	}

	@Override
	protected void runImpl()
	{
		Player manufacturer = getClient().getActiveChar();
		if(manufacturer == null || _count == 0)
			return;

		if(!TradeHelper.checksIfCanOpenStore(manufacturer, Player.STORE_PRIVATE_MANUFACTURE))
		{
			manufacturer.sendActionFailed();
			return;
		}

		if(_count > Config.MAX_PVTCRAFT_SLOTS)
		{
			manufacturer.sendPacket(SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
			return;
		}

		List<ManufactureItem> createList = new CopyOnWriteArrayList<ManufactureItem>();
		for(int i = 0; i < _count; i++)
		{
			int recipeId = _recipes[i];
			long price = _prices[i];
			if(!manufacturer.findRecipe(recipeId))
				continue;

			ManufactureItem mi = new ManufactureItem(recipeId, price);
			createList.add(mi);
		}

		if(!createList.isEmpty())
		{
			manufacturer.setCreateList(createList);
			manufacturer.setPrivateStoreType(Player.STORE_PRIVATE_MANUFACTURE);
			manufacturer.storePrivateStore();
			manufacturer.broadcastPrivateStoreInfo();
			manufacturer.sitDown(null);
			manufacturer.broadcastCharInfo();
		}

		manufacturer.sendActionFailed();
	}
}