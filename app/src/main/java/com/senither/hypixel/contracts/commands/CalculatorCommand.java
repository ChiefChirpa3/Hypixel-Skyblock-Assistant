/*
 * Copyright (c) 2020.
 *
 * This file is part of Hypixel Skyblock Assistant.
 *
 * Hypixel Guild Synchronizer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hypixel Guild Synchronizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hypixel Guild Synchronizer.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.senither.hypixel.contracts.commands;

import com.google.common.collect.ImmutableMultiset;
import com.senither.hypixel.Constants;
import com.senither.hypixel.SkyblockAssistant;
import com.senither.hypixel.contracts.statistics.HasLevel;
import com.senither.hypixel.contracts.statistics.StatisticsResponse;
import com.senither.hypixel.hypixel.SkyBlockSkill;
import com.senither.hypixel.statistics.responses.DungeonResponse;
import com.senither.hypixel.statistics.responses.SkillsResponse;
import com.senither.hypixel.statistics.weight.DungeonWeight;
import com.senither.hypixel.statistics.weight.SkillWeight;
import com.senither.hypixel.statistics.weight.Weight;

public abstract class CalculatorCommand extends Command {

    public CalculatorCommand(SkyblockAssistant app) {
        super(app);
    }

    protected final SkillType<?> getSkillTypeFromName(String name, SkillsResponse skillsResponse, DungeonResponse dungeonResponse) {
        switch (name.toLowerCase()) {
            case "mine":
            case "mining":
                return new SkillType<>(
                    "Mining",
                    skillsResponse.getMining(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setMining,
                    SkillWeight.MINING::calculateSkillWeight,
                    SkyBlockSkill.MINING
                );

            case "tree":
            case "forage":
            case "foraging":
                return new SkillType<>(
                    "Foraging",
                    skillsResponse.getForaging(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setForaging,
                    SkillWeight.FORAGING::calculateSkillWeight,
                    SkyBlockSkill.FORAGING
                );

            case "enchant":
            case "enchanting":
                return new SkillType<>(
                    "Enchanting",
                    skillsResponse.getEnchanting(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setEnchanting,
                    SkillWeight.ENCHANTING::calculateSkillWeight,
                    SkyBlockSkill.ENCHANTING
                );

            case "farm":
            case "farming":
                return new SkillType<>(
                    "Farming",
                    skillsResponse.getFarming(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setFarming,
                    SkillWeight.FARMING::calculateSkillWeight,
                    SkyBlockSkill.FARMING
                );

            case "fight":
            case "combat":
                return new SkillType<>(
                    "Combat",
                    skillsResponse.getCombat(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setCombat,
                    SkillWeight.COMBAT::calculateSkillWeight,
                    SkyBlockSkill.COMBAT
                );

            case "fish":
            case "fishing":
                return new SkillType<>(
                    "Fishing",
                    skillsResponse.getFishing(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setFishing,
                    SkillWeight.FISHING::calculateSkillWeight,
                    SkyBlockSkill.FISHING
                );

            case "alch":
            case "alchemy":
                return new SkillType<>(
                    "Alchemy",
                    skillsResponse.getAlchemy(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setAlchemy,
                    SkillWeight.ALCHEMY::calculateSkillWeight,
                    SkyBlockSkill.ALCHEMY
                );

            case "pet":
            case "pets":
            case "tame":
            case "taming":
                return new SkillType<>(
                    "Taming",
                    skillsResponse.getTaming(),
                    SkillCalculationType.GENERAL,
                    SkillsResponse::setTaming,
                    SkillWeight.TAMING::calculateSkillWeight,
                    SkyBlockSkill.TAMING
                );

            case "rune":
            case "runecraft":
            case "runecrafting":
                return new SkillType<>(
                    "Runecrafting",
                    skillsResponse.getRunecrafting(),
                    SkillCalculationType.RUNECRAFTING,
                    SkillsResponse::setRunecrafting,
                    null,
                    SkyBlockSkill.RUNECRAFTING
                );

            case "ca":
            case "cata":
            case "catacomb":
            case "catacombs":
                return new SkillType<DungeonResponse>(
                    "Catacomb",
                    dungeonResponse.getDungeonFromType(DungeonResponse.DungeonType.CATACOMBS),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getDungeonFromType(DungeonResponse.DungeonType.CATACOMBS)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.CATACOMB::calculateWeight
                );

            case "heal":
            case "healer":
                return new SkillType<DungeonResponse>(
                    "Healer",
                    dungeonResponse.getClassFromType(DungeonResponse.DungeonClassType.HEALER),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getClassFromType(DungeonResponse.DungeonClassType.HEALER)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.HEALER::calculateWeight
                );

            case "mage":
            case "mages":
                return new SkillType<DungeonResponse>(
                    "Mage",
                    dungeonResponse.getClassFromType(DungeonResponse.DungeonClassType.MAGE),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getClassFromType(DungeonResponse.DungeonClassType.MAGE)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.MAGE::calculateWeight
                );

            case "warrior":
            case "berserk":
            case "berserker":
                return new SkillType<DungeonResponse>(
                    "Berserker",
                    dungeonResponse.getClassFromType(DungeonResponse.DungeonClassType.BERSERK),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getClassFromType(DungeonResponse.DungeonClassType.BERSERK)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.BERSERK::calculateWeight
                );

            case "bow":
            case "archer":
                return new SkillType<DungeonResponse>(
                    "Archer",
                    dungeonResponse.getClassFromType(DungeonResponse.DungeonClassType.ARCHER),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getClassFromType(DungeonResponse.DungeonClassType.ARCHER)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.ARCHER::calculateWeight
                );

            case "tank":
                return new SkillType<DungeonResponse>(
                    "Tank",
                    dungeonResponse.getClassFromType(DungeonResponse.DungeonClassType.TANK),
                    SkillCalculationType.DUNGEON,
                    (response, level, experience) -> {
                        response.getClassFromType(DungeonResponse.DungeonClassType.TANK)
                            .setLevelAndExperience(level, experience);

                        return response;
                    },
                    DungeonWeight.TANK::calculateWeight
                );

            default:
                return null;
        }
    }

    protected enum SkillCalculationType {

        GENERAL, RUNECRAFTING, DUNGEON, SLAYERS
    }

    protected class SkillType<T extends StatisticsResponse> {

        private final String name;
        private final HasLevel stat;
        private final SkillCalculationType type;
        private final SetCalculatableSkill<T> skillFunction;
        private final GetWeightCalculator weightFunction;
        private final SkyBlockSkill skillType;

        public SkillType(
            String name,
            HasLevel stat,
            SkillCalculationType type,
            SetCalculatableSkill<T> skillFunction,
            GetWeightCalculator weightFunction
        ) {
            this(name, stat, type, skillFunction, weightFunction, null);
        }

        public SkillType(
            String name,
            HasLevel stat,
            SkillCalculationType type,
            SetCalculatableSkill<T> skillFunction,
            GetWeightCalculator weightFunction,
            SkyBlockSkill skillType
        ) {
            this.name = name;
            this.stat = stat;
            this.type = type;

            this.skillType = skillType;
            this.skillFunction = skillFunction;
            this.weightFunction = weightFunction;
        }

        public String getName() {
            return name;
        }

        public HasLevel getStat() {
            return stat;
        }

        public SkillCalculationType getType() {
            return type;
        }

        public T setLevelAndExperience(T response, double level, double experience) {
            return skillFunction.setCalculateableSkill(response, level, experience);
        }

        public ImmutableMultiset<Integer> getExperienceList() {
            switch (type) {
                case GENERAL:
                    return Constants.GENERAL_SKILL_EXPERIENCE;

                case RUNECRAFTING:
                    return Constants.RUNECRAFTING_SKILL_EXPERIENCE;

                case DUNGEON:
                    return Constants.DUNGEON_EXPERIENCE;

                case SLAYERS:
                    return Constants.SLAYER_EXPERIENCE;

                default:
                    throw new RuntimeException("No valid skill experience calculator could be found for type '" + type + "'");
            }
        }

        public Weight calculateWeight(double experience) {
            return weightFunction.getWeight(experience);
        }

        public SkyBlockSkill getSkillType() {
            return skillType;
        }
    }
}
