package org.l2j.gameserver.model.base;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.enums.Race;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author luisantonioa
 */
public enum PlayerClass {
    HumanFighter(Race.HUMAN, ClassType.Fighter, ClassLevel.FIRST),
    Warrior(Race.HUMAN, ClassType.Fighter, ClassLevel.SECOND),
    Gladiator(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    Warlord(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    HumanKnight(Race.HUMAN, ClassType.Fighter, ClassLevel.SECOND),
    Paladin(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    DarkAvenger(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    Rogue(Race.HUMAN, ClassType.Fighter, ClassLevel.SECOND),
    TreasureHunter(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    Hawkeye(Race.HUMAN, ClassType.Fighter, ClassLevel.THIRD),
    HumanMystic(Race.HUMAN, ClassType.Mystic, ClassLevel.FIRST),
    HumanWizard(Race.HUMAN, ClassType.Mystic, ClassLevel.SECOND),
    Sorceror(Race.HUMAN, ClassType.Mystic, ClassLevel.THIRD),
    Necromancer(Race.HUMAN, ClassType.Mystic, ClassLevel.THIRD),
    Warlock(Race.HUMAN, ClassType.Mystic, ClassLevel.THIRD),
    Cleric(Race.HUMAN, ClassType.Priest, ClassLevel.SECOND),
    Bishop(Race.HUMAN, ClassType.Priest, ClassLevel.THIRD),
    Prophet(Race.HUMAN, ClassType.Priest, ClassLevel.THIRD),

    ElvenFighter(Race.ELF, ClassType.Fighter, ClassLevel.FIRST),
    ElvenKnight(Race.ELF, ClassType.Fighter, ClassLevel.SECOND),
    TempleKnight(Race.ELF, ClassType.Fighter, ClassLevel.THIRD),
    Swordsinger(Race.ELF, ClassType.Fighter, ClassLevel.THIRD),
    ElvenScout(Race.ELF, ClassType.Fighter, ClassLevel.SECOND),
    Plainswalker(Race.ELF, ClassType.Fighter, ClassLevel.THIRD),
    SilverRanger(Race.ELF, ClassType.Fighter, ClassLevel.THIRD),
    ElvenMystic(Race.ELF, ClassType.Mystic, ClassLevel.FIRST),
    ElvenWizard(Race.ELF, ClassType.Mystic, ClassLevel.SECOND),
    Spellsinger(Race.ELF, ClassType.Mystic, ClassLevel.THIRD),
    ElementalSummoner(Race.ELF, ClassType.Mystic, ClassLevel.THIRD),
    ElvenOracle(Race.ELF, ClassType.Priest, ClassLevel.SECOND),
    ElvenElder(Race.ELF, ClassType.Priest, ClassLevel.THIRD),

    DarkElvenFighter(Race.DARK_ELF, ClassType.Fighter, ClassLevel.FIRST),
    PalusKnight(Race.DARK_ELF, ClassType.Fighter, ClassLevel.SECOND),
    ShillienKnight(Race.DARK_ELF, ClassType.Fighter, ClassLevel.THIRD),
    Bladedancer(Race.DARK_ELF, ClassType.Fighter, ClassLevel.THIRD),
    Assassin(Race.DARK_ELF, ClassType.Fighter, ClassLevel.SECOND),
    AbyssWalker(Race.DARK_ELF, ClassType.Fighter, ClassLevel.THIRD),
    PhantomRanger(Race.DARK_ELF, ClassType.Fighter, ClassLevel.THIRD),
    DarkElvenMystic(Race.DARK_ELF, ClassType.Mystic, ClassLevel.FIRST),
    DarkElvenWizard(Race.DARK_ELF, ClassType.Mystic, ClassLevel.SECOND),
    Spellhowler(Race.DARK_ELF, ClassType.Mystic, ClassLevel.THIRD),
    PhantomSummoner(Race.DARK_ELF, ClassType.Mystic, ClassLevel.THIRD),
    ShillienOracle(Race.DARK_ELF, ClassType.Priest, ClassLevel.SECOND),
    ShillienElder(Race.DARK_ELF, ClassType.Priest, ClassLevel.THIRD),

    OrcFighter(Race.ORC, ClassType.Fighter, ClassLevel.FIRST),
    OrcRaider(Race.ORC, ClassType.Fighter, ClassLevel.SECOND),
    Destroyer(Race.ORC, ClassType.Fighter, ClassLevel.THIRD),
    OrcMonk(Race.ORC, ClassType.Fighter, ClassLevel.SECOND),
    Tyrant(Race.ORC, ClassType.Fighter, ClassLevel.THIRD),
    OrcMystic(Race.ORC, ClassType.Mystic, ClassLevel.FIRST),
    OrcShaman(Race.ORC, ClassType.Mystic, ClassLevel.SECOND),
    Overlord(Race.ORC, ClassType.Mystic, ClassLevel.THIRD),
    Warcryer(Race.ORC, ClassType.Mystic, ClassLevel.THIRD),

    DwarvenFighter(Race.DWARF, ClassType.Fighter, ClassLevel.FIRST),
    DwarvenScavenger(Race.DWARF, ClassType.Fighter, ClassLevel.SECOND),
    BountyHunter(Race.DWARF, ClassType.Fighter, ClassLevel.THIRD),
    DwarvenArtisan(Race.DWARF, ClassType.Fighter, ClassLevel.SECOND),
    Warsmith(Race.DWARF, ClassType.Fighter, ClassLevel.THIRD),

    dummyEntry1(null, null, null),
    dummyEntry2(null, null, null),
    dummyEntry3(null, null, null),
    dummyEntry4(null, null, null),
    dummyEntry5(null, null, null),
    dummyEntry6(null, null, null),
    dummyEntry7(null, null, null),
    dummyEntry8(null, null, null),
    dummyEntry9(null, null, null),
    dummyEntry10(null, null, null),
    dummyEntry11(null, null, null),
    dummyEntry12(null, null, null),
    dummyEntry13(null, null, null),
    dummyEntry14(null, null, null),
    dummyEntry15(null, null, null),
    dummyEntry16(null, null, null),
    dummyEntry17(null, null, null),
    dummyEntry18(null, null, null),
    dummyEntry19(null, null, null),
    dummyEntry20(null, null, null),
    dummyEntry21(null, null, null),
    dummyEntry22(null, null, null),
    dummyEntry23(null, null, null),
    dummyEntry24(null, null, null),
    dummyEntry25(null, null, null),
    dummyEntry26(null, null, null),
    dummyEntry27(null, null, null),
    dummyEntry28(null, null, null),
    dummyEntry29(null, null, null),
    dummyEntry30(null, null, null),
    /*
     * (3rd classes)
     */
    duelist(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    dreadnought(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    phoenixKnight(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    hellKnight(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    sagittarius(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    adventurer(Race.HUMAN, ClassType.Fighter, ClassLevel.FOURTH),
    archmage(Race.HUMAN, ClassType.Mystic, ClassLevel.FOURTH),
    soultaker(Race.HUMAN, ClassType.Mystic, ClassLevel.FOURTH),
    arcanaLord(Race.HUMAN, ClassType.Mystic, ClassLevel.FOURTH),
    cardinal(Race.HUMAN, ClassType.Priest, ClassLevel.FOURTH),
    hierophant(Race.HUMAN, ClassType.Priest, ClassLevel.FOURTH),

    evaTemplar(Race.ELF, ClassType.Fighter, ClassLevel.FOURTH),
    swordMuse(Race.ELF, ClassType.Fighter, ClassLevel.FOURTH),
    windRider(Race.ELF, ClassType.Fighter, ClassLevel.FOURTH),
    moonlightSentinel(Race.ELF, ClassType.Fighter, ClassLevel.FOURTH),
    mysticMuse(Race.ELF, ClassType.Mystic, ClassLevel.FOURTH),
    elementalMaster(Race.ELF, ClassType.Mystic, ClassLevel.FOURTH),
    evaSaint(Race.ELF, ClassType.Priest, ClassLevel.FOURTH),

    shillienTemplar(Race.DARK_ELF, ClassType.Fighter, ClassLevel.FOURTH),
    spectralDancer(Race.DARK_ELF, ClassType.Fighter, ClassLevel.FOURTH),
    ghostHunter(Race.DARK_ELF, ClassType.Fighter, ClassLevel.FOURTH),
    ghostSentinel(Race.DARK_ELF, ClassType.Fighter, ClassLevel.FOURTH),
    stormScreamer(Race.DARK_ELF, ClassType.Mystic, ClassLevel.FOURTH),
    spectralMaster(Race.DARK_ELF, ClassType.Mystic, ClassLevel.FOURTH),
    shillienSaint(Race.DARK_ELF, ClassType.Priest, ClassLevel.FOURTH),

    titan(Race.ORC, ClassType.Fighter, ClassLevel.FOURTH),
    grandKhavatari(Race.ORC, ClassType.Fighter, ClassLevel.FOURTH),
    dominator(Race.ORC, ClassType.Mystic, ClassLevel.FOURTH),
    doomcryer(Race.ORC, ClassType.Mystic, ClassLevel.FOURTH),

    fortuneSeeker(Race.DWARF, ClassType.Fighter, ClassLevel.FOURTH),
    maestro(Race.DWARF, ClassType.Fighter, ClassLevel.FOURTH),

    dummyEntry31(null, null, null),
    dummyEntry32(null, null, null),
    dummyEntry33(null, null, null),
    dummyEntry34(null, null, null),

    maleSoldier(Race.KAMAEL, ClassType.Fighter, ClassLevel.FIRST),
    femaleSoldier(Race.KAMAEL, ClassType.Fighter, ClassLevel.FIRST),
    trooper(Race.KAMAEL, ClassType.Fighter, ClassLevel.SECOND),
    warder(Race.KAMAEL, ClassType.Fighter, ClassLevel.SECOND),
    berserker(Race.KAMAEL, ClassType.Fighter, ClassLevel.THIRD),
    maleSoulbreaker(Race.KAMAEL, ClassType.Fighter, ClassLevel.THIRD),
    femaleSoulbreaker(Race.KAMAEL, ClassType.Fighter, ClassLevel.THIRD),
    arbalester(Race.KAMAEL, ClassType.Fighter, ClassLevel.THIRD),
    doombringer(Race.KAMAEL, ClassType.Fighter, ClassLevel.FOURTH),
    maleSoulhound(Race.KAMAEL, ClassType.Fighter, ClassLevel.FOURTH),
    femaleSoulhound(Race.KAMAEL, ClassType.Fighter, ClassLevel.FOURTH),
    trickster(Race.KAMAEL, ClassType.Fighter, ClassLevel.FOURTH),
    inspector(Race.KAMAEL, ClassType.Fighter, ClassLevel.THIRD),
    judicator(Race.KAMAEL, ClassType.Fighter, ClassLevel.FOURTH),

    dummyEntry35(null, null, null),
    dummyEntry36(null, null, null),

    sigelKnight(null, ClassType.Fighter, null),
    tyrWarrior(null, ClassType.Fighter, null),
    otherRogue(null, ClassType.Fighter, null),
    yrArcher(null, ClassType.Fighter, null),
    feohWizard(null, ClassType.Mystic, null),
    issEnchanter(null, ClassType.Priest, null),
    wynnSummoner(null, ClassType.Mystic, null),
    eolhHealer(null, ClassType.Priest, null),

    dummyEntry37(null, null, null),

    sigelPhoenixKnight(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    sigelHellKnight(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    sigelEvasTemplar(Race.ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    sigelShilenTemplar(Race.DARK_ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrDuelist(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrDreadnought(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrTitan(Race.ORC, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrGrandKhavatari(Race.ORC, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrMaestro(Race.DWARF, ClassType.Fighter, ClassLevel.AWAKEN),
    tyrrDoombringer(Race.KAMAEL, ClassType.Fighter, ClassLevel.AWAKEN),
    othellAdventurer(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    othellWindRider(Race.ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    othellGhostHunter(Race.DARK_ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    othellFortuneSeeker(Race.DWARF, ClassType.Fighter, ClassLevel.AWAKEN),
    yulSagittarius(Race.HUMAN, ClassType.Fighter, ClassLevel.AWAKEN),
    yulMoonlightSentinel(Race.ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    yulGhostSentinel(Race.DARK_ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    yulTrickster(Race.KAMAEL, ClassType.Fighter, ClassLevel.AWAKEN),
    feohArchmage(Race.HUMAN, ClassType.Mystic, ClassLevel.AWAKEN),
    feohSoultaker(Race.HUMAN, ClassType.Mystic, ClassLevel.AWAKEN),
    feohMysticMuse(Race.ELF, ClassType.Mystic, ClassLevel.AWAKEN),
    feoStormScreamer(Race.DARK_ELF, ClassType.Mystic, ClassLevel.AWAKEN),
    feohSoulHound(Race.KAMAEL, ClassType.Mystic, ClassLevel.AWAKEN), // fix me
    issHierophant(Race.HUMAN, ClassType.Priest, ClassLevel.AWAKEN),
    issSwordMuse(Race.ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    issSpectralDancer(Race.DARK_ELF, ClassType.Fighter, ClassLevel.AWAKEN),
    issDominator(Race.ORC, ClassType.Priest, ClassLevel.AWAKEN),
    issDoomcryer(Race.ORC, ClassType.Priest, ClassLevel.AWAKEN),
    wynnArcanaLord(Race.HUMAN, ClassType.Mystic, ClassLevel.AWAKEN),
    wynnElementalMaster(Race.ELF, ClassType.Mystic, ClassLevel.AWAKEN),
    wynnSpectralMaster(Race.DARK_ELF, ClassType.Mystic, ClassLevel.AWAKEN),
    aeoreCardinal(Race.HUMAN, ClassType.Priest, ClassLevel.AWAKEN),
    aeoreEvaSaint(Race.ELF, ClassType.Priest, ClassLevel.AWAKEN),
    aeoreShillienSaint(Race.DARK_ELF, ClassType.Priest, ClassLevel.AWAKEN),

    ertheiaFighter(Race.ERTHEIA, ClassType.Fighter, ClassLevel.FIRST),
    ertheiaWizzard(Race.ERTHEIA, ClassType.Mystic, ClassLevel.FIRST),

    marauder(Race.ERTHEIA, ClassType.Fighter, ClassLevel.THIRD),
    cloudBreaker(Race.ERTHEIA, ClassType.Mystic, ClassLevel.THIRD),

    ripper(Race.ERTHEIA, ClassType.Fighter, ClassLevel.FOURTH),
    Stratomancer(Race.ERTHEIA, ClassType.Mystic, ClassLevel.FOURTH),

    eviscerator(Race.ERTHEIA, ClassType.Fighter, ClassLevel.AWAKEN),
    sayhaSeer(Race.ERTHEIA, ClassType.Mystic, ClassLevel.AWAKEN);

    private static final Set<PlayerClass> mainSubclassSet;
    private static final Set<PlayerClass> neverSubclassed = EnumSet.of(Overlord, Warsmith);

    private static final Set<PlayerClass> subclasseSet1 = EnumSet.of(DarkAvenger, Paladin, TempleKnight, ShillienKnight);
    private static final Set<PlayerClass> subclasseSet2 = EnumSet.of(TreasureHunter, AbyssWalker, Plainswalker);
    private static final Set<PlayerClass> subclasseSet3 = EnumSet.of(Hawkeye, SilverRanger, PhantomRanger);
    private static final Set<PlayerClass> subclasseSet4 = EnumSet.of(Warlock, ElementalSummoner, PhantomSummoner);
    private static final Set<PlayerClass> subclasseSet5 = EnumSet.of(Sorceror, Spellsinger, Spellhowler);
    private static final EnumMap<PlayerClass, Set<PlayerClass>> subclassSetMap = new EnumMap<>(PlayerClass.class);

    static {
        final Set<PlayerClass> subclasses = getSet(null, ClassLevel.THIRD);
        subclasses.removeAll(neverSubclassed);

        mainSubclassSet = subclasses;

        subclassSetMap.put(DarkAvenger, subclasseSet1);
        subclassSetMap.put(Paladin, subclasseSet1);
        subclassSetMap.put(TempleKnight, subclasseSet1);
        subclassSetMap.put(ShillienKnight, subclasseSet1);

        subclassSetMap.put(TreasureHunter, subclasseSet2);
        subclassSetMap.put(AbyssWalker, subclasseSet2);
        subclassSetMap.put(Plainswalker, subclasseSet2);

        subclassSetMap.put(Hawkeye, subclasseSet3);
        subclassSetMap.put(SilverRanger, subclasseSet3);
        subclassSetMap.put(PhantomRanger, subclasseSet3);

        subclassSetMap.put(Warlock, subclasseSet4);
        subclassSetMap.put(ElementalSummoner, subclasseSet4);
        subclassSetMap.put(PhantomSummoner, subclasseSet4);

        subclassSetMap.put(Sorceror, subclasseSet5);
        subclassSetMap.put(Spellsinger, subclasseSet5);
        subclassSetMap.put(Spellhowler, subclasseSet5);
    }

    private Race _race;
    private ClassLevel _level;
    private ClassType _type;

    private PlayerClass(Race race, ClassType pType, ClassLevel pLevel) {
        _race = race;
        _level = pLevel;
        _type = pType;
    }

    public static EnumSet<PlayerClass> getSet(Race race, ClassLevel level) {
        final EnumSet<PlayerClass> allOf = EnumSet.noneOf(PlayerClass.class);

        for (PlayerClass playerClass : EnumSet.allOf(PlayerClass.class)) {
            if ((race == null) || playerClass.isOfRace(race)) {
                if ((level == null) || playerClass.isOfLevel(level)) {
                    allOf.add(playerClass);
                }
            }
        }
        return allOf;
    }

    public final Set<PlayerClass> getAvailableSubclasses(L2PcInstance player) {
        Set<PlayerClass> subclasses = null;

        if (_level == ClassLevel.THIRD) {
            if (player.getRace() != Race.KAMAEL) {
                subclasses = EnumSet.copyOf(mainSubclassSet);

                subclasses.remove(this);

                switch (player.getRace()) {
                    case Race.ELF:
                        subclasses.removeAll(getSet(Race.DARK_ELF, ClassLevel.THIRD));
                        break;
                    case Race.DARK_ELF:
                        subclasses.removeAll(getSet(Race.ELF, ClassLevel.THIRD));
                        break;
                }

                subclasses.removeAll(getSet(Race.KAMAEL, ClassLevel.THIRD));

                final Set<PlayerClass> unavailableClasses = subclassSetMap.get(this);

                if (unavailableClasses != null) {
                    subclasses.removeAll(unavailableClasses);
                }

            } else {
                subclasses = getSet(Race.KAMAEL, ClassLevel.THIRD);
                subclasses.remove(this);
                // Check sex, male subclasses female and vice versa
                // If server owner set MaxSubclass > 3 some kamael's cannot take 4 sub
                // So, in that situation we must skip sex check
                if (Config.MAX_SUBCLASS <= 3) {
                    if (player.getAppearance().getSex()) {
                        subclasses.removeAll(EnumSet.of(femaleSoulbreaker));
                    } else {
                        subclasses.removeAll(EnumSet.of(maleSoulbreaker));
                    }
                }
                if (!player.getSubClasses().containsKey(2) || (player.getSubClasses().get(2).getLevel() < 75)) {
                    subclasses.removeAll(EnumSet.of(inspector));
                }
            }
        }
        return subclasses;
    }

    public final boolean isOfRace(Race pRace) {
        return _race == pRace;
    }

    public final boolean isOfType(ClassType pType) {
        return _type == pType;
    }

    public final boolean isOfLevel(ClassLevel pLevel) {
        return _level == pLevel;
    }

    public final ClassLevel getLevel() {
        return _level;
    }
}