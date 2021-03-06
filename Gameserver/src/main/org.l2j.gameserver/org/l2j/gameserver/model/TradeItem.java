package org.l2j.gameserver.model;

import org.l2j.gameserver.enums.AttributeType;
import org.l2j.gameserver.model.ensoul.EnsoulOption;
import org.l2j.gameserver.model.items.L2Item;
import org.l2j.gameserver.model.items.instance.L2ItemInstance;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class TradeItem {
    private final L2Item _item;
    private final int _location;
    private final int _type1;
    private final int _type2;
    private final int[] _elemDefAttr =
            {
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
            };
    private final int[] _enchantOptions;
    private int _objectId;
    private int _enchant;
    private long _count;
    private long _storeCount;
    private long _price;
    private byte _elemAtkType;
    private int _elemAtkPower;
    private Collection<EnsoulOption> _soulCrystalOptions;
    private Collection<EnsoulOption> _soulCrystalSpecialOptions;
    private int _augmentationOption1 = -1;
    private int _augmentationOption2 = -1;

    public TradeItem(L2ItemInstance item, long count, long price) {
        Objects.requireNonNull(item);
        _objectId = item.getObjectId();
        _item = item.getItem();
        _location = item.getLocationSlot();
        _enchant = item.getEnchantLevel();
        _type1 = item.getCustomType1();
        _type2 = item.getCustomType2();
        _count = count;
        _price = price;
        _elemAtkType = item.getAttackAttributeType().getClientId();
        _elemAtkPower = item.getAttackAttributePower();
        for (AttributeType type : AttributeType.ATTRIBUTE_TYPES) {
            _elemDefAttr[type.getClientId()] = item.getDefenceAttribute(type);
        }
        _enchantOptions = item.getEnchantOptions();
        _soulCrystalOptions = item.getSpecialAbilities();
        _soulCrystalSpecialOptions = item.getAdditionalSpecialAbilities();

        if (item.getAugmentation() != null) {
            _augmentationOption1 = item.getAugmentation().getOption1Id();
            _augmentationOption1 = item.getAugmentation().getOption2Id();
        }
    }

    public TradeItem(L2Item item, long count, long price) {
        Objects.requireNonNull(item);
        _objectId = 0;
        _item = item;
        _location = 0;
        _enchant = 0;
        _type1 = 0;
        _type2 = 0;
        _count = count;
        _storeCount = count;
        _price = price;
        _elemAtkType = AttributeType.NONE.getClientId();
        _elemAtkPower = 0;
        _enchantOptions = L2ItemInstance.DEFAULT_ENCHANT_OPTIONS;
        _soulCrystalOptions = Collections.emptyList();
        _soulCrystalSpecialOptions = Collections.emptyList();
    }

    public TradeItem(TradeItem item, long count, long price) {
        Objects.requireNonNull(item);
        _objectId = item.getObjectId();
        _item = item.getItem();
        _location = item.getLocationSlot();
        _enchant = item.getEnchant();
        _type1 = item.getCustomType1();
        _type2 = item.getCustomType2();
        _count = count;
        _storeCount = count;
        _price = price;
        _elemAtkType = item.getAttackElementType();
        _elemAtkPower = item.getAttackElementPower();
        for (byte i = 0; i < 6; i++) {
            _elemDefAttr[i] = item.getElementDefAttr(i);
        }
        _enchantOptions = item.getEnchantOptions();
        _soulCrystalOptions = item.getSoulCrystalOptions();
        _soulCrystalSpecialOptions = item.getSoulCrystalSpecialOptions();
    }

    public int getObjectId() {
        return _objectId;
    }

    public void setObjectId(int objectId) {
        _objectId = objectId;
    }

    public L2Item getItem() {
        return _item;
    }

    public int getLocationSlot() {
        return _location;
    }

    public int getEnchant() {
        return _enchant;
    }

    public void setEnchant(int enchant) {
        _enchant = enchant;
    }

    public int getCustomType1() {
        return _type1;
    }

    public int getCustomType2() {
        return _type2;
    }

    public long getCount() {
        return _count;
    }

    public void setCount(long count) {
        _count = count;
    }

    public long getStoreCount() {
        return _storeCount;
    }

    public long getPrice() {
        return _price;
    }

    public void setPrice(long price) {
        _price = price;
    }

    public byte getAttackElementType() {
        return _elemAtkType;
    }

    public void setAttackElementType(AttributeType attackElement) {
        _elemAtkType = attackElement.getClientId();
    }

    public int getAttackElementPower() {
        return _elemAtkPower;
    }

    public void setAttackElementPower(int attackElementPower) {
        _elemAtkPower = attackElementPower;
    }

    public void setElementDefAttr(AttributeType element, int value) {
        _elemDefAttr[element.getClientId()] = value;
    }

    public int getElementDefAttr(byte i) {
        return _elemDefAttr[i];
    }

    public int[] getEnchantOptions() {
        return _enchantOptions;
    }

    public Collection<EnsoulOption> getSoulCrystalOptions() {
        return _soulCrystalOptions == null ? Collections.emptyList() : _soulCrystalOptions;
    }

    public void setSoulCrystalOptions(Collection<EnsoulOption> soulCrystalOptions) {
        _soulCrystalOptions = soulCrystalOptions;
    }

    public Collection<EnsoulOption> getSoulCrystalSpecialOptions() {
        return _soulCrystalSpecialOptions == null ? Collections.emptyList() : _soulCrystalSpecialOptions;
    }

    public void setSoulCrystalSpecialOptions(Collection<EnsoulOption> soulCrystalSpecialOptions) {
        _soulCrystalSpecialOptions = soulCrystalSpecialOptions;
    }

    public void setAugmentation(int option1, int option2) {
        _augmentationOption1 = option1;
        _augmentationOption2 = option2;
    }

    public int getAugmentationOption1() {
        return _augmentationOption1;
    }

    public int getAugmentationOption2() {
        return _augmentationOption2;
    }
}
